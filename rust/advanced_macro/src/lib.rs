extern crate proc_macro;

use proc_macro::TokenStream;

pub trait HelloMacro {
    fn hello_macro();
}

#[macro_export]
macro_rules! myvec {
    ( $( $x:expr ),* ) => {
        {
            let mut temp_vec = Vec::new();
            $(
                temp_vec.push($x);
            )*
            temp_vec
        }
    };
}

#[cfg(test)]
mod tests {
    #[test]
    fn it_works() {
        let l = myvec![1, 2, 3];
        assert_eq!(l[1], 2);
    }
}
