use nix::unistd::read;
use nix::unistd::write;

fn main() {
    let mut buffer = [0u8;128];

    match read(0, &mut buffer){
        Ok(nread) => {
            match  write(1, &buffer[..nread]){
                Ok(nwrite) => {
                    if nread != nwrite{
                        write( 2, format!("nwrite {}", nwrite).as_bytes());
                        write( 2, format!("nwrite {}", nread).as_bytes());
                    }

                },
                Err(err) => {
                    write( 2, "A write error has occurred\n".as_bytes());
                }
            }
        },
        Err(err) => {
            write( 2, "A read error has occurred\n".as_bytes());
        }
    }


}