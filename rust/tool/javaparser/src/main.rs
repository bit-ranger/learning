use std::fs::File;
use std::io::{ErrorKind, Read};
use std::env;
use std::path::Path;
mod meta;
mod parser;
mod render;


fn main() {
    let args: Vec<_> = env::args().collect();
    let program = args[0].clone();
    let mut opts = getopts::Options::new();
    opts.optopt("o", "output", "output dir", "output dir");

    let matches = match opts.parse(&args[1..]) {
        Ok(m) => m,
        Err(_) => {
            println!("{}", opts.short_usage(&program));
            return;
        }
    };

    let output_file_path = matches.opt_str("o");
    let source_files = matches.free;

    for src in source_files {
        let path = Path::new(&src);
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
        let tokens = parser::tokenize(text);
        let members = parser::parse_file(&tokens);
        print!("{:#?}", &members);

        match &output_file_path{
            Some(of) => render::export(&members, &format!("{}{}{}.csv", of, std::path::MAIN_SEPARATOR, path.file_name().unwrap().to_str().unwrap())),
            None => render::export(&members, &format!("{}.csv", src))
        }

    }

}
