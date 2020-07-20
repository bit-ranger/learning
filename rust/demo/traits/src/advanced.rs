#[cfg(test)]
mod tests {
    //若使用泛型，必须提供类型注解来表明希望使用 Iterator 的哪一个实现
    //关联类型更通用
    pub trait Iterator {
        type Item;

        fn next(&mut self) -> Option<Self::Item>;
    }
}
