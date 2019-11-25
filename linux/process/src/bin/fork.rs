use nix::unistd::{fork, getpid};
use nix::unistd::ForkResult::{self, Parent, Child};

fn main() {


    match fork() {
        Ok(fr) => {
            match fr {
                Child => {
                    for i in 0..5{
                        println!("{}, I am a child", getpid())
                    }

                },
                Parent{child} => {
                    for i in 0..5{
                        println!("{}, I am a parent, child {}", getpid(), child)
                    }

                }
            }
        },
        Err(e) => {
            println!("{}, fork error {}", getpid(), e)
        }


    }
}