use std::fmt::Display;

use summary::Summary;

mod add_overload;
mod advanced;
mod associate;
mod bound;
mod newtype;
mod samename;

mod summary;

struct NewsArticle {
    pub headline: String,
    pub location: String,
    pub author: String,
    pub content: String,
}

impl Summary for NewsArticle {
    fn summarize_author(&self) -> String {
        format!("@{}", self.author)
    }
}

struct Tweet {
    pub username: String,
    pub content: String,
    pub reply: bool,
    pub retweet: bool,
}

impl Summary for Tweet {
    fn summarize_author(&self) -> String {
        format!("@{}", self.username)
    }
}

fn notify(item: impl Summary) {
    println!("Breaking news! {}", item.summarize());
}

fn notify_2(item1: impl Summary, item2: impl Summary) {
    println!("notify_2! {}", item1.summarize());
}

fn notify_3<T: Summary>(item_1: T, item2: T) {
    println!("notify_3! {}", item_1.summarize());
}

fn notify_4(item: impl Summary + Display) {
    println!("notify_4! {}", item.summarize());
}

fn notify_5<T: Summary + Display>(item: T) {
    println!("notify_5! {}", item.summarize());
}

fn notify_6<T, U>(t: T, u: U) -> i32
where
    T: Display + Clone,
    U: Clone + Display,
{
    println!("notify_5! {}", t.to_string());
    1
}

fn mk_summary() -> impl Summary {
    NewsArticle {
        headline: String::from("Penguins win the Stanley Cup Championship!"),
        location: String::from("Pittsburgh, PA, USA"),
        author: String::from("Iceburgh"),
        content: String::from(
            "The Pittsburgh Penguins once again are the best
            hockey team in the NHL.",
        ),
    }
}

fn mk_tweet() -> impl Summary {
    Tweet {
        username: String::from("horse_ebooks"),
        content: String::from("of course, as you probably already know, people"),
        reply: false,
        retweet: false,
    }
}

fn largest<T: PartialOrd + Copy>(list: &[T]) -> T {
    let mut largest = list[0];

    for &item in list.iter() {
        if item > largest {
            largest = item;
        }
    }

    largest
}

struct Pair<T> {
    x: T,
    y: T,
}

impl<T> Pair<T> {
    fn new(x: T, y: T) -> Self {
        Self { x, y }
    }
}

impl<T: Display + PartialOrd> Pair<T> {
    fn cmp_display(&self) {
        if self.x >= self.y {
            println!("The largest member is x = {}", self.x);
        } else {
            println!("The largest member is y = {}", self.y);
        }
    }
}

//impl<T: Display> ToString for T {
//    fn to_string(&self) -> String {
//        unimplemented!()
//    }
//}

fn max<T: PartialOrd>(list: &Vec<T>) -> &T {
    let mut largest = list.get(0).unwrap();

    for item in list.iter() {
        if &item > &largest {
            largest = item;
        }
    }

    largest
}

fn main() {
    let tweet = mk_summary();

    println!("1 new tweet: {}", tweet.summarize());

    let article = mk_tweet();

    println!("New article available! {}", article.summarize());

    notify(article);

    let char_list = vec!['y', 'm', 'a', 'q', 'z'];
    let result = largest(&char_list);
    println!("The largest char is {}", result);

    let result = max(&char_list);
    println!("the max is {}", result);
}
