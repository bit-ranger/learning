#include <iostream>
#include <fstream>
#include <string>
#include <sstream>

using namespace std;

struct Date {
	int year, mon, day;
};

template <class T>
inline string toString(const T& t) {
	ostringstream os;
	os << t;
	return os.str();
}


int main()
{
	ofstream fout("b.out");
	streambuf* pOld = cout.rdbuf(fout.rdbuf());
    std::cout << "Hello World!\n"; 

	cout.rdbuf(pOld);
	std::cout << "Hello World!\n";

	Date dt = { 1,2,3 };
	ofstream file("date.dat", ios_base::binary);
	//将指向dt的指针转换为字符类型，满足write函数要求
	file.write(reinterpret_cast<char*>(&dt), sizeof(dt));
	file.close();

	ifstream filer("date.dat", ios_base::binary);
	Date dtr;
	filer.read(reinterpret_cast<char*>(&dtr), sizeof(dtr));
	filer.close();

	string str1 = toString(dtr.day);
	cout << str1 << endl;

	return 0;
}
