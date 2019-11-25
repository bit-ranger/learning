use nix::unistd::{fork, getpid, sleep};
use nix::unistd::ForkResult::{self, Parent, Child};
use nix::sys::wait::{wait, waitpid};
use nix::sys::wait::WaitStatus::Exited;
use nix::sys::wait::WaitPidFlag;

fn main() {


    match fork() {
        Ok(fr) => {
            match fr {
                Child => {
                    for i in 0..5{
                        println!("{}, I am a child", getpid());
                        sleep(1);
                    }

                },
                Parent{child} => {
//                    match wait().unwrap(){
                    match waitpid(child, Some(WaitPidFlag::WNOHANG)).unwrap(){
                        Exited(child_pid, child_exit_code) => {
                            println!("{}, child exit {}, {}", getpid(), child_pid, child_exit_code)
                        }
                        _ => {
                            println!("not exited")
                        }
                    }
                    for i in 0..5{
                        println!("{}, I am a parent, child {}", getpid(), child);
                        sleep(1);
                    }
                }
            }
        },
        Err(e) => {
            println!("{}, fork error {}", getpid(), e)
        }


    }
}