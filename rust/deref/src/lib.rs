#[cfg(test)]
mod tests {
    #[test]
    fn it_works() {
        let x = 5;
        let y = &x;

        assert_eq!(5, x);
        assert_eq!(5, *y);
    }

    #[test]
    fn it_works_2() {
        let x = 5;
        let y = Box::new(x);

        assert_eq!(5, x);
        assert_eq!(5, *y);
    }

    mod my_box {
        use std::ops::{Deref, DerefMut};

        struct MyBox<T>(T);

        impl<T> MyBox<T> {
            fn new(x: T) -> MyBox<T> {
                MyBox(x)
            }

            fn look(self) {
                let value = self.0;
                println!("{}", "look")
            }
        }

        impl<T> Deref for MyBox<T> {
            type Target = T;

            fn deref(&self) -> &T {
                // 通过引用访问字段，获取的是字段的引用
                let df = &self.0;
                df
            }
        }

        impl<T> DerefMut for MyBox<T> {
            fn deref_mut(&mut self) -> &mut T {
                let df = &mut self.0;
                df
            }
        }

        #[test]
        fn it_works() {
            let x = 5;
            let y = MyBox::new(x);
            //            let y = *y;

            assert_eq!(5, x);
            assert_eq!(5, *y);
        }

        fn hello(name: &str) {
            println!("Hello, {}!", name);
        }

        #[test]
        fn it_works_2() {
            let m = MyBox::new(String::from("Rust"));
            // 解引用强制多态
            hello(&(*m)[..]);
            hello(&(m.deref())[..]);
            hello(&m);
            m.look();
            //            m.look(); 上一个m.look已move ownership
        }
    }
}
