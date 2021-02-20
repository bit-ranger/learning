use std::env;
use reqwest::header::{CONTENT_TYPE};
use std::slice::Iter;
use md5;
use itertools::Itertools;
use json::JsonValue;

fn main() {

    let args: Vec<_> = env::args().collect();
    let program = args[0].clone();
    let mut opts = getopts::Options::new();
    opts.reqopt("k", "key", "qqmap key", "key");
    opts.reqopt("s", "sk", "qqmap sk", "sk");

    let matches = match opts.parse(&args[1..]) {
        Ok(m) => m,
        Err(_) => {
            println!("{}", opts.short_usage(&program));
            return;
        }
    };

    let key = matches.opt_str("k").unwrap();
    let sk = matches.opt_str("s").unwrap();


    let path = create_path(&sk, "/ws/geocoder/v1/", vec![
        ("address", "重庆市奉节县新世纪百货奉节商都2楼百家好专柜"),
        ("key", &key),
    ].iter());

    let url = format!("https://apis.map.qq.com{}", &path);
    println!("{}", &url);
    let json_value = request_json(&url);
    println!("{}", json_value);


}


fn create_path(sk: &str, uri: &str, param: Iter<(&str, &str)>) -> String{
    let mut vec:Vec<&(&str, &str)> = param.collect_vec();
    vec.sort();

    let param_str = vec.iter()
        .map(|(k, v)| { format!("{}={}", k, v) })
        .join("&");

    let raw = format!("{}?{}{}", uri, param_str, sk);
    let digest = format!("{:x}",md5::compute(raw));

    return format!("{}?{}&sig={}", uri, param_str, digest);
}

fn request_json(url: &str) -> JsonValue{
    let client = reqwest::blocking::Client::new();
    let resp = client
        .get(url)
        .header(CONTENT_TYPE, "application/json")
        .send()
        .unwrap();
    let text = &resp.text().unwrap();
    let json = json::parse(text).unwrap();
    return json;
}