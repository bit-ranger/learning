#include "pch.h"
#include "smtptr.cpp"
#include <string>

using namespace std;


class Sample {

public:

	~Sample()
	{
		cout << "release Sample\n";
	}

	void doSth() {}
};


int main() {

	Sample *s = new Sample;

	// 构造
	auto s1 = SmartPtr<Sample>(s);

	cout << "s1 " << s1.useCount() << endl;

	// 拷贝构造
	auto s2 = s1;

	cout << "s2 " << s2.useCount() << endl;

	// 移动构造
	auto s3 = move(s2);

	cout << "s3 " << s3.useCount() << endl;

	// 构造，移动赋值
	s1 = SmartPtr<Sample>(s);

	cout << "s1 " << s1.useCount() << endl;

	// 拷贝赋值
	//s1 = s2;
	s1 = s3;

	cout << "s1 " << s1.useCount() << endl;

	// 指针访问
	s1->doSth();
	// 取内容
	(*s1).doSth();

	cout << "use count " << s1.useCount() << endl;
}