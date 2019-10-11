#include "pch.h"
#include <iostream>
using namespace std;

class Box
{
public:
	Box() {
		cout << "construct" << endl;
	}
	~Box() {
		cout << "destruct" << endl;
	}
};

int main()
{
	Box* myBoxArray = new Box[4];

	delete[] myBoxArray;
	return 0;
}
