use std::ffi::CStr;
use std::os::raw::c_char;

#[link(name = "hello_static", kind = "static")]
extern "C" {
    pub fn hello_static() -> *const c_char;
}

#[link(name = "hello_dynamic")]
extern "C" {
    pub fn hello_dynamic() -> *const c_char;
}

fn main() {
    unsafe {
        println!(
            "{}",
            std::str::from_utf8(CStr::from_ptr(hello_static()).to_bytes()).unwrap()
        );
        println!(
            "{}",
            std::str::from_utf8(CStr::from_ptr(hello_dynamic()).to_bytes()).unwrap()
        );
    }
}
