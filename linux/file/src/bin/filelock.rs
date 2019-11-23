use nix::fcntl::open;
use nix::fcntl::OFlag;
use nix::sys::stat::Mode;
use std::thread::sleep;
use std::time::Duration;
use nix::unistd::{getpid, close, unlink};

fn main() {

    let lock_file_path = "/tmp/lock.test";

    for i in 0..9{
        let lock_file = open("/tmp/lock.test",
                             OFlag::O_CREAT | OFlag::O_RDWR | OFlag::O_EXCL,
                             Mode::S_IRUSR | Mode::S_IRGRP | Mode::S_IROTH);
        match lock_file {
            Err(e) => {
                println!("create failure, {}, {}", e, getpid());
                sleep(Duration::from_secs(3));
            }
            Ok(f) => {
                println!("create success, {}", getpid());
                sleep(Duration::from_secs(1));
                close(f);
                unlink(lock_file_path);
            }
        }
    }


}