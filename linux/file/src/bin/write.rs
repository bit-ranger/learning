use nix::unistd::write;

fn main() {
    if write(1, "Here is some data\n".as_bytes()).unwrap() != 18 {
        write( 2, "A write error has occurred on file descriptor 1\n".as_bytes());
    }

}
