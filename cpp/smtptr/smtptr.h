#ifndef SMTPTR_H
#define SMTPTR_H

/**
 * \brief 
 * \tparam T 
 */
template <typename T>
class SmartPtr {
public:

	explicit SmartPtr(T* t);

	~SmartPtr();

	SmartPtr(SmartPtr<T>& src);

	SmartPtr(SmartPtr<T>&& src) noexcept;

	T& operator*();

	T* operator->();

	SmartPtr & operator=(SmartPtr<T>& src);

	SmartPtr& operator=(SmartPtr<T>&& src) noexcept;

	unsigned int useCount() const;

private:
    T * ptr_;

	//应当为指针，用于多个对象之间共享数据
	unsigned int *cnt_;
};
#endif // SMTPTR_H
