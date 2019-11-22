use libc::{getuid, getlogin, getpwuid, getpwnam};
use std::ffi::CStr;

fn main() {
    unsafe {
        let uid = getuid();
        println!("{}", uid);

//        let login = getlogin();
//        println!("{}", CStr::from_ptr(login).to_str().unwrap())

        let pw = getpwuid(uid);
        println!("pw_name={}, {}", CStr::from_ptr((*pw).pw_name).to_str().unwrap(), CStr::from_ptr((*pw).pw_passwd).to_str().unwrap())
    }



}