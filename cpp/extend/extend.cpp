#include "pch.h"
#include <iostream>
#include <string>

using namespace std;

class Box {
protected:
    unsigned int weight;

public:
    Box(const unsigned int weight);

    Box(Box &box);

    ~Box();
};

class Color {
protected:
    string *color;
public:
    Color(string *color);

    ~Color();

    // 纯虚函数
    virtual string * getColor() = 0;
};

//public 继承, private 成员不可直接访问, public protected 成员保持不变
//private 继承, private 成员不可直接访问, public protected 成员以 private 出现在派生类中
//protected 继承, private 成员不可直接访问, public protected 成员以 protected 出现在派生类中
//virtual 继承, 所有成员只在最远派生类中存在一份, 不存在覆盖
class NameColorBox : public Box, virtual Color {
private:
    string *name;

public:
    // C++11 语法, 继承基类的构造函数
    using Box::Box;
    using Color::Color;

    NameColorBox(
        const unsigned int weight,
        string *name,
        string *color);

    NameColorBox(NameColorBox &ncb);

    ~NameColorBox();

    string * getName();

    string * getColor() override;
};

Box::Box(const unsigned int weight) 
{
    this->weight = weight;
    cout << "Box construct" << endl;
}

Box::~Box() 
{
    cout << "Box destruct" << endl;
}

Color::Color(string *color) 
{
    this->color = color;
    cout << "Color construct" << endl;
}

Color::~Color()
{
    cout << "Color destruct" << endl;
}


// 基类构造函数的调用顺序,在声明继承时确定, 与此处赋值顺序无关
// 若为virtual继承, 每个派生类都必须调用最顶层的基类, 且只有最远派生类的构造函数中对顶层基类的构造函数的调用会执行, 中间类对顶层构造函数的调用将忽略
NameColorBox::NameColorBox(
    const unsigned int weight, 
    string *name, 
    string *color):
    Color(color),
    Box(weight),
    // 初始化成员时， 只能调用成员的构造函数， 无法赋值
    name(nullptr) 
{
    // 释放构造时创建的对象
    delete this->name;
    this->name = name;
    cout << "NameColorBox construct" << endl;
}

// 基类析构函数的调用顺序, 与声明时相反
NameColorBox::NameColorBox(NameColorBox &ncb):
    // 调用Box的构造函数
    Box(ncb.weight),
    // 调用Color默认复制构造函数
    Color(ncb)
{
    this->name = ncb.name;
}

NameColorBox::~NameColorBox()
{
    cout << "NameColorBox destruct" << endl;
}

string * NameColorBox::getName()
{
    return this->name;
}

string * NameColorBox::getColor()
{
    // 派生类成员存在覆盖时, 指定基类成员
    return this->color;
}


int main()
{
    string name = "box1";
    string color = "red";
    NameColorBox box(998, &name, &color);
    std::cout << "name " +  *box.getName() + " , color: " + *box.getColor() << endl; 
}

