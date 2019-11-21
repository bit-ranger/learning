use libc::{getenv};
use std::ffi::CStr;

fn main() {

    unsafe {
        let java_home = getenv(b"JAVA_HOME\x00".as_ptr() as *const i8);
        println!("JAVA_HOME={}", CStr::from_ptr(java_home).to_str().unwrap())
    }
}
