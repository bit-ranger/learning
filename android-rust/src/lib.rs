use jni::JNIEnv;
use jni::objects::JClass;

pub extern "system" fn Java_com_github_xxx_yyy_MainActivity_init() {
    // TODO
}

pub extern "system" fn Java_com_github_xxx_yyy_MainActivity_stringFromJNI(env: JNIEnv,
                                                                          _class: JClass)
                                                                          -> jstring {
    let hello = env.new_string("Hello from Rust")
        .expect("Couldn't create java string!");

    hello.into_inner()
}