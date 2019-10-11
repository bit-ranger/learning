#include <stdio.h>

int main(){
    //对指针赋值有两种形式，
	//第一种是在定义时赋值，
	//第二种是赋值语句，
	//两种写法有区别
	int a=0, *pa=&a, *pb;

	pb = &a; //在非定义中对指针赋值，
			 //注意前面没有*号,如果有*号，表示对指向的地址赋值，
			 //指针在未初始化时，有一个垃圾地址，对这个地址赋值非常危险。

	printf("a   == %d\n",a);//对指针取值
	printf("*pa == %d\n",*pa);//对指针取值
	printf("*pb == %d\n",*pb);//对指针取值
	printf("a   -> %p\n", &a);//取地址
	printf("pa  -> %p\n", pa);//取地址
	printf("pb  -> %p\n", pb);//取地址

	printf("-------change a----------\n");

	a = 998;
	printf("a   == %p\n", &a);//a地址未变

	printf("-------change *pa--------\n");

	*pa = 123;              //在非定义语句中,这是对指针指向的地址赋值
	printf("a   == %d\n", a);
	printf("*pa == %d\n", *pa);
	printf("*pb == %d\n", *pb);
	printf("a   -> %p\n", &a);
	printf("pa  -> %p\n", pa);
	printf("pb  -> %p\n", pb);

	printf("-----------------------\n");

    int d, *pd;         //只要声明了，就有地址
	printf("d  -> %p\n", &d);
	printf("pd -> %p\n", pd);

	//指针长度
	printf("ln == %d\n", sizeof(d));

	printf("&pd -> %p\n", &pd);
}
