use libc::{fopen, fread, fwrite, fflush, fclose};
use std::alloc::{alloc, Layout, dealloc};
use std::ffi::c_void;

fn main() {

    unsafe {
        let filename_in = b"resource/file.in\x00".as_ptr();
        let filename_out = b"resource/file.out\x00".as_ptr();

        let file_in = fopen(filename_in as *const i8, b"rb\x00".as_ptr() as *const i8);
        let file_out = fopen(filename_out as *const i8, b"wb\x00".as_ptr() as *const i8);

        if file_in.is_null() {
            println!("open failure");
            return;
        }

        let buf= [0u8; 128].as_mut_ptr();

        loop{
            let read_num = fread(buf as *mut c_void, 1, 128, file_in);
            if read_num == 0{
                break;
            }

            fwrite(buf as *const c_void, 1, read_num, file_out);
        }
        fclose(file_in);
        fclose(file_out);
    }


}