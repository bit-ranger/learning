#include "pch.h"
#include <iostream>
#include <vector>

using namespace std;

double average(const vector<double> &arr) {
	double sum = 0;
	for (unsigned i = 0; i < arr.size(); i++)
	{
		sum += arr[i];
	}
	return sum / arr.size();
}

int main()
{
	unsigned n;
	cout << "n = ";
	cin >> n;
	vector<double> arr(n);
	std::cout << "please input " << n << " real numbers:" << std::endl;
	for (unsigned i = 0; i < n; i++)
	{
		cin >> arr[i];
	}
	cout << "average = " << average(arr) << endl;

	cout << "print" << endl;

	for (auto i = arr.begin(); i != arr.end(); ++i)
	{
		cout << *i << endl;
	}

	cout << "print" << endl;

	for (auto e : arr) {
		cout << e << endl;
	}

	return 0;

}
