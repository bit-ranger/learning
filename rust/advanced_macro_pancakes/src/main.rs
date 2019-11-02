use advanced_macro::HelloMacro;
use advanced_macro_derive::HelloMacro;

#[derive(HelloMacro)]
struct Pancakes;

fn main() {
    Pancakes::hello_macro();
}

//@see hello_macro_derive 将生成如下方注释的内容
//#[derive(HelloMacro)]
//struct Pancakes;

//impl HelloMacro for Pancakes {
//    fn hello_macro() {
//        println!("Hello, Macro! My name is Pancakes!");
//    }
//}
