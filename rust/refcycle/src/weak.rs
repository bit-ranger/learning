#[cfg(test)]
mod test {
    use std::cell::RefCell;
    use std::rc::{Rc, Weak};

    #[derive(Debug)]
    struct Node {
        value: i32,
        parent: RefCell<Weak<Node>>,
        children: Vec<Rc<Node>>,
    }

    #[test]
    fn work() {
        let leaf = Rc::new(Node {
            value: 3,
            parent: RefCell::new(Weak::new()),
            children: vec![],
        });

        println!(
            "leaf strong = {}, weak = {}",
            Rc::strong_count(&leaf),
            Rc::weak_count(&leaf),
        );

        {
            let branch = Rc::new(Node {
                value: 5,
                parent: RefCell::new(Weak::new()),
                children: vec![Rc::clone(&leaf)],
            });

            //Rc::downgrade 传递 Rc 实例的引用来创建其值的 弱引用（weak reference）
            //不同于将 Rc<T> 实例的 strong_count 加一, 调用 Rc::downgrade 会将 weak_count 加一
            //weak_count 无需计数为 0 就能使 Rc 实例被清理
            *leaf.parent.borrow_mut() = Rc::downgrade(&branch);

            println!(
                "branch strong = {}, weak = {}",
                Rc::strong_count(&branch),
                Rc::weak_count(&branch),
            );

            println!(
                "leaf strong = {}, weak = {}",
                Rc::strong_count(&leaf),
                Rc::weak_count(&leaf),
            );
        }

        //调用 Weak<T> 实例的 upgrade 方法，这会返回 Option<Rc<T>>
        // 如果 Rc<T> 值还未被丢弃则结果是 Some，如果 Rc<T> 已经被丢弃则结果是 None
        println!("leaf parent = {:?}", leaf.parent.borrow().upgrade());

        println!(
            "leaf strong = {}, weak = {}",
            Rc::strong_count(&leaf),
            Rc::weak_count(&leaf),
        );
    }
}
