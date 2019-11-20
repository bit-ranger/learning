use libc::{fopen, fread};
use std::alloc::{alloc, Layout};
use std::ffi::c_void;

fn main() {
     unsafe {
        let file = fopen("file.in".as_ptr() as *const i8, "r".as_ptr() as *const i8);

         let layout = Layout::from_size_align(100, 4).unwrap();
         let buf = alloc(layout);

//         let nr = fread(buf as *mut c_void, 1, 8, file);
//         let content = String::from_raw_parts(buf.as_mut_ptr(), nr, nr);
//         println!("{}", content);

    };

}