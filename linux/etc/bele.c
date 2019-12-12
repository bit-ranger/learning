#include <stdio.h>

void main(){
    short a = 0x0102;
    char *b = &a;
    if (*b == (char)0x01) {
        printf("be \n");
    }

    if (*b == (char)0x02){
        printf("le \n");
    }

}
