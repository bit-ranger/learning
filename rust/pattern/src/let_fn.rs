#[cfg(test)]
mod tests {
    use super::*;

    fn print_coordinates(&(x, y): &(i32, i32)) {
        println!("Current location: ({}, {})", x, y);
    }

    fn print_coordinates2((x, y): (i32, i32)) {
        println!("Current location: ({}, {})", x, y);
    }

    #[test]
    fn work() {
        let point = (3, 5);
        print_coordinates(&point);
        print_coordinates2(point);
    }
}
