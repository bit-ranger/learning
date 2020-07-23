use crate::meta::*;
use std::fs::File;
use std::io::{Write};

fn export_comment(comments: &Vec<String>) -> String{
    if comments.len() < 2{
        return String::new();
    }
    let comments:Vec<String> = comments[1..comments.len()-1].to_vec();
    let mut new:Vec<String> = Vec::new();
    let mut line_buffer: Vec<String> = Vec::new();
    for (i,e) in comments.iter().enumerate() {
        line_buffer.push(e.clone());
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
        }
    }
    return new.join("");
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

pub fn export(members: &Vec<Member>, file_path: &String){
    let mut flat_class_list:Vec<Member> = Vec::new();
    to_flat_class(members, &mut flat_class_list);

    let output = File::create(file_path);
    let mut output = match output {
        Ok(file) => file,
        Err(error) => panic!("There was a problem creating the file: {:?}", error)
    };
    //utf-8 bom
    let _ =output.write("\u{feff}".as_bytes());
    for fm in flat_class_list {
        match fm {
            Member::Type(class) => {
                let _ =writeln!(output, "{}", class.name);
                for cm in class.member {
                    match cm {
                        Member::Field(field) => {
                            let _ = writeln!(output, "{},{},\"{}\"", field.name, field.data_type, export_comment(field.comment.as_ref()));
                        },
                        _ => continue
                    }
                }
            },
            _ => continue
        }
    }
}