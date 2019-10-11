#include <stdio.h>


int num = 998; //global



void test(){
	int auto  num = 123; //默认为auto
	register int num2 = 456; //寄存器变量，快速

	printf("%d\n", num);

	static int stc = 0; //静态变量
	stc += 1;
	printf("%d\n", stc);
}

int main(){
	test();
	test();
	printf("%d\n", num);
	printf("%s\n","你好，全世界！");
}





