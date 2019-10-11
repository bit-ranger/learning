#include "pch.h"
#include "box.h"
#include <iostream>
using namespace std;

const int I = 2;

//如果已定义的函数多于一行，编译器会忽略 inline 限定符。
//在类定义中的定义的函数都是内联函数，即使没有使用 inline 说明符。
inline double learn::getLength(learn::Box box){
    return box.length;
}


int main( )
{

    learn::Box box(2,3,4);
    learn::Box box2 = box;


    cout << "length of box : " << learn::getLength(box2) << endl;
    cout << "volume of box : " << box2.getVolume() << endl;

    learn::Box *p_b = &box;
    cout << "volume of box : " << p_b->getVolume() << endl;

    cout << "object weight : " << p_b ->getWeight() << endl;

    cout << "object count : " << learn::Box::getCount() << endl;

    cout << I;

    return 0;
}
