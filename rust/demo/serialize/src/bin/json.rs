use serde_json::error::Result;
use serde_json::Value;

fn main() {
    real().unwrap();
}

fn real() -> Result<usize> {
    let data = r#"
        {
            "name": "John Doe",
            "age": 43,
            "phones": [
                "+44 1234567",
                "+44 2345678"
            ]
        }"#;

    // Parse the string of data into serde_json::Value.
    let v: Value = serde_json::from_str(data)?;

    // Access parts of the data by indexing with square brackets.
    println!("Please call {} at the number {}", v["name"], v["phones"][0]);

    let text = serde_json::to_string(&v)?;
    println!("text = {}", text);

    return Ok(0);
}
