#[cfg(test)]
mod tests {
    pub trait Draw {
        fn draw(&self);
    }

    // 若使用泛型定义 Screen, components中的所有元素必须拥有相同的类型
    // 编译器为每一个被泛型类型参数代替的具体类型生成了非泛型的函数和方法实现。单态化所产生的代码进行 静态分发
    // 而使用 Box<dyn Draw>, 每个元素不需要具有相同的类型, 只要实现了Draw就行
    // 当使用 trait 对象时，Rust 必须使用动态分发。编译器无法知晓所有可能用于 trait 对象代码的类型
    //
    //    pub struct Screen<T: Draw> {
    //        pub components: Vec<T>,
    //    }
    //
    //    impl<T> Screen<T>
    //        where T: Draw {
    //        pub fn run(&self) {
    //            for component in self.components.iter() {
    //                component.draw();
    //            }
    //        }
    //    }

    pub struct Screen {
        pub components: Vec<Box<dyn Draw>>,
    }

    impl Screen {
        pub fn run(&self) {
            for component in self.components.iter() {
                component.draw();
            }
        }
    }

    pub struct Button {
        pub width: u32,
        pub height: u32,
        pub label: String,
    }

    impl Draw for Button {
        fn draw(&self) {
            // 实际绘制按钮的代码
        }
    }

    struct SelectBox {
        width: u32,
        height: u32,
        options: Vec<String>,
    }

    impl Draw for SelectBox {
        fn draw(&self) {
            // code to actually draw a select box
        }
    }

    #[test]
    fn it_works() {
        let screen = Screen {
            components: vec![
                Box::new(SelectBox {
                    width: 75,
                    height: 10,
                    options: vec![
                        String::from("Yes"),
                        String::from("Maybe"),
                        String::from("No"),
                    ],
                }),
                Box::new(Button {
                    width: 50,
                    height: 10,
                    label: String::from("OK"),
                }),
            ],
        };

        screen.run();
    }
}
