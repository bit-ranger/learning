#include <iostream>
#include <windows.h>

int main()
{
	printf("char size = %zu\n", sizeof(char));
	printf("unsigned char size = %zu\n\n", sizeof(unsigned char));

	printf("short size = %zu\n", sizeof(short));
	printf("unsigned short size = %zu\n\n", sizeof(unsigned short));

	printf("int size = %zu\n", sizeof(int));
	printf("unsigned int size = %zu\n\n", sizeof(unsigned int));

	printf("long size = %zu\n", sizeof(long));
	printf("unsigned long size = %zu\n\n", sizeof(unsigned long));

	printf("long long size = %zu\n", sizeof(long long));
	printf("unsigned long long size = %zu\n\n", sizeof(unsigned long long));

	printf("float size = %zu\n", sizeof(float));
	// 非标准
	//printf("long float size = %zu\n\n", sizeof(long float)); 

	printf("double size = %zu\n", sizeof(double));
	printf("long double size = %zu\n\n", sizeof(long double));

	printf("BYTE size = %zu\n", sizeof(BYTE));
	printf("WORD size = %zu\n", sizeof(WORD));
	printf("DWORD size = %zu\n", sizeof(DWORD));

	int a;
	size_t b;
	printf("pointer size = %zu\n", sizeof(&a));
	printf("size_t size = %zu\n", sizeof(b));

	return 0;
}


