//! layout.ld

MEMORY
{
    FLASH (rx) : ORIGIN = 0x08000000, LENGTH = 64K
    RAM (rwx) : ORIGIN = 0x20000000, LENGTH = 20K
}

ENTRY(reset_handler);

SECTIONS
{
    _sstack = ORIGIN(RAM) + LENGTH(RAM);

    .isr_vector :
    {
        . = ALIGN(4);

        /* Initial Stack Pointer (SP) value */
        LONG(_sstack);

        KEEP(*(.isr_vector))

        . = ALIGN(4);
    } > FLASH

    .text :
    {
        . = ALIGN(4);

        *(.text .text.*)
        *(.rodata .rodata.*)

        . = ALIGN(4);

    } > FLASH

    .data :
    {
        . = ALIGN(4);

        *(.data)
        *(.data.*)

        . = ALIGN(4);
    } > RAM AT > FLASH

    /* VMA of .data */
    _sdata = ADDR(.data);
    _edata = ADDR(.data) + SIZEOF(.data);

    /* LMA of .data */
    _sidata = LOADADDR(.data);

    .bss (NOLOAD) :
    {
        . = ALIGN(4);

        *(.bss)
        *(.bss.*)

        . = ALIGN(4);
    } > RAM

    _sbss = ADDR(.bss);
    _ebss = ADDR(.bss) + SIZEOF(.bss);
}