#include <stdio.h>

struct{
	int a;
	int b;
}s1;

struct{
	int a:1;
	int b:3;
}s2;

int main(){
	printf("%d\n", sizeof(s1));
	printf("%d\n", sizeof(s2));

	s2.b = 7;
	printf( "s2.b : %d\n", s2.b );

	//超过3位，编译出错
//	s2.b = 8;
//	printf( "s2.b : %d\n", s2.b );
	return 0;
}


