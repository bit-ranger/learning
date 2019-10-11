#include "pch.h"
#include <iostream>

using namespace std;

int main()
{
	enum color { RED, ORANGE = 6, YELLOW } c = ORANGE;
	color d = YELLOW;
	cout << c << endl;
	cout << d << endl;
	return 0;
}

