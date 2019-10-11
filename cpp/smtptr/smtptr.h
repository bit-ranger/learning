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

	//Ӧ��Ϊָ�룬���ڶ������֮�乲������
	unsigned int *cnt_;
};
#endif // SMTPTR_H
