#[cfg(test)]
mod tests {
    use std::slice;

    unsafe fn dangerous() {}

    #[test]
    fn work() {
        unsafe {
            dangerous();
        }
    }

    fn split_at_mut(slice: &mut [i32], mid: usize) -> (&mut [i32], &mut [i32]) {
        let len = slice.len();
        let ptr = slice.as_mut_ptr();

        assert!(mid <= len);

        //        (&mut slice[..mid], &mut slice[mid..])

        unsafe {
            (
                slice::from_raw_parts_mut(ptr, mid),
                slice::from_raw_parts_mut(ptr.offset(mid as isize), len - mid),
            )
        }
    }

    #[test]
    fn work2() {
        let mut v = vec![1, 2, 3, 4, 5, 6];

        let r = &mut v[..];

        let (a, b) = split_at_mut(r, 3);

        assert_eq!(a, &mut [1, 2, 3]);
        assert_eq!(b, &mut [4, 5, 6]);
    }

    //导入C语言ABI
    extern "C" {
        fn abs(input: i32) -> i32;
    }

    //将call_from_c导出为C语言ABI
    #[no_mangle]
    pub extern "C" fn call_from_c() {
        println!("Just called a Rust function from C!");
    }

    #[test]
    fn work3() {
        unsafe {
            println!("Absolute value of -3 according to C: {}", abs(-3));
        }
    }

    mod static_demo {
        //静态变量只能储存拥有 'static 生命周期的引用
        //静态变量中的值有一个固定的内存地址。使用这个值总是会访问相同的地址。
        //常量则允许在任何被用到的时候复制其数据。
        static HELLO_WORLD: &str = "Hello, world!";

        #[test]
        fn work4() {
            println!("name is: {}", HELLO_WORLD);
        }

        //静态变量可以是可变的。访问和修改可变静态变量都是 不安全 的
        static mut COUNTER: u32 = 0;

        fn add_to_count(inc: u32) {
            unsafe {
                COUNTER += inc;
            }
        }

        #[test]
        fn work5() {
            add_to_count(3);

            unsafe {
                println!("COUNTER: {}", COUNTER);
            }
        }
    }

    mod trait_demo {
        unsafe trait Foo {
            // methods go here
        }

        //如果结构体中有个成员不是Send/Sync，比如裸指针，
        //需要手动Send 或 Sync，则必须使用 unsafe。
        unsafe impl Foo for i32 {
            // method implementations go here
        }
    }
}
