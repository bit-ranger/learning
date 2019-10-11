#include "pch.h"
#include <iostream>

using namespace std;



template <class E>
class Math {

public:

	E abs(E x) {
		return x < 0 ? -x : x;
	}

};

template <typename T>
T abs2(T x) {
	return x < 0 ? -x : x;
};

int main()
{
	int i = -5;
	double d = 6;
	cout << Math<int>().abs(i) << endl;
	cout << abs2(d) << endl;
	return 0;
}

