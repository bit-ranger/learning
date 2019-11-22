use libc::{getpriority, PRIO_PROCESS, getpid, id_t, c_uint, getrlimit, rlimit, RLIMIT_NOFILE, getrusage, rusage, RUSAGE_SELF};
use std::alloc::{alloc, Layout, dealloc};
use std::mem::{size_of, align_of};

fn main() {

    unsafe {
        let pri = getpriority(PRIO_PROCESS as c_uint, getpid() as id_t);
        println!("getpriority = {}", pri);

        let layout = Layout::from_size_align(size_of::<rlimit>(), align_of::<rlimit>()).unwrap();
        let limit_ptr = alloc(layout) as *mut rlimit;

        getrlimit(RLIMIT_NOFILE, limit_ptr);

        println!("{}, {}", (*limit_ptr).rlim_cur, (*limit_ptr).rlim_max);
        dealloc(limit_ptr as *mut u8, layout);

        let layout = Layout::from_size_align(size_of::<rusage>(), align_of::<rusage>()).unwrap();
        let rusage_ptr = alloc(layout) as *mut rusage;
        getrusage(RUSAGE_SELF, rusage_ptr);
        println!("{}, {}", (*rusage_ptr).ru_utime.tv_sec, (*rusage_ptr).ru_stime.tv_sec);
        dealloc(rusage_ptr as *mut u8, layout);

    }

}