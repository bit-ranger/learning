use libc::{tmpnam, L_tmpnam};
use std::os::raw::c_char;
use std::ffi::CStr;
use nix::unistd::{mkstemp, unlink};
use nix::sys::stat::fstat;
use nix::fcntl::readlink;

fn main() {

    unsafe {
        let mut tmp_file_name_ptr = [0u8; L_tmpnam as usize].as_mut_ptr() as *mut c_char;
        tmpnam(tmp_file_name_ptr);
        println!("tmp_file_name = {}", CStr::from_ptr(tmp_file_name_ptr).to_str().unwrap());
        tmpnam(tmp_file_name_ptr);
        println!("tmp_file_name = {}", CStr::from_ptr(tmp_file_name_ptr).to_str().unwrap());

        let temp_file = match mkstemp("/tmp/tempfile_XXXXXX") {
            Ok((fd, path)) => {
                unlink(path.as_path()).unwrap(); // flag file to be deleted at app termination
                fd
            }
            Err(e) => panic!("mkstemp failed: {}", e)
        };


        let link = format!("/proc/self/fd/{}", temp_file);
        let mut link_content = [0u8; 1024];
        readlink(link.as_str(), &mut link_content);
        println!("{}", CStr::from_bytes_with_nul_unchecked(&link_content).to_str().unwrap())
    }








}