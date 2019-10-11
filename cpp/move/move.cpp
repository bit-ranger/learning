#include <iostream>
#include <vector>

using namespace std;

void processValue(int& i) {
	std::cout << "LValue processed: " << i << std::endl;
}

void processValue(int&& i) {
	std::cout << "RValue processed: " << i << std::endl;
}

void forwardValue(int&& i) {
	// 右值转换为左值
	processValue(i);
}


class MyStringNoMove final
{
private:
	string data_;
public:

	MyStringNoMove() {
		data_ = string();
	}

	explicit MyStringNoMove(const string data) {
		this->data_ = data;
	}

	MyStringNoMove(const MyStringNoMove& str) {
		this->data_ = str.data_;
		std::cout << "Copy Constructor is called! source: " << str.data_ << std::endl;
	}

	MyStringNoMove& operator=(const MyStringNoMove& str) {
		if (this != &str) {
			this->data_ = str.data_;
		}
		std::cout << "Copy Assignment is called! source: " << str.data_ << std::endl;
		return *this;
	}
};


class MyStringHasMove final
{
private:
	string data_;
public:

	MyStringHasMove() {
		data_ = string();
	}

	explicit MyStringHasMove(const string data) {
		this->data_ = data;
	}

	MyStringHasMove(const MyStringHasMove& str) {
		this->data_ = str.data_;
		std::cout << "Copy Constructor is called! source: " << this ->data_ << std::endl;
	}

	MyStringHasMove& operator=(const MyStringHasMove& str) {
		if (this != &str) {
			this->data_ = str.data_;
		}
		std::cout << "Copy Assignment is called! source: " << this -> data_ << std::endl;
		return *this;
	}

	MyStringHasMove(MyStringHasMove&& str) {
		this->data_ = str.data_;
		str.data_ = string();
		std::cout << "Move Constructor is called! source: " << this -> data_ << std::endl;
	}

	MyStringHasMove& operator=(MyStringHasMove&& str) {
		if (this != &str) {
			this->data_ = str.data_;
			str.data_ = string();
		}
		std::cout << "Move Assignment is called! source: " << this -> data_ << std::endl;
		return *this;
	}
};










int main() {

	// left value, right value
	auto a = 0;
	processValue(a);
	processValue(1);
	forwardValue(2);


	// no move
	MyStringNoMove b;
	b = MyStringNoMove("Hello no move");
	std::vector<MyStringNoMove> vecb;
	vecb.push_back(MyStringNoMove("World no move"));

	// has move
	MyStringHasMove c;
	c = MyStringHasMove("Hello has move");
	std::vector<MyStringHasMove> vecc;
	vecc.push_back(MyStringHasMove("World has move"));


	return 0;
}
