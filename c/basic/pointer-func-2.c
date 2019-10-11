#include <stdio.h>
#include <stdlib.h>

/**
 * @str 原始字符串
 * @begin 截取起始index
 * @length 截取长度
 **/
char * subString(char str[], int begin, int length){
	static char tmp[100]; //不可以是变量

	int m = 0;
	for (int i=begin; m<length; m++,i++){
		tmp[m] = *(str + i);
	}
	tmp[m] = '\0';

	return tmp;
}

int main(){

	char *sub = subString("I love C !", 2,4);

	printf("%s", sub);
	
	return 0;
}
