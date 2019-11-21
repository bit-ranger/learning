use libc::{fopen, mmap, PROT_READ, PROT_WRITE, MAP_PRIVATE, msync, munmap, MS_ASYNC, fclose};
use std::mem::size_of;
use std::os::raw::c_void;
use std::ptr;

struct Record {
    id: u32,
    name: u32
}

fn main() {
    unsafe {

        let fp_name = b"mmap.dat\x00".as_ptr();
        let fp = fopen(fp_name as *const i8, b"wb\x00".as_ptr() as *const i8);

        if fp.is_null(){
            println!("open failure");
            return;
        }

        let total = 100usize;
        let mmap_size = size_of::<Record>() * total;

        println!("mmap size {}", mmap_size);

        let mapped = mmap(ptr::null_mut(), mmap_size, PROT_READ | PROT_WRITE, MAP_PRIVATE, fp as i32, 0) as *mut Record;

        println!("mapped {:?}", mapped);

        let mut record = Record {
            id: 0,
            name: 0,
        };
        for i in 0..total {
            record.id = i as u32;
            record.name = i as u32
        }

        (*mapped.offset(43)).id = 243;
        (*mapped.offset(43)).name = 243;

        msync(mapped as *mut c_void, mmap_size, MS_ASYNC);
        println!("msync {:?}", mapped);

        munmap(mapped as *mut c_void, mmap_size);
        println!("munmap {:?}", mapped);

        fclose(fp);
    }
}