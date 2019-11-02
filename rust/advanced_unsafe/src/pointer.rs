#[cfg(test)]
mod tests {
    #[test]
    fn work() {
        let mut num = 5;

        let r1 = &num as *const i32;
        let r2 = &mut num as *mut i32;

        unsafe {
            println!("r1 is: {}", *r1);
            println!("r2 is: {}", *r2);
        }

        let address = 0b00011000usize;
        let _r = address as *const i32;
    }
}
