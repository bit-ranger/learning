#include "pch.h"
#include <iostream>
using namespace std;

int main()
{
	// 带有 5 个元素的双精度浮点型数组
	double balance[5] = { 1000.0, 2.0, 3.4, 17.0, 50.0 };

	// 数组是个常量指针
	double *p = balance;

	// 输出数组中每个元素的值
	cout << "use pinter " << endl;
	for (int i = 0; i < 5; i++)
	{
		cout << "*(p + " << i << ") : ";
		cout << *(p + i) << endl;
	}

	cout << "use array " << endl;
	for (int i = 0; i < 5; i++)
	{
		cout << "p[" << i << "]) : ";
		cout << p[i] << endl;
	}

	return 0;
}
