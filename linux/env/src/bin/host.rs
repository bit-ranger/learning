use nix::unistd::gethostname;
use nix::sys::utsname::uname;

fn main() {
    let mut utsname = [0u8; 128];
    let hostname_cstr =  gethostname(&mut utsname);

    println!("{}", hostname_cstr.unwrap().to_str().unwrap());

    let utsname = uname();

    println!("{}, {}, {}, {}, {}", utsname.sysname(), utsname.nodename(), utsname.release(), utsname.version(), utsname.machine())


}