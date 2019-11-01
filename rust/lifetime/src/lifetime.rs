#[cfg(test)]
mod tests {
    struct Context<'a>(&'a str);

    // 'b >= 'a
    struct Parser<'a, 'b: 'a> {
        context: &'a Context<'b>,
    }

    impl<'a, 'b> Parser<'a, 'b> {
        fn parse(&self) -> Result<(), &'b str> {
            Err(&self.context.0[1..])
        }
    }

    //获得了所有权
    fn parse_context(context: Context) -> Result<(), &str> {
        Parser { context: &context }.parse()
    }

    #[test]
    fn work() {}

    mod generic {
        //指定 T 中的任何引用需至少与 'a 存活的一样久
        // T >= 'a
        struct Ref<'a, T: 'a>(&'a T);

        //T 包含任何引用，他们必须有 'static 生命周期
        struct StaticRef<T: 'static>(&'static T);
    }

    mod trait_demo {
        trait Red {}

        struct Ball<'a> {
            diameter: &'a i32,
        }

        impl<'a> Red for Ball<'a> {}

        #[test]
        fn main() {
            let num = 5;

            let obj = Box::new(Ball { diameter: &num }) as Box<dyn Red>;
        }
    }

    mod noname {
        use std::fmt;
        use std::fmt::{Error, Formatter};

        struct StrWrap<'a>(&'a str);

        fn foo<'a>(string: &'a str) -> StrWrap<'a> {
            StrWrap(string)
        }

        fn foo2(string: &str) -> StrWrap<'_> {
            StrWrap(string)
        }

        fn foo3(string: &str) -> StrWrap {
            StrWrap(string)
        }

        //        // 冗余
        //        impl<'a> fmt::Debug for StrWrap<'a> {
        //            fn fmt(&self, f: &mut Formatter<'_>) -> Result<(), Error> {
        //                unimplemented!()
        //            }
        //        }
        //
        //        // 省略
        //        impl fmt::Debug for StrWrap<'_> {
        //            fn fmt(&self, f: &mut Formatter<'_>) -> Result<(), Error> {
        //                unimplemented!()
        //            }
        //        }

        // 省略
        impl fmt::Debug for StrWrap<'_> {
            fn fmt(&self, f: &mut Formatter<'_>) -> Result<(), Error> {
                unimplemented!()
            }
        }
    }
}
