#include "pch.h"
#include <iostream>
#include <memory>

using namespace std;

class Test
{
public:
    explicit Test(const string s)
    {
        str_ = s;
        cout << "Test create\n";
    }
    ~Test()
    {
        cout << "Test delete:" << str_ << endl;
    }
    string& getStr()
    {
        return str_;
    }
    void setStr(string s)
    {
        str_ = s;
    }
    void print() const
    {
        cout << str_ << endl;
        cout.flush();
    }
private:
    string str_;
};

unique_ptr<Test> fun()
{
    return unique_ptr<Test>(new Test("move fun"));
}


class B;
class A
{
public:
    // weak_ptr
    weak_ptr<B> pb_;
    ~A()
    {
        cout << "A delete\n";
    }
};
class B
{
public:
    shared_ptr<A> pa_;
    ~B()
    {
        cout << "B delete\n";
    }

    void print()
    {
        cout << "B print\n";
    }
};

void funWeak()
{
    shared_ptr<B> pb(new B());
    shared_ptr<A> pa(new A());
    pb->pa_ = pa;
    pa->pb_ = pb;
    cout << pb.use_count() << endl;
    cout << pa.use_count() << endl;
    shared_ptr<B> pbl = pa->pb_.lock();
    pbl->print();
}

int main()
{

    // auto_ptr
    auto_ptr<Test> autoTest(new Test("123"));
    autoTest->setStr("hello ");
    autoTest->print();
    // 获取原始指针
    autoTest.get()->print();
    autoTest->getStr() += "world !";
    (*autoTest).print();
    // 重新绑定指向的对象，而原来的对象则会被释放
    autoTest.reset(new Test("123"));
    autoTest->print();
    // 只是把智能指针赋值为空，但是它原来指向的内存并没有被释放
    autoTest.release();


    // unique_ptr 独占
    unique_ptr<Test> uniqueTest(new Test("456"));
    unique_ptr<Test> uniqueTest2(new Test("789"));
    uniqueTest->print();
    uniqueTest2 = std::move(uniqueTest);//不能直接ptest2 = ptest
    if (uniqueTest == NULL)cout << "uniqueTest = NULL\n";
    Test* p = uniqueTest2.release();
    p->print();
    uniqueTest.reset(p);
    uniqueTest->print();
    uniqueTest2 = fun(); //这里可以用=，因为使用了unique_ptr的移动构造函数
    uniqueTest2->print();



    // shared_ptr 计数
    shared_ptr<Test> sharedTest(new Test("shared123"));
    shared_ptr<Test> sharedTest2(new Test("shared456"));
    cout << sharedTest2->getStr() << endl;
    cout << "sharedTest2 use count " <<  sharedTest2.use_count() << endl;
    sharedTest = sharedTest2;//"456"引用次数加1，“123”销毁
    sharedTest->print();
    cout << "sharedTest2 use count " << sharedTest2.use_count() << endl;//2
    cout << "sharedTest use count " << sharedTest.use_count() << endl;//2
    sharedTest.reset();
    sharedTest2.reset();//此时“456”销毁
    cout << "done !\n";


    //weak_ptr 
    funWeak();
    return 0;
}

