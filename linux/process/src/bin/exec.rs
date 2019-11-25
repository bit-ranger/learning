use nix::unistd::{execv, execve, execvp};
use std::ffi::{CString, CStr};
use std::os::raw::c_char;


fn to_cstring(s: &str) -> CString{
    unsafe {
        CString::from(CStr::from_ptr(s.as_ptr() as *mut c_char))
    }
}

fn main() {

    unsafe {

        let arg = [to_cstring("ps\0"), to_cstring("ax\0")];
        let env = [to_cstring("PATH=/bin:/usr/bin\0")];
        execv(&to_cstring("/bin/ps"), &arg );
        execvp(&to_cstring("/bin/ps"), &arg );
        execve(&to_cstring("/bin/ps"), &arg, &env);
    }

}
