#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn work() {
        let mut stack = Vec::new();

        stack.push(1);
        stack.push(2);
        stack.push(3);

        //只要模式匹配就一直进行 while 循环
        while let Some(top) = stack.pop() {
            println!("{}", top);
        }
    }
}
