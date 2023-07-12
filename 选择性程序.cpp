#include <iostream>

int main() {
	int num;

	std::cout << "请输入一个整数：";
	std::cin >> num;

	if (num > 0) {
		std::cout << "这个数字是正数" << std::endl;
	} else if (num < 0) {
		std::cout << "这个数字是负数" << std::endl;
	} else {
		std::cout << "这个数字是零" << std::endl;
	}

	return 0;
}
