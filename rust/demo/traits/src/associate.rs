#[cfg(test)]
mod tests {
    trait Animal {
        fn baby_name() -> String;
    }

    struct Dog;

    impl Dog {
        //关联函数没有self
        fn baby_name() -> String {
            String::from("Spot")
        }
    }

    impl Animal for Dog {
        fn baby_name() -> String {
            String::from("puppy")
        }
    }

    #[test]
    fn main() {
        println!("A baby dog is called a {}", Dog::baby_name());
        //        println!("A baby dog is called a {}", Animal::baby_name());
        println!("A baby dog is called a {}", <Dog as Animal>::baby_name());
    }
}
