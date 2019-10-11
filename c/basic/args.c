#include <stdio.h>
#include <stdarg.h>


//第一个参数必须为参数数量
int sum(int argCount, ...){
	va_list args;
	va_start(args,argCount);

	int sum = 0;
	for (int i=0; i<argCount; i++){
		sum += va_arg(args,int);
	}

	va_end(args);

	return sum; 
}


int main(){
	int num = sum(3, 1, 2, 3);
	printf("%d\n", num);
	return 0;
}
