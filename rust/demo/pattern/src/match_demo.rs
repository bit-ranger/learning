#[cfg(test)]
mod tests {
    #[test]
    fn work() {
        let x = 1;

        match x {
            // | 匹配多个
            1 | 2 => println!("one or two"),
            3 => println!("three"),
            _ => println!("anything"),
        }

        let x = 5;
        match x {
            // | 匹配范围
            // 范围只允许用于数字或 char 值，因为编译器会在编译时检查范围不为空
            // char 和 数字值是 Rust 仅有的可以判断范围是否为空的类型。
            1..=5 => println!("one through five"),
            _ => println!("something else"),
        }

        let x = 'c';
        match x {
            'a'..='j' => println!("early ASCII letter"),
            'k'..='z' => println!("late ASCII letter"),
            _ => println!("something else"),
        }
    }

    struct Point {
        x: i32,
        y: i32,
    }

    #[test]
    fn work2() {
        let p = Point { x: 0, y: 7 };

        // 结构结构体
        // 字段名不同
        //        let Point { x: a, y: b } = p;
        //        assert_eq!(0, a);
        //        assert_eq!(7, b);

        // 字段名相同
        //        let Point { x: x, y: y } = p;
        //        assert_eq!(0, x);
        //        assert_eq!(7, y);

        // 字段名简写
        let Point { x, y } = p;
        assert_eq!(0, x);
        assert_eq!(7, y);
    }

    #[test]
    fn work3() {
        let p = Point { x: 0, y: 7 };

        // 解构并匹配字段
        match p {
            Point { x, y: 0 } => println!("On the x axis at {}", x),
            Point { x: 0, y } => println!("On the y axis at {}", y),
            Point { x, y } => println!("On neither axis: ({}, {})", x, y),
        }
    }

    #[test]
    fn work4() {
        //结构结构体和元组
        let ((_feet, _inches), Point { x: _, y: _ }) = ((3, 10), Point { x: 3, y: -10 });
    }

    mod ignore {
        // 让编译器忽略警告
        fn foo(_: i32, y: i32) {
            println!("This code only uses the y parameter: {}", y);
        }

        #[test]
        fn work5() {
            foo(3, 4);
        }

        #[test]
        fn work6() {
            let mut setting_value = Some(5);
            let new_setting_value = Some(10);

            // 忽略枚举值
            match (setting_value, new_setting_value) {
                (Some(_), Some(_)) => {
                    println!("Can't overwrite an existing customized value");
                }
                _ => {
                    setting_value = new_setting_value;
                }
            }

            println!("setting is {:?}", setting_value);
        }

        #[test]
        fn work7() {
            let numbers = (2, 4, 8, 16, 32);

            // 忽略位置参数
            match numbers {
                (first, _, third, _, fifth) => {
                    println!("Some numbers: {}, {}, {}", first, third, fifth)
                }
            }
        }

        #[test]
        fn work8() {
            // 忽略未使用的变量
            // _x 仍会将值绑定到变量，而 _ 则完全不会绑定
            let _x = 5;
            let _y = 10;
        }

        #[test]
        fn work9() {
            struct Point {
                x: i32,
                y: i32,
                z: i32,
            }

            let origin = Point { x: 0, y: 0, z: 0 };

            match origin {
                // 忽略剩余的字段
                Point { x, .. } => println!("x is {}", x),
            }

            let numbers = (2, 4, 8, 16, 32);
            match numbers {
                (first, .., last) => {
                    println!("Some numbers: {}, {}", first, last);
                }
            }
        }
    }

    mod enum_mod {
        enum Message {
            Quit,
            Move { x: i32, y: i32 },
            Write(String),
            ChangeColor(i32, i32, i32),
        }

        #[test]
        fn work4() {
            let msg = Message::ChangeColor(0, 160, 255);

            match msg {
                Message::Quit => println!("The Quit variant has no data to destructure."),
                Message::Move { x, y } => {
                    println!("Move in the x direction {} and in the y direction {}", x, y);
                }
                Message::Write(text) => println!("Text message: {}", text),
                Message::ChangeColor(r, g, b) => {
                    println!("Change the color to red {}, green {}, and blue {}", r, g, b)
                }
            }
        }
    }

    mod enum_nested_mod {
        enum Color {
            Rgb(i32, i32, i32),
            Hsv(i32, i32, i32),
        }

        enum Message {
            Quit,
            Move { x: i32, y: i32 },
            Write(String),
            ChangeColor(Color),
        }

        #[test]
        fn main() {
            let msg = Message::ChangeColor(Color::Hsv(0, 160, 255));

            //嵌套的模式匹配
            //rust enum 是ADT algebraic data type
            match msg {
                Message::ChangeColor(Color::Rgb(r, g, b)) => {
                    println!("Change the color to red {}, green {}, and blue {}", r, g, b)
                }
                Message::ChangeColor(Color::Hsv(h, s, v)) => println!(
                    "Change the color to hue {}, saturation {}, and value {}",
                    h, s, v
                ),
                _ => (),
            }
        }
    }

    mod guard {
        #[test]
        fn work() {
            let num = Some(4);

            match num {
                Some(x) if x < 5 => println!("less than five: {}", x),
                Some(x) => println!("{}", x),
                None => (),
            }
        }

        #[test]
        fn work2() {
            let x = Some(5);
            let y = 10;

            match x {
                Some(50) => println!("Got 50"),
                Some(n) if n == y => println!("Matched, n = {:?}", n),
                _ => println!("Default case, x = {:?}", x),
            }

            println!("at the end: x = {:?}, y = {:?}", x, y);
        }

        #[test]
        fn work3() {
            let x = 4;
            let y = false;

            match x {
                //匹配守卫的条件会作用于所有的模式
                // 优先级 (4 | 5 | 6) if y => ...
                4 | 5 | 6 if y => println!("yes"),
                _ => println!("no"),
            }
        }
    }

    mod bind {
        #[test]
        fn work() {
            enum Message {
                Hello { id: i32 },
            }

            let msg = Message::Hello { id: 5 };

            match msg {
                // 匹配后绑定到id_variable
                Message::Hello {
                    id: id_variable @ 3..=7,
                } => println!("Found an id in range: {}", id_variable),
                // 使用...后不能绑定到变量, 需要绑定必须@
                Message::Hello { id: 10..=12 } => println!("Found an id in another range"),
                Message::Hello { id } => println!("Found some other id: {}", id),
            }
        }
    }
}
