#include <stdio.h>

void main(){
    int a = 0;
    int *b = &a;
    printf("int size %ld\n", sizeof(a));
    printf("pointer size %ld\n", sizeof(b));
}