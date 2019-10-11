#include <stdio.h>

/* 必须通过指针将原地址传过来
 * 因为函数的形参是一个新变量，拥有新的地址*/
void swap(int *a, int *b){
	int swap;
	swap = *a;
	*a = *b;
	*b = swap;
}

int main(){
	int a = 1, b = 2;

	swap(&a, &b);
	printf("a == %d\n", a);
	printf("b == %d\n", b);

	return 0;
}

