use cargo;

fn main() {
    let num = 10;
    println!("Hello, world! {} plus one is {}!", num, cargo::add_one(num));
}
