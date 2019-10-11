#include <stdio.h>

int main(){
	char a[3] = {'A', 'B', 'C'};
	char *p[3] = {&a[0], &a[1], &a[2]}; //指针数组，就是普通的数组，数组内的元素是指针

	for (int i=0; i<3; i++){
		printf("%c\n", *(p[i]));
	}

	char *ps[3] = {"jlkj",
		"jkjlj",
		"sdfsd"};

	for (int i=0; i<3; i++){
		printf("%s\n", ps[i]); //打印字符串需要的是一个指针
	}

	return 0;

}
