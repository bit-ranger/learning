#pragma once
#include <string>

namespace learnObject {
	class Box
	{
	private: 
		std::string *name = nullptr;

	public:
		Box();
		Box(const Box &box);
		Box(Box &&box);
		~Box();
	};
}

