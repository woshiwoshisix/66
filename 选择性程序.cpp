#include <iostream>

int main() {
	int num;

	std::cout << "������һ��������";
	std::cin >> num;

	if (num > 0) {
		std::cout << "�������������" << std::endl;
	} else if (num < 0) {
		std::cout << "��������Ǹ���" << std::endl;
	} else {
		std::cout << "�����������" << std::endl;
	}

	return 0;
}
