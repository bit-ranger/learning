use libc::{syslog, openlog, LOG_PID, LOG_CONS, LOG_USER, LOG_INFO, getpid};
use std::os::raw::c_char;

fn main() {

    unsafe {
        openlog("log_demo".as_ptr() as *const c_char, LOG_PID|LOG_CONS, LOG_USER);
        syslog(LOG_INFO, "pid = %d".as_ptr() as *const c_char, getpid())
    }

}