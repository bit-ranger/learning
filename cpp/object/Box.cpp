#include "pch.h"
#include "Box.h"
#include <iostream>

using namespace learnObject;
using namespace std;

Box::Box()
{
	this -> name = new string("default");

	cout << "box construct" << endl;
}


Box::~Box()
{
	cout << "box destruct" << endl;
	delete this->name;
}

Box::Box(const Box &box)
{
	cout << "box copy" << endl;
}

Box::Box(Box && box)
{
	cout << "box move" << endl;
	this->name = box.name;
	box.name = nullptr;
}