#![no_std]

mod led;
mod startup;

use core::panic::PanicInfo;
use stm32f1::stm32f103::Peripherals;
use cortex_m::asm;


#[no_mangle]
#[inline(never)]
fn main() -> ! {
    let mut dp = Peripherals::take().unwrap();

    led::init(&mut dp.RCC, &mut dp.GPIOB);

    loop {
        // light on
        led::set(true);

        delay();

        // light off
        led::set(false);

        delay();
    }
}

fn delay() {
    let _ = asm::delay(2000000);
}

#[panic_handler]
pub unsafe extern "C" fn panic_fmt(_info: &PanicInfo) -> ! {
    loop {}
}