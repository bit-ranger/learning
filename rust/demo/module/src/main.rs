pub use front_of_house::{hosting, mm};

mod front_of_house;

pub fn main() {
    hosting::add_to_wait_list();
    mm::print_mm();
}
