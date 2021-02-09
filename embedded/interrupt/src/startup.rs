#[no_mangle]
pub unsafe extern "C" fn reset_handler() {
    init_data(&mut _sidata, &mut _sdata, &mut _edata);
    zero_bss(&mut _sbss, &mut _ebss);

    crate::main();
}

// interrupt vector that will be linked to the very start of FLASH
#[link_section = ".isr_vector"]
#[used]
pub static ISR_VECTOR: [unsafe extern "C" fn(); 1] = [reset_handler];