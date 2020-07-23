#[derive(Debug,Clone)]
pub struct Type {
    pub name: String,
    pub category: String,
    pub qualifier: Vec<String>,
    pub implements: Vec<String>,
    pub extends: Option<String>,
    pub annotation: Vec<String>,
    pub comment: Vec<String>,
    pub member: Vec<Member>,
}

#[derive(Debug,Clone)]
pub struct Method {
    pub name: String,
    pub is_abstract: bool,
    pub return_type: String,
    pub input_type: Vec<String>,
    pub qualifier: Vec<String>,
    pub annotation: Vec<String>,
    pub comment: Vec<String>,
}

#[derive(Debug,Clone)]
pub struct Field {
    pub name: String,
    pub data_type: String,
    pub qualifier: Vec<String>,
    pub annotation: Vec<String>,
    pub comment: Vec<String>,
}

#[derive(Debug,Clone)]
pub struct FileHead {
    pub comment: Vec<String>,
    pub import: Vec<String>,
    pub package: String,
}

#[derive(Debug,Clone)]
pub enum Member {
    Type(Type),
    Method(Method),
    Field(Field),
    FileHead(FileHead),
}
