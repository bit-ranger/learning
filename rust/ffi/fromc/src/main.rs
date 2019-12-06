#[link(name = "hello", kind = "static")]
extern "C" {
    pub fn say_hello();
}

fn main() {
    unsafe {
        say_hello();
    }
}
