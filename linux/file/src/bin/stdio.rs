use libc::{fopen, fread, fwrite, fflush, fclose};
use std::alloc::{alloc, Layout, dealloc};
use std::ffi::c_void;

fn main() {
    unsafe {
        let filename = b"resource/file.in\x00".as_ptr();
        let mode = b"r+b\x00".as_ptr();

        let file = fopen(filename as *const i8, mode as *const i8);

        if file.is_null() {
            println!("open failure");
            return;
        }

        let buf= [0u8; 128].as_mut_ptr();
//        let mem_layout = Layout::from_size_align(128, 16).unwrap();
//        let buf = alloc(mem_layout);

        let read_num = fread(buf as *mut c_void, 1, 128, file);

        let read_str = String::from_raw_parts(buf, read_num, read_num);

//        dealloc(buf, mem_layout);
        println!("{}", read_str);

        let write_content = b"world\x00";
        let write_num = fwrite(write_content.as_ptr() as *const c_void, 1, write_content.len(), file);

        fflush(file);
        fclose(file);

        println!("write bytes {}", write_num);


    };
}