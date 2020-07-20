extern crate dunce;

use std::{env, path::PathBuf};

fn main() {
    let root = PathBuf::from(env::var_os("CARGO_MANIFEST_DIR").unwrap());
    let library_dir = dunce::canonicalize(root.join("src")).unwrap();

    println!("cargo:rustc-link-lib=static={}", "hello_static");
    println!("cargo:rustc-link-lib={}", "hello_dynamic");
    println!(
        "cargo:rustc-link-search=native={}",
        env::join_paths(&[library_dir]).unwrap().to_str().unwrap()
    );
}
