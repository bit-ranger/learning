#include <stdio.h>

int main(){
	int arr[5],
		*p = NULL;

	p = arr;
	//等价于
	//p = &arr[0];


	//数组名是一个常量指针，指向数组第一个元素
	printf("%p\n",p);
	printf("%p\n",&p); //指针变量有自己的地址
	printf("%p\n",arr);
	printf("%p\n",&arr); //数组指向第一个元素，这第一个元素就存放在数组数组变量声明时的地址，可以说，数组指向自身。
	printf("%p\n",&arr[0]);


	for (int i=0; i<5; i++){
		*p = i;
		p ++;
	}
	p = p-5;
	for(int i=0; i<5; i++){
		printf("%d", *p);
		p ++;
	}

	printf("\n");

	for (int i=0; i<5; i++){
		printf("%d", *(arr+i)); //arr是一个常量指针，可读不可写
		printf("%d", arr[i]);  //两种方式等价
		printf("\n");
	}

	printf("-----------------------\n");


	int double_array[2][3],
		(*parr)[3] = double_array;
	//二维数组可以拆解成多个一维数组。
	//parr 是一个数组指针，指向一个长度为3的数组，该数组就是double_array指向的地址，double_array[0],
	//对 parr 指针求值，将得到一个普通指针，指向数组内的第一个元素。虽然求值前后的值相同，但是类型不同。
	//此时问题将分解成一维数组的问题。

	double_array[1][1] = 998;

	printf("%d\n",*(*(parr+1)+1));

	printf("%p\n",parr);
	printf("%p\n",double_array);  //二维数组名是一个常量指针，指向第一个一维数组，这是一个数组指针。
	printf("%p\n",&double_array[0][0]);


	printf("%p\n",*parr);
	printf("%p\n",double_array[0]); 
	printf("%p\n",&double_array[0][0]);

	printf("%p\n",*(parr+1));
	printf("%p\n",double_array[1]);
	printf("%p\n",&double_array[1][0]);

	return 0;
}
