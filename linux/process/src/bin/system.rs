use libc::system;
use std::os::raw::c_char;

fn main() {

    unsafe {
        system("ps ax \x00".as_ptr() as *const c_char);
        println!("done")
    }
}
