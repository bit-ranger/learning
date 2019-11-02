#[cfg(test)]
mod tests {
    fn add_one(x: i32) -> i32 {
        x + 1
    }

    //函数作为参数
    fn do_twice(f: fn(i32) -> i32, arg: i32) -> i32 {
        f(arg) + f(arg)
    }

    #[test]
    fn work() {
        let answer = do_twice(add_one, 5);

        println!("The answer is: {}", answer);
    }

    #[test]
    fn work2() {
        let list_of_numbers = vec![1, 2, 3];
        let list_of_strings: Vec<String> = list_of_numbers.iter().map(|i| i.to_string()).collect();
    }

    #[test]
    fn work3() {
        let list_of_numbers = vec![1, 2, 3];
        let list_of_strings: Vec<String> =
            list_of_numbers.iter().map(ToString::to_string).collect();
    }

    #[test]
    fn work4() {
        //Rust 并不知道需要多少空间来储存闭包
        //将闭包放在指针之后
        fn returns_closure() -> Box<dyn Fn(i32) -> i32> {
            Box::new(|x| x + 1)
        }
    }
}
