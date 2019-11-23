use nix::fcntl::{open, fcntl, FcntlArg, OFlag};
use nix::unistd::{getpid};
use std::time::Duration;
use std::thread::sleep;
use libc::{flock, F_RDLCK, F_WRLCK, SEEK_SET};
use nix::sys::stat::Mode;
use std::os::raw::c_short;

fn main() {

    let lock_file_path = "/tmp/lock_segment.test";

    let lock_file = open(lock_file_path,
                         OFlag::O_RDWR,
                         Mode::S_IRUSR | Mode::S_IWUSR | Mode::S_IRGRP | Mode::S_IWGRP | Mode::S_IROTH | Mode::S_IWOTH);
    match lock_file {
        Err(e) => {
            println!("open failure, {}, {}", e, getpid());
            sleep(Duration::from_secs(3));
        }
        Ok(f) => {
            let step = 5;
            for i in (0..99).step_by(step) {

                println!("Testing region, {} - {}, {}", i, i+step, getpid());
                let mut lock_w_info = flock{
                    l_type: F_WRLCK as c_short,
                    l_whence: SEEK_SET as c_short,
                    l_start: i as i64,
                    l_len: step as i64,
                    l_pid: -1,
                };

                match fcntl(f, FcntlArg::F_GETLK(&mut lock_w_info)){
                    Err(e) => println!("Failed to F_WRLCK, {}, {}", e, getpid()),
                    Ok(_) => {
                        if lock_w_info.l_pid != -1 {
                            println!("Write Locked Would Failure, {}", getpid());
                        }else{
                            println!("Write Locked Would Success, {}", getpid());
                        }
                    }
                }

                let mut lock_r_info = flock{
                    l_type: F_RDLCK as c_short,
                    l_whence: SEEK_SET as c_short,
                    l_start: i as i64,
                    l_len: step as i64,
                    l_pid: -1,
                };

                match fcntl(f, FcntlArg::F_GETLK(&mut lock_r_info)){
                    Err(e) => println!("Failed to F_GETLK, {}, {}", e, getpid()),
                    Ok(_) => {
                        if lock_r_info.l_pid != -1 {
                            println!("Read Locked Would Failure, {}", getpid());
                        } else{
                            println!("Read Locked Would Success, {}", getpid());
                        }
                    }
                }

            }
        }
    }

}