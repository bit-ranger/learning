use std::env;
use reqwest::header::{CONTENT_TYPE};

fn main() {

    let args: Vec<_> = env::args().collect();
    let program = args[0].clone();
    let mut opts = getopts::Options::new();
    opts.reqopt("k", "key", "amap key", "key");

    let matches = match opts.parse(&args[1..]) {
        Ok(m) => m,
        Err(_) => {
            println!("{}", opts.short_usage(&program));
            return;
        }
    };

    let key = matches.opt_str("k").unwrap();

    let client = reqwest::blocking::Client::new();
    let url = format!("https://restapi.amap.com/v3/geocode/geo?key={}&address={}", key, "重庆市奉节县新世纪百货奉节商都2楼");
    println!("{}", url);
    let resp = client
        .get(&url)
        .header(CONTENT_TYPE, "application/json")
        .send()
        .unwrap();
    let text = &resp.text().unwrap();
    let json = json::parse(text).unwrap();
    println!("{}", json);

}
