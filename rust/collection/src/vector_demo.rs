pub fn demo() {
    let v = vec![1, 2, 3, 4];
    let mut v: Vec<i32> = Vec::from(v);

    v.push(5);
    v.push(6);
    v.push(7);
    v.push(8);

    let third: &i32 = &v[2];
    println!("The third element is {}", third);

    match v.get(2) {
        Some(third) => println!("The third element is {}", third),
        None => println!("There is no third element."),
    }

    //遍历更新
    for i in &mut v {
        *i += 50;
    }

    //遍历
    for i in &v {
        println!("{}", i);
    }
}
