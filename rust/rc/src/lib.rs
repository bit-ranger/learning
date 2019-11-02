#[cfg(test)]
mod tests {
    use std::ops::Deref;
    use std::rc::Rc;

    use List::{Cons, Nil};

    enum List {
        Cons(i32, Rc<List>),
        Nil,
    }

    impl List {
        fn value(&self) -> i32 {
            match *self {
                List::Cons(x, _) => x,
                _ => 0,
            }
        }
    }

    #[test]
    fn it_works() {
        let a = Rc::new(Cons(5, Rc::new(Cons(10, Rc::new(Nil)))));
        println!("count after creating a = {}", Rc::strong_count(&a));
        let _b = Cons(3, a.clone());
        println!("count after creating b = {}", Rc::strong_count(&a));
        {
            let _c = Cons(4, Rc::clone(&a));
            println!("count after creating c = {}", Rc::strong_count(&a));
        }
        println!("count after c goes out of scope = {}", Rc::strong_count(&a));
        println!("{}", a.deref().value());
        println!("{}", a.value());
        println!("{}", (*a).value());
    }
}
