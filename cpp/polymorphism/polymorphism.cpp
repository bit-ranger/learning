#include "pch.h"
#include <iostream>
#include <string>

using namespace std;

class Integer{

private:
	int value;
	
public:
	
	Integer(int value);

	string toString();

	Integer operator + (Integer other);

	Integer operator ++ ();

	Integer operator ++ (int);

	friend Integer operator<< (Integer a, Integer b);
};



Integer::Integer(int value)
{
	cout << "Integer construct" << endl;
	this->value = value;
}

string Integer::toString()
{
	return to_string(this->value);
}

Integer Integer::operator+(Integer other)
{
	return Integer(this->value + other.value);
}

Integer Integer::operator++()
{
	return Integer(this->value + 1);
}

Integer Integer::operator++(int)
{
	Integer old = *this;
	++(*this);
	return old;
}

Integer operator<< (Integer a, Integer b) {
	return Integer(a.value + b.value);
}


int main()
{
	Integer a(1);
	cout << "a: " + a.toString() << endl;

	cout << "a+a: " + (a+a).toString() << endl;

	cout << "++a: " + (++a).toString() << endl;

	cout << "a++: " + (a++).toString() << endl;

	cout << "a: " + a.toString() << endl;

	cout << "a << a: " + (a << a).toString() << endl;

	return 0;
}


