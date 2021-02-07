#![no_std]
#![no_main]

extern crate panic_halt;
extern crate stm32f1xx_hal as hal;

use cortex_m::asm;
use cortex_m_rt::entry;
use hal::prelude::*;
use embedded_hal::digital::v2::OutputPin;

#[entry]
fn main() -> ! {
    // 获取 Peripherals
    let peripherals = hal::pac::Peripherals::take().unwrap();

    // 将 RCC 寄存器结构体转换为进一步抽象的 hal 结构体
    let mut rcc = peripherals.RCC.constrain();

    // 获取 GPIOC 实例，这里会自动打开总线开关
    let mut gpio_c = peripherals.GPIOC.split(&mut rcc.apb2);

    // 获取 PC13 实例，并进行引脚配置
    let mut led = gpio_c.pc13.into_push_pull_output(&mut gpio_c.crh);

    //https://pic3.zhimg.com/80/v2-6165f61bc28c9e94b23d44cee04b0e2a_720w.jpg
    loop {
        delay();

        // 输出低电平
        let _ =led.set_low();

        delay();


        // 输出高电平
        let _ = led.set_high();
    }
}

fn delay() {
    let _ = asm::delay(2000000);
}