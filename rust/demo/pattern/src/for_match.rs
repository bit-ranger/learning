#[cfg(test)]
mod tests {
    use std::iter::Iterator;

    #[test]
    fn work() {
        let v = vec!['a', 'b', 'c'];

        for (index, value) in v.iter().enumerate() {
            println!("{} is at index {}", value, index);
        }
    }
}
