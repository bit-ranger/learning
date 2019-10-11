#include <stdio.h>
int main( )
{
	int c;
	printf( "Enter a value :");
	c = getchar( ); //阻塞读取字符
	printf( "\nYou entered: ");
	putchar( c );   //输出字符


	char str[100];
	/*
	printf( "Enter a value :");
	gets( str );    //危险，不该使用
	printf( "\nYou entered: ");
	puts( str );
	*/

	int i;
	printf( "Enter a value :");
	scanf("%s %d", str, &i);
	printf( "\nYou entered: %s %d ", str, i);
	return 0;

}
