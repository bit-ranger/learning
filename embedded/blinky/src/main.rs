#![no_std]
#![no_main]

extern crate panic_halt;
extern crate stm32f1xx_hal as hal;

use cortex_m::asm;
use cortex_m_rt::entry;
use hal::prelude::*;
use embedded_hal::digital::v2::OutputPin;
use hal::rcc::{Rcc, Clocks};
use hal::flash::Parts;

#[entry]
fn main() -> ! {
    // 获取 Peripherals
    let peripherals = hal::pac::Peripherals::take().unwrap();

    // 将 RCC 寄存器结构体转换为进一步抽象的 hal 结构体
    let mut rcc:Rcc = peripherals.RCC.constrain();

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


#[allow(dead_code)]
fn rcc_clock_init(rcc: Rcc, mut flash:  Parts) ->  Clocks{
    let clocks = rcc
        .cfgr
        .use_hse(8.mhz())   // 高速外部时钟源
        .sysclk(72.mhz())   // 系统时钟
        .hclk(72.mhz())     // AHB 高速总线
        .pclk1(36.mhz())    // APB1 低速外设总线
        .pclk2(72.mhz())    // APB2 高速外设总线
        .freeze(&mut flash.acr);    // 应用时钟配置
    return clocks;
}


//https://www.zhihu.com/column/embedded-rust
#[allow(dead_code)]
fn rcc_clock_init_1(rcc: &mut stm32f1::stm32f103::RCC, flash: &mut stm32f1::stm32f103::FLASH) {
    // 启动外部高速时钟 (HSE)
    rcc.cr.write(|w| w.hseon().set_bit());

    // 等待外部高速时钟稳定
    while !rcc.cr.read().hserdy().bit_is_set() {}

    // 设置分频 AHB(HCLK) = SYSCLK, APB1(PCLK1) = SYSCLK / 2, APB2(PCK2) = SYSCLK
    rcc.cfgr
        .write(|w| w.hpre().div1().ppre1().div2().ppre2().div1());

    // 设置锁相环为 9 x HSE
    rcc.cfgr
        .write(|w| w.pllsrc().hse_div_prediv().pllxtpre().div1().pllmul().mul9());

    // 设置 flash : two wait states, 启用预读取
    flash.acr.write(|w| w.latency().ws2().prftbe().set_bit());

    // 启动锁相环
    rcc.cr.write(|w| w.pllon().set_bit());

    // 等待锁相环锁定
    while !rcc.cr.read().pllrdy().bit_is_set() {}

    // 使用锁相环输出作为 SYSCLK
    rcc.cfgr.write(|w| w.sw().pll());

    // 等待 SYSCLK 切换为锁相环
    while !rcc.cfgr.read().sws().is_pll() {}
}