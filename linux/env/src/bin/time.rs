use chrono::offset::Utc;
use chrono::DateTime;
use libc::time;
use std::ptr;
use std::time::{Duration, UNIX_EPOCH};
use std::ops::Add;

fn main() {

    unsafe {
        let time = time(ptr::null_mut());
        let du = Duration::new(time as u64, 0);
        let now:DateTime<Utc> = UNIX_EPOCH.add(du).into();
        println!("{}", now.format("%d/%m/%Y %T"));


    }
}