#include <stdio.h>
//#include "extern-1.c"
//#include "extern-2.c"

int test(); //先定义后使用

int main(){
	extern int a; //从外部文件中查找,此处没有外部文件，编译器应有警告

	printf("%d\n", a);
	printf("%d\n", test());

	
	return 0;
}


int test(){
	return 123;
}
