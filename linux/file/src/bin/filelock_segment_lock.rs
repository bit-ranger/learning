use nix::fcntl::{open, fcntl, FcntlArg, OFlag};
use nix::unistd::{getpid, close, write};
use std::time::Duration;
use std::thread::sleep;
use libc::{flock, F_RDLCK, F_WRLCK, SEEK_SET};
use nix::sys::stat::Mode;
use std::os::raw::c_short;

fn main() {

    let lock_file_path = "/tmp/lock_segment.test";

    let lock_file = open(lock_file_path,
                         OFlag::O_CREAT | OFlag::O_RDWR,
                         Mode::S_IRUSR | Mode::S_IWUSR | Mode::S_IRGRP | Mode::S_IWGRP | Mode::S_IROTH | Mode::S_IWOTH);
    match lock_file {
        Err(e) => {
            println!("create failure, {}, {}", e, getpid());
            sleep(Duration::from_secs(3));
        }
        Ok(f) => {
            write(f, &[0; 100]).unwrap();

            println!("Locking file, {}", getpid());
            let lock_1_info = flock{
                l_type: F_RDLCK as c_short,
                l_whence: SEEK_SET as c_short,
                l_start: 10,
                l_len: 20,
                l_pid: 0,
            };
            if let Err(e) = fcntl(f, FcntlArg::F_SETLK(&lock_1_info)){
                println!("Failed to F_RDLCK, {}, {}", e, getpid());
            }

            let lock_2_info = flock{
                l_type: F_WRLCK as c_short,
                l_whence: SEEK_SET as c_short,
                l_start: 40,
                l_len: 10,
                l_pid: 0,
            };
            if let Err(e) = fcntl(f, FcntlArg::F_SETLK(&lock_2_info)){
                println!("Failed to F_WRLCK, {}, {}", e, getpid());
            }

            println!("Locked file, {}", getpid());

            sleep(Duration::from_secs(60));

            println!("Closing file, {}", getpid());
            if let Err(e) = close(f){
                println!("Failure to close, {}, {}", e, getpid());
            }
        }
    }

}