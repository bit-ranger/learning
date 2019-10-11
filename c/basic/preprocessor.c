#include <stdio.h>

#define MAX_ARRAY_LENGTH 20 //结尾不可有分号

#undef  FILE_SIZE
#define FILE_SIZE 42

#ifndef MESSAGE
#define MESSAGE "You wish!"
#endif

//字符串常量化
#define message_for(a,b) \
	printf(#a " and " #b "\n")   

//标记粘贴
#define tokenpaster(n) \
	printf("token " #n " = %d\n", token##n);

//参数化宏
#define MAX(x,y) ((x) > (y) ? (x) : (y))

int main(){
	printf("File :%s\n" ,__FILE__ );//双下划线
	printf("Date :%s\n", __DATE__ );
	printf("Time :%s\n", __TIME__ );
	printf("Line :%d\n", __LINE__ );
	printf("ANSI :%d\n", __STDC__ );

	printf("MESSAGE :%s\n", MESSAGE );

	message_for(msgA,msgB);

	int token34 = 40;
	tokenpaster(34);

	printf("Max between 20 and 10 is %d\n", MAX(10, 20));  

	const int LENGTH = 10;
	printf("%d\n",LENGTH);
}

