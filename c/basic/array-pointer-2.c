#include <stdio.h>

int main(){
	char a[3] = {'A','B','C'};
	char (*p)[3] = &a;  //长度必须对应
	//char (*p)[3] = a; //警告，虽然&a和a值相同，但是类型不同，&a是整个数组的首地址，a是第一个元素的地址

	printf("%c", *(*p));
}
