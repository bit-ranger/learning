use stm32f1::stm32f103::{RCC, GPIOB};

pub fn init(rcc: &mut RCC, gpiob: &mut GPIOB) {
    // enable gpiob
    rcc.apb2enr.write(|w| w.iopben().enabled());

    // configurate PB12 as push-pull output
    gpiob.crh.write(|w| w.mode12().output50().cnf12().push_pull());
}

pub fn set(on: bool) {
    let gpiob = unsafe { &*GPIOB::ptr() };
    if on {
        gpiob.bsrr.write(|w| w.br12().reset());
    } else {
        gpiob.bsrr.write(|w| w.bs12().set());
    }
}