use std::error::Error;
use std::process;
use strfmt::strfmt;
use csv::StringRecord;
use std::collections::HashMap;
use std::env;

fn generate(path: &str, format: &str) -> Result<Vec<String>, Box<dyn Error>> {
    // Build the CSV reader and iterate over each record.
    let mut rdr = csv::Reader::from_path(path).unwrap();
    let mut vec = Vec::new();
    for result in rdr.records() {
        // The iterator yields Result<StringRecord, Error>, so we check the
        // error here.
        let record = result?;
        let hash_map = to_hashmap(record);
        let statement = strfmt(format, &hash_map).unwrap();
        vec.push(statement);
    }
    Ok(vec)
}

fn to_hashmap(sr: StringRecord) -> HashMap<String,String>{
    let mut counter:i32 = 0;
    let mut hash_map = HashMap::new();
    for sri in sr.iter() {
        hash_map.insert(counter.to_string(), String::from(sri));
        counter+=1;
    }
    return hash_map;
}

fn main() {
    let args: Vec<_> = env::args().collect();
    let mut opts = getopts::Options::new();
    opts.reqopt("f", "file", "file path", "file path");
    opts.reqopt("s", "statement", "statement", "statement");

    let matches = match opts.parse(&args[1..]) {
        Ok(m) => m,
        Err(_) => {
            println!("{}", opts.short_usage("sqlgen"));
            return;
        }
    };

    let file_path = matches.opt_str("f").unwrap();
    let format = matches.opt_str("s").unwrap();

    match generate(
        &file_path,
        &format) {
        Err(_e) => {
            process::exit(1);
        },
        Ok(vec) => {
            for statement in vec {
                println!("{}", statement)
            }
        }
    }
}