use std::fs::File;
use std::io::{ErrorKind, Read};

#[derive(Debug)]
struct Class{
    name: String,
    qualifier: Vec<String>,
    comment: Vec<String>
}

#[derive(Debug)]
struct Method{
    name: String,
    return_type: String,
    input_type: Vec<String>,
    qualifier: Vec<String>,
    comment: Vec<String>
}

#[derive(Debug)]
struct Field{
    name: String,
    data_type: String,
    qualifier: Vec<String>,
    comment: Vec<String>
}

#[derive(Debug)]
enum Member{
    Class (Class),
    Method (Method),
    Field (Field)
}


fn parse_class(tokens: Vec<String>) -> Vec<Member>{
    let mut members : Vec<Member> = Vec::new();
    let mut stack:Vec<String> = Vec::new();
    // for tk in tokens {
    //     match columns[0] {
    //         "public" | "private" => {
    //             let member = Member{
    //                 name: String::from(columns[2]),
    //                 member_type: String::from(columns[1]),
    //                 qualifier: String::from(columns[0]),
    //                 comment: stack.clone(),
    //                 children: Vec::new()
    //             };
    //             members.push(member);
    //             stack.clear();
    //         }
    //         _ => {
    //             stack.push(columns.join("\n"))
    //         }
    //     }
    // }
    return members;
}

fn split_keep<'a>(is_delimiter: fn(char) -> bool, text: &'a str) -> Vec<&'a str> {
    let mut result: Vec<&str> = Vec::new();
    let mut li:usize = 0;
    for (i, c) in text.char_indices(){
        if is_delimiter(c){
            result.push(&text[li..i]);
            result.push(&text[i..i+1]);
            li = i+1;
        }
    }

    return result;
}

fn tokenize(text: String) -> Vec<String> {
    let regex = |c: char| [' ', '\t', '\n', '\r', ';', '(', ')', '{', '}'].contains(&c);
    let splits = split_keep(regex, text.as_str());
    return splits.into_iter()
        .map(|t| t.trim())
        .filter(|t| !t.is_empty())
        .map(|t| String::from(t))
        .collect();
}

fn parse(text: String) -> Vec<Member>{
    let tokens = tokenize(text);

    return Vec::new();
}

fn main() {
    let f = File::open("C:\\Home\\Workspace\\repo\\starunion\\starunion\\starunion-tracker\\starunion-tracker-interface\\src\\main\\java\\com\\starunion\\tracker\\param\\PenaltyTransferArtificialHandleParam.java");
    let mut f = match f {
        Ok(file) => file,
        Err(error) => match error.kind() {
            ErrorKind::NotFound => panic!("File not found"),
            other_error => panic!("There was a problem opening the file: {:?}", other_error),
        },
    };

    let mut text = String::new();
    f.read_to_string(&mut text);
    let members = parse(text);
    print!("{:#?}", members)

}
