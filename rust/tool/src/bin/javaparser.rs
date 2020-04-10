use std::fs::File;
use std::io::{ErrorKind, Read};
use std::io;

#[derive(Debug)]
struct Member{
    name: String,
    self_type: String,
    accessibility: String,
    comment: Vec<String>,
    members: Vec<Member>
}

fn parse_class(rows: &Vec<&str>) -> Vec<Member>{
    let mut members : Vec<Member> = Vec::new();
    let mut stack:Vec<String> = Vec::new();
    for row in rows {
        let columns:&Vec<&str> =  &row.split_terminator(' ').collect();
        match columns[0] {
            "public" | "private" => {
                let member = Member{
                    name: String::from(columns[2]),
                    self_type: String::from(columns[1]),
                    accessibility: String::from(columns[0]),
                    comment: stack.clone(),
                    members: Vec::new()
                };
                members.push(member);
                stack.clear();
            }
            _ => {
                stack.push(columns.join("\n"))
            }
        }
    }
    return members;
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

    let rows:Vec<&str> =  text.split_terminator('\n')
        .into_iter()
        .map(|r| r.trim())
        .filter(|r| !r.is_empty())
        .collect();

    let members = parse_class(&rows);
    print!("{:#?}", members)



}
