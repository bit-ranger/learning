#[cfg(test)]
mod tests {
    use List::{Cons, Nil};

    #[test]
    fn it_works() {
        let b = Box::new(5);
        println!("b = {}", b);
    }

    enum List {
        Cons(i32, Box<List>),
        Nil,
    }

    fn it_works_2() {
        let _list = Cons(1, Box::new(Cons(2, Box::new(Cons(3, Box::new(Nil))))));
    }
}
