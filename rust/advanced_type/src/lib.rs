#[cfg(test)]
mod tests {
    //类型别名
    type Kilometers = i32;

    //    fn takes_long_type(f: Box<dyn Fn() + Send + 'static>) {
    //        // --snip--
    //    }
    //
    //    fn returns_long_type() -> Box<dyn Fn() + Send + 'static> {
    //        // --snip--
    //    }

    #[test]
    fn it_works() {
        let f: Box<dyn Fn() + Send + 'static> = Box::new(|| println!("hi"));
    }
}

#[cfg(test)]
mod alais {
    //类型别名
    type Thunk = Box<dyn Fn() + Send + 'static>;

    //    fn takes_long_type(f: Thunk) {
    //        // --snip--
    //    }
    //
    //    fn returns_long_type() -> Thunk {
    //        // --snip--
    //    }

    #[test]
    fn work() {
        let f: Thunk = Box::new(|| println!("hi"));
    }
}

#[cfg(test)]
mod never {
    //发散函数
    //    fn bar() -> ! {
    //        // --snip--
    //    }

    #[test]
    fn work() {
        //continue 的值是 !
        //never type 可以强转为任何其他类型
        //panic! 是 ! 类型
        //        let guess: u32 = match guess.trim().parse() {
        //            Ok(num) => num,
        //            Err(_) => continue,
        //        };
    }
}

#[cfg(test)]
mod dynamic {
    fn generic<T>(t: T) {
        // --snip--
    }

    fn generic2<T: Sized>(t: T) {
        // --snip--
    }

    //泛型函数默认只能用于在编译时已知大小的类型。然而可以使用如下特殊语法来放宽这个限制
    fn generic3<T: ?Sized>(t: &T) {
        // --snip--
    }

    #[test]
    fn work() {

        //必须将动态大小类型的值置于某种指针之后
        //        let s1: str = "Hello there!";
        //        let s2: str = "How's it going?";
    }
}
