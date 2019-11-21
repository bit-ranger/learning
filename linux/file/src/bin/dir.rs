use libc::{opendir, readdir, chdir, closedir};
use std::ffi::CStr;

fn main() {
    let dir_path = b".\x00".as_ptr() as *const i8;

    let dir = unsafe { opendir(dir_path) };
    unsafe { chdir(dir_path); }

    loop{
        let dirent = unsafe { readdir(dir) };

        if dirent.is_null(){
            break;
        }

        let d_name =  (unsafe { *dirent }).d_name.as_ptr() as *mut i8;
        let d_name: &CStr = unsafe { CStr::from_ptr(d_name) };
        let d_name = d_name.to_str().unwrap();
        println!("{}", d_name);
    }

    unsafe { closedir(dir); }

}