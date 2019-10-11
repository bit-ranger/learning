#include "pch.h"
#include "smtptr.h"
#include <iostream>

using namespace std;

template <typename T>
SmartPtr<T>::SmartPtr(T* t): 
ptr_(t)
{
	cnt_ = new unsigned int(1);
	cout << "construct\n";
}

template <typename T>
SmartPtr<T>::~SmartPtr()
{
	if(cnt_ != nullptr)
	{
		(*cnt_)--;
		if ((*cnt_) == 0)
		{
			delete ptr_;
			delete cnt_;
		}
	}

	cout << "destruct\n";
}

template <typename T>
SmartPtr<T>::SmartPtr(SmartPtr<T>& src): 
ptr_(src.ptr_), cnt_(src.cnt_)
{
	cout << "copy construct\n";
	(*cnt_)++;
}

template <typename T>
SmartPtr<T>::SmartPtr(SmartPtr<T>&& src) noexcept:
ptr_(src.ptr_), cnt_(src.cnt_)
{
	cout << "move construct\n";
	src.ptr_ = nullptr;
	src.cnt_ = nullptr;
}

template <typename T>
SmartPtr<T> & SmartPtr<T>::operator=(SmartPtr<T>& src)
{
	cout << "copy assignment\n";

	(*cnt_)--;

	// 赋值时只能知道引用减1, 不能知道是否可以释放内存
	//if ((*cnt_) == 0)
	//{
	//	delete ptr_;
	//}

	ptr_ = src.ptr_;
	cnt_ = src.cnt_;
	(*cnt_)++;

	return *this;
}


template <typename T>
SmartPtr<T>& SmartPtr<T>::operator=(SmartPtr<T>&& src) noexcept
{

	cout << "move assignment\n";

	(*cnt_)--;

	// 赋值时只能知道引用减1, 不能知道是否可以释放内存
	//if (*cnt_ == 0)
	//{
	//	delete ptr_;
	//}

	ptr_ = src.ptr_;
	cnt_ = src.cnt_;

	src.ptr_ = nullptr;
	src.cnt_ = nullptr;

	return *this;
}


template <typename T>
T& SmartPtr<T>::operator*()
{

	return *ptr_;
}

template <typename T>
T* SmartPtr<T>::operator->()
{
	return ptr_;
}

template <typename T>
unsigned SmartPtr<T>::useCount() const
{
	return *cnt_;
}
