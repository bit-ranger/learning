// friend.cpp : 此文件包含 "main" 函数。程序执行将在此处开始并结束。
//

#include "pch.h"
#include <iostream>


using namespace std;
 
class Box
{
private:
   double width;
   friend class BoxFriend;
   friend void printWidth(Box box);
public:
	void setWidth(double wid);
};

// 成员函数定义
void Box::setWidth( double wid )
{
    width = wid;
}

// 请注意：printWidth() 不是任何类的成员函数
void printWidth( Box box )
{
   /* 因为 printWidth() 是 Box 的友元，它可以直接访问该类的任何成员 */
   cout << "Width of box : " << box.width <<endl;
}
 

class BoxFriend {

public:
	static void execute() {
		Box box;
		// 使用成员函数设置宽度
		box.setWidth(10.0);
		box.width = 11.0;

		// 使用友元函数输出宽度
		printWidth(box);
	}
};

// 程序的主函数
int main( )
{
	BoxFriend::execute();
	return 0;
}