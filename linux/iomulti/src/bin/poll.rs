use nix::poll::{poll, PollFd, PollFlags};
use nix::unistd::read;

fn main() {

    let file = 0;

    let fds = [PollFd::new(file, PollFlags::POLLIN)];

    let mut fds_act = fds;
    poll(&mut fds_act, -1);

    for fd in fds_act.iter(){
        if let Some(flag) =  fd.revents(){
            if flag & PollFlags::POLLIN == PollFlags::POLLIN{
                let mut buffer = [0u8;128];
                let nread = read(file, &mut buffer).unwrap();
                println!("{}", unsafe { String::from_raw_parts(buffer.as_mut_ptr(), nread, nread) });
            }

        }
    }
}