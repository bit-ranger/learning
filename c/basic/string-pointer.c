#include <stdio.h>

int main(){
	char str[] = "I love C!";
	printf("%s\n", str);


	char *ps = "I love C too!";

	for (int i=0; i<13; i++){
		printf("%c", *(ps + i));
	}

	printf("%s\n", ps); //打印字符串需要指针

	return 0;
}
