use nix::sys::signal::sigaction;
use nix::sys::signal::sigprocmask;
use nix::sys::signal::Signal;
use nix::sys::signal::SigAction;
use nix::sys::signal::SigHandler;
use libc;
use nix::sys::signal::SaFlags;
use nix::sys::signal::SigSet;
use std::thread::sleep;
use std::time::Duration;
use nix::sys::signal::SigmaskHow;
use libc::sigsuspend;
use libc::sigpending;
use std::mem;
use std::mem::MaybeUninit;
use libc::sigemptyset;
use libc::sigaddset;

extern "C"
fn handler(sig: libc::c_int) {
    println!("I got signal {}", sig);


}




fn main() {


    let mut mask_old = SigSet::empty();
    //获取mask
    sigprocmask(SigmaskHow::SIG_BLOCK, None, Some(&mut mask_old));
    println!("mask_old = {:?}", mask_old);


    unsafe {

        //获取pending的signal
        let mut sigset_pending_raw: libc::sigset_t = MaybeUninit::uninit().assume_init();
        sigpending(&mut sigset_pending_raw);
        let int_is_pending = libc::sigismember(&sigset_pending_raw, libc::SIGINT);
        println!("int is pending = {}", int_is_pending);




        //捕获libc::SIGINT并处理
        let handler = SigHandler::Handler(handler);
        let action = SigAction::new(handler, SaFlags::SA_RESETHAND, SigSet::empty());
        let action_old = sigaction(Signal::SIGINT, &action);


        //获取到libc::SIGINT前挂起
//        let mut sig_suspend: libc::sigset_t =  MaybeUninit::uninit().assume_init();
//        sigemptyset(&mut sig_suspend);
//        sigaddset(&mut sig_suspend, libc::SIGINT);
//        sigsuspend(&sig_suspend);
    }




    loop {
        println!("hello world!");
        sleep(Duration::from_secs(1));
    }
}