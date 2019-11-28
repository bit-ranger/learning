use nix::sys::epoll::{epoll_create1, epoll_ctl, epoll_wait, EpollCreateFlags, EpollOp, EpollEvent, EpollFlags};
use std::mem::MaybeUninit;
use nix::unistd::read;

fn main() {

    let efd = epoll_create1(EpollCreateFlags::EPOLL_CLOEXEC).unwrap();

    let fd = 0;
    let mut event_watch = EpollEvent::new(EpollFlags::EPOLLIN, fd);
    epoll_ctl(efd, EpollOp::EpollCtlAdd, fd as i32, Some(&mut event_watch)).unwrap();

    let mut events_active:[EpollEvent;1] = unsafe { MaybeUninit::uninit().assume_init() };
    let epoll_n = epoll_wait(efd, &mut events_active, -1).unwrap();

    for i in 0..epoll_n {
        let eve = events_active[i];
        let fd = eve.data();
        let mut buffer = [0u8;128];
        let nread = read(fd as i32, &mut buffer).unwrap();
        println!("{}", unsafe { String::from_raw_parts(buffer.as_mut_ptr(), nread, nread) });
    }



}