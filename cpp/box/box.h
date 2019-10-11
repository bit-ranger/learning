#ifndef LEARNING_CPP_BOX_H
#define LEARNING_CPP_BOX_H

#include <iostream>
#include <string>

namespace learn {
	class Box;
}


using namespace learn;

class learn::Box {
    static int objectCount;

private:

    double length;
    double breath;
    double height;

public:

    //纯虚函数
    //virtual int area() = 0;

    //虚函数
    virtual double getVolume(void) {
        std::cout << "Box getVolume " << std::endl;
        return length * breath * height;
    }

    Box(double l, double b, double h) {
        std::cout << "Object is being created " << this << std::endl;
        length = l;
        breath = b;
        height = h;

        objectCount++;
    }

    ~Box() {
        std::cout << "Object is being deleted " << this << std::endl;
    }

    Box(const Box &box) {

        std::cout << "Object is being copied from " << &box << " to " << this << std::endl;
        height = box.height;
        breath = box.breath;
        length = box.length;

        objectCount++;
    }

    friend double getLength(Box box);

	constexpr int getWeight() {
		return 5;
	}

    static int getCount() {
        return objectCount;
    }
};
int Box::objectCount = 0L;

#endif
