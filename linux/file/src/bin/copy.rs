use nix::fcntl::open;
use nix::unistd::{close, read, write};
use nix::fcntl::OFlag;
use nix::sys::stat::Mode;

fn main() {

    let mut buf = [0u8; 1024];
    let infile = open("file.in", OFlag::O_RDONLY, Mode::S_IRUSR).unwrap();
    let outfile = open("file.out", OFlag::O_WRONLY | OFlag::O_CREAT, Mode::S_IRUSR | Mode::S_IWUSR).unwrap();
    loop {
        match read(infile, &mut buf){
            Ok(nread) => {
                if nread > 0 {
                    write(outfile, &buf[..nread]);
                } else {
                    break;
                }
            },
            Err(e) => {
                println!("error {:?}", e)
            }
        }
    }

    close(infile);
}