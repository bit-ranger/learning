#[cfg(test)]
mod tests {
    trait Pilot {
        fn fly(&self);
    }

    trait Wizard {
        fn fly(&self);
    }

    struct Human;

    impl Pilot for Human {
        fn fly(&self) {
            println!("This is your captain speaking.");
        }
    }

    impl Wizard for Human {
        fn fly(&self) {
            println!("Up!");
        }
    }

    impl Human {
        fn fly(&self) {
            println!("*waving arms furiously*");
        }
    }

    #[test]
    fn main() {
        let person = Human;
        //重名时精确指定
        Pilot::fly(&person);
        Wizard::fly(&person);
        person.fly();
    }
}
