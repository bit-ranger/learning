use std::fs::File;
use std::io::{ErrorKind, Read, Write};
use std::env;
use std::path::Path;

#[derive(Debug,Clone)]
struct Type {
    name: String,
    category: String,
    qualifier: Vec<String>,
    implements: Vec<String>,
    extends: Option<String>,
    annotation: Vec<String>,
    comment: Vec<String>,
    member: Vec<Member>,
}

#[derive(Debug,Clone)]
struct Method {
    name: String,
    is_abstract: bool,
    return_type: String,
    input_type: Vec<String>,
    qualifier: Vec<String>,
    annotation: Vec<String>,
    comment: Vec<String>,
}

#[derive(Debug,Clone)]
struct Field {
    name: String,
    data_type: String,
    qualifier: Vec<String>,
    annotation: Vec<String>,
    comment: Vec<String>,
}

#[derive(Debug,Clone)]
struct FileHead {
    comment: Vec<String>,
    import: Vec<String>,
    package: String,
}

#[derive(Debug,Clone)]
enum Member {
    Type(Type),
    Method(Method),
    Field(Field),
    FileHead(FileHead),
}

fn split_keep(is_delimiter: fn(char) -> bool, text: &str) -> Vec<&str> {
    let mut result: Vec<&str> = Vec::new();
    let mut li: usize = 0;
    for (i, c) in text.char_indices() {
        if is_delimiter(c) {
            result.push(&text[li..i]);
            result.push(&text[i..i + 1]);
            li = i + 1;
        }
    }

    return result;
}

fn tokenize(text: String) -> Vec<String> {
    let is_delimiter = |c: char| [' ', '\t', '\n', '\r', ',', ';', '(', ')', '{', '}', '<', '>'].contains(&c);
    let splits = split_keep(is_delimiter, text.as_str());
    return splits.into_iter()
        .map(|t| String::from(t))
        .collect();
}

fn parse_type(tokens: &Vec<String>) -> Vec<Member> {
    let mut member_list: Vec<Member> = Vec::new();
    let mut meta_left = 0;
    let mut comment: Vec<String> = Vec::new();
    let mut annotation: Vec<String> = Vec::new();
    for (i, t) in tokens.iter().enumerate() {
        if i < meta_left {
            continue;
        }
        if t.starts_with("/*") {
            let right_index = i + tokens[i..].iter().position(|e| e.ends_with("*/")).unwrap();
            comment.extend(parse_doc_comment(tokens[i..right_index + 1].to_vec().as_ref()));
            meta_left = right_index + 1;
            continue;
        }
        if t.starts_with("//") {
            let right_index = i + tokens[i..].iter().position(|e| e.eq("\n")).unwrap();
            comment.push(trim_and_remove_empty(tokens[i..right_index + 1].to_vec()).join(""));
            meta_left = right_index + 1;
            continue;
        }
        if t.starts_with("@") {
            if tokens[i + 1].eq("(") {
                let right_index = match_right(tokens, i + 1, &String::from(")"));
                annotation.push(trim_and_remove_empty(tokens[i..right_index + 1].to_vec()).join(""));
                meta_left = right_index + 1;
            } else {
                let right_index = i + tokens[i..].iter().position(|e| e.eq("\n")).unwrap();
                annotation.push(trim_and_remove_empty(tokens[i..right_index + 1].to_vec()).join(""));
                meta_left = right_index + 1;
            }
            continue;
        }
        if t.eq("{") {
            let right_index = match_right(tokens, i, &String::from("}"));
            let meta = trim_and_remove_empty(tokens[meta_left..i].to_vec());
            if is_method_meta(&meta) {
                let mut method = parse_method_meta(&meta);
                method.is_abstract = false;
                method.annotation = annotation.clone();
                method.comment = comment.clone();
                member_list.push(Member::Method(method));
            } else {
                let mut type_ = parse_type_meta(&meta);
                let class_member = parse_type(&tokens[i + 1..right_index + 1].to_vec());
                type_.annotation = annotation.clone();
                type_.comment = comment.clone();
                type_.member = class_member;
                member_list.push(Member::Type(type_));
            }
            annotation.clear();
            comment.clear();
            meta_left = right_index + 1;
            continue;
        }
        if t.eq(";") {
            let meta = trim_and_remove_empty(tokens[meta_left..i].to_vec());
            if is_method_meta(&meta){
                let mut method = parse_method_meta(&meta);
                method.is_abstract = true;
                method.annotation = annotation.clone();
                method.comment = comment.clone();
                member_list.push(Member::Method(method));
            } else{
                let mut field = parse_field_meta(&meta);
                field.annotation = annotation.clone();
                field.comment = comment.clone();
                member_list.push(Member::Field(field));
            }
            annotation.clear();
            comment.clear();
            meta_left = i + 1;
            continue;
        }
    }
    return member_list;
}

fn parse_file(tokens: &Vec<String>) -> Vec<Member> {
    let mut member_list: Vec<Member> = Vec::new();
    let mut comment: Vec<String> = Vec::new();
    let mut comment_enable = true;
    let mut package: Option<String> = Option::None;
    let mut import: Vec<String> = Vec::new();
    let mut meta_left = 0;
    for (i, t) in tokens.iter().enumerate() {
        if i < meta_left {
            continue;
        }
        if t.trim().is_empty() {
            continue;
        }
        if comment_enable && t.starts_with("/*") {
            let right_index = i + tokens[i..].iter().position(|e| e.ends_with("*/")).unwrap();
            comment.extend(parse_doc_comment(tokens[i..right_index + 1].to_vec().as_ref()));
            meta_left = right_index + 1;
            continue;
        }
        if t.eq("package") {
            let right_index = i + tokens[i..].iter().position(|e| e.eq(";")).unwrap();
            package = Option::Some(trim_and_remove_empty(tokens[i + 1..right_index].to_vec()).join(""));
            comment_enable = false;
            meta_left = right_index + 1;
            continue;
        }

        if t.eq("import") {
            let right_index = i + tokens[i..].iter().position(|e| e.eq(";")).unwrap();
            import.push(trim_and_remove_empty(tokens[i + 1..right_index].to_vec()).join(""));
            meta_left = right_index + 1;
            continue;
        }

        meta_left = i;
        break;
    }

    member_list.push(Member::FileHead(FileHead {
        comment: comment,
        package: package.unwrap(),
        import: import,
    }));

    let body_member = parse_type(tokens[meta_left..].to_vec().as_ref());
    member_list.extend(body_member);
    return member_list;
}

fn parse_type_meta(meta: &Vec<String>) -> Type {
    let implements_index = meta.iter().position(|e| e.eq("implements"));
    let extends_index = meta.iter().position(|e| e.eq("extends"));
    let class_index = meta.iter().position(|e| e.eq("class"));
    let interface_index = meta.iter().position(|e| e.eq("interface"));
    let enum_index = meta.iter().position(|e| e.eq("enum"));
    let category_index = match class_index {
        Some(ci) => ci,
        None => match interface_index {
            Some(ii) => ii,
            None => enum_index.unwrap()
        }
    };

    return Type {
        name: meta[category_index + 1 .. match extends_index {
            Some(ei) => ei,
            None => match implements_index {
                Some(ii) => ii,
                None => meta.len()
            }
        }].join(""),
        category: meta[category_index].clone(),
        qualifier: meta[..category_index].to_vec(),
        implements: match implements_index {
            Some(ii) => meta[ii..].to_vec(),
            None => Vec::with_capacity(0)
        },
        extends: match extends_index {
            Some(ei) => Some(meta[ei + 1..match implements_index {
                Some(ii) => ii,
                None => meta.len()
            }].join("")),
            None => None
        },
        member: Vec::with_capacity(0),
        annotation: Vec::with_capacity(0),
        comment: Vec::with_capacity(0),
    };
}

fn is_method_meta(meta: &Vec<String>) -> bool{
    return  meta.iter().position(|e| e.eq("(")).is_some()
}

fn parse_method_meta(meta: &Vec<String>) -> Method {
    let left_bracket_index = meta.iter().position(|e| e.eq("(")).unwrap();
    let name_index = left_bracket_index - 1;
    let qualifier_list = ["public", "private", "protected", "static"];
    let last_qualifier_index = meta[..name_index]
        .iter()
        .position(|e| qualifier_list.contains(&e.as_str()));


    let input_type =
        if left_bracket_index == meta.len() - 2 {
            Vec::with_capacity(0)
        } else {
            let mut it = Vec::new();
            let mut last_it_push_idx = left_bracket_index;
            for (i, e) in meta.iter().enumerate() {
                if e.eq(",") || i == meta.len() - 1 {
                    it.push(meta[last_it_push_idx+1 .. i-1].join(""));
                    last_it_push_idx = i;
                }
            }
            it
        };
    return Method {
        name: meta[name_index].clone(),
        is_abstract: true,
        input_type: input_type,
        return_type: match last_qualifier_index {
            Some(lqi) => {
                if name_index-lqi==1 {
                    meta[lqi+1..name_index+1].join("")
                } else{
                    meta[lqi+1..name_index].join("")
                }
            },
            None => {
                if name_index==0 {
                    meta[..name_index+1].join("")
                } else{
                    meta[..name_index].join("")
                }
            }
        },
        qualifier: meta[..match last_qualifier_index { Some(lqi) => lqi, None => 0}].to_vec(),
        annotation: Vec::with_capacity(0),
        comment: Vec::with_capacity(0),
    };
}

fn parse_field_meta(meta: &Vec<String>) -> Field {
    let eq_index = meta.iter().position(|e| e.eq("="));
    let define = match eq_index {
        Some(index) => meta[..index].to_vec(),
        None => meta.to_vec()
    };
    return Field {
        name: define[define.len() - 1].clone(),
        data_type: define[define.len() - 2].clone(),
        qualifier: define[..define.len() - 2].to_vec(),
        annotation: Vec::with_capacity(0),
        comment: Vec::with_capacity(0),
    };
}

fn parse_doc_comment(comments: &Vec<String>) -> Vec<String> {
    return comments.to_vec();
}

fn match_right(tokens: &Vec<String>, left_index: usize, right_token: &String) -> usize {
    let left_token = tokens.get(left_index).unwrap();
    let mut entrant = 0;
    for (i, t) in tokens[left_index..].iter().enumerate() {
        if t.eq(left_token) {
            entrant += 1;
        }
        if t.eq(right_token) {
            entrant -= 1;
            if entrant == 0 {
                return i + left_index;
            }
        }
    }
    return 0;
}

fn trim_and_remove_empty(tokens: Vec<String>) -> Vec<String> {
    return tokens.iter()
        .map(|e| e.trim())
        .filter(|e| !e.is_empty())
        .map(|e| String::from(e))
        .collect::<Vec<String>>();
}

fn to_flat_class(members:&Vec<Member>, flat_class_container:&mut Vec<Member>){
    for m in members{
        if let Member::Type(class) = m{
            flat_class_container.push(m.clone());
            if !class.member.is_empty(){
                to_flat_class(class.member.to_vec().as_ref(), flat_class_container);
            }
        }
    }
}

fn export_comment(comments: &Vec<String>) -> String{
    if comments.len() < 2{
        return String::new();
    }
    let comments:Vec<String> = comments[1..comments.len()-1].to_vec();
    let mut new:Vec<String> = Vec::new();
    let mut line_buffer: Vec<String> = Vec::new();
    for (i,e) in comments.iter().enumerate() {
        if e.eq("\r"){
            continue;
        }
        if e.eq("\n") || i == comments.len()-1{
            let star_pos =  line_buffer.iter().position(|e| e.eq("*"));
            match star_pos{
                None => new.push(line_buffer.join("")),
                Some(star_pos) => {
                    let any_before_star = line_buffer[..star_pos].iter().find(|e| !e.trim().is_empty());
                    match any_before_star{
                        None => new.push(line_buffer[star_pos +1..].join("")),
                        Some(_) => {line_buffer.join("");}
                    }
                }
            }

            line_buffer.clear();
        } else{
            line_buffer.push(e.clone());
        }
    }
    return new.join("");
}

fn export(members: &Vec<Member>, file_path: &String){
    let mut flat_class_list:Vec<Member> = Vec::new();
    to_flat_class(members, &mut flat_class_list);

    let output = File::create(file_path);
    let mut output = match output {
        Ok(file) => file,
        Err(error) => panic!("There was a problem creating the file: {:?}", error)
    };

    for fm in flat_class_list {
        match fm {
            Member::Type(class) => {
                let _ =writeln!(output, "{}", class.name);
                for cm in class.member {
                    match cm {
                        Member::Field(field) => {
                            let _ = writeln!(output, "{}", [field.name, field.data_type, export_comment(field.comment.as_ref())].join(","));
                        },
                        _ => continue
                    }
                }
            },
            _ => continue
        }
    }
}

fn main() {
    let mut args = env::args();
    let export_file_path =
        args.nth(1)
        .expect("require export_file_path");

    for arg in args {
        let path = Path::new(&arg);
        let file = File::open(&path);
        let mut input = match file {
            Ok(f) => f,
            Err(error) => match error.kind() {
                ErrorKind::NotFound => panic!("File not found"),
                other_error => panic!("There was a problem opening the file: {:?}", other_error),
            },
        };

        let mut text = String::new();
        let _ = input.read_to_string(&mut text);
        let tokens = tokenize(text);
        let members = parse_file(&tokens);
        print!("{:#?}", &members);

        export(&members, &format!("{}{}{}.csv", export_file_path, std::path::MAIN_SEPARATOR, path.file_name().unwrap().to_str().unwrap()));
    }


}
