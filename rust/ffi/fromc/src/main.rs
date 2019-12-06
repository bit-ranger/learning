#[link(name = "hello_static", kind = "static")]
extern "C" {
    pub fn say_hello_static();
}

#[link(name = "hello_dynamic")]
extern "C" {
    pub fn say_hello_dynamic();
}

fn main() {
    unsafe {
        say_hello_static();
        say_hello_dynamic();
    }
}
