use std::fmt;

struct Wrapper(Vec<String>);

//因为 Wrapper 是一个新类型，它没有定义于其值之上的方法；
// 必须直接在 Wrapper 上实现 Vec<T> 的所有方法，这样就可以代理到self.0 上
//这就允许我们完全像 Vec<T> 那样对待 Wrapper
//如果希望新类型拥有其内部类型的每一个方法，为封装类型实现 Deref trait并返回其内部类型是一种解决方案
impl fmt::Display for Wrapper {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        write!(f, "[{}]", self.0.join(", "))
    }
}

fn main() {
    let w = Wrapper(vec![String::from("hello"), String::from("world")]);
    println!("w = {}", w);
}
