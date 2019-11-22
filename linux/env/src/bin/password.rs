use libc::{termios, tcgetattr, tcsetattr, STDIN_FILENO, ECHO, TCSAFLUSH, fgets, puts};
use std::mem;
use std::mem::MaybeUninit;
use std::ptr::copy;
use std::os::raw::c_char;

fn main() {

    unsafe {
        let mut termios_origin:termios =  MaybeUninit::uninit().assume_init();
        tcgetattr(STDIN_FILENO, &mut termios_origin);

        let mut termios_new:termios =  MaybeUninit::uninit().assume_init();
        copy(&mut termios_origin, &mut termios_new, mem::size_of::<termios>());
        termios_new.c_cflag &= !ECHO;
        tcsetattr(STDIN_FILENO, TCSAFLUSH, & termios_new);

        println!("please enter password");


//        let mut input_passord:[c_char;2] = [0; 2];
//        fgets(input_passord.as_mut_ptr(), input_passord.len() as i32, );
//
//
//        println!("{}", input_password);

        tcsetattr(STDIN_FILENO, TCSAFLUSH, & termios_origin);

    }


}