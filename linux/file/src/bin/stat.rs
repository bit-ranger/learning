use nix::sys::stat::{stat, SFlag};

fn main() {

    let fs = stat("file.in").unwrap();

//    let  modes = SFlag::from_bits(fs.st_mode);

//    if modes.contains(&SFlag::S_IFREG){
//        println!("is reg");
//    }


    let modes = fs.st_mode;
    let reg_bits = SFlag::S_IFREG.bits();
    if modes & reg_bits == reg_bits{
        println!("is reg");
    }
}