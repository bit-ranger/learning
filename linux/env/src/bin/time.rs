use chrono::offset::Utc;
use chrono::DateTime;
use libc::{time, sleep, difftime, gmtime, mktime, localtime};
use std::ptr;
use std::time::{Duration, UNIX_EPOCH};
use std::ops::Add;
use std::ffi::CStr;

fn main() {

    unsafe {
        let time1 = time(ptr::null_mut());
        sleep(2);
        let time2 = time(ptr::null_mut());
        println!("time2 = {}", time2);
        let difftime = difftime(time1, time2);
        println!("difftime = {}", difftime);

        let tm = gmtime(&time2 as *const i64);
        println!("{} {} {}", (*tm).tm_hour, (*tm).tm_min, (*tm).tm_sec);

        // 不等于之前的time2??
        let time2 = mktime(tm);
        println!("time2 = {}", time2);

        let tm = localtime( &time2 as *const i64);
        println!("{} {} {}", (*tm).tm_hour, (*tm).tm_min, (*tm).tm_sec);


//        let mut fmt_time = [0i8;100] ;
//        strftime((&mut fmt_time).as_mut_ptr(), 100, b"%d/%m/%Y %T\x00".as_ptr() as *const i8, tm);
//        println!("strftime = {}", CStr::from_ptr((&fmt_time).as_ptr()).to_str().unwrap());


        //rust
        let du = Duration::new(time2 as u64, 0);
        let now:DateTime<Utc> = UNIX_EPOCH.add(du).into();
        println!("{}", now.format("%d/%m/%Y %T"));



    }
}