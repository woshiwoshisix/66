
#include <iostream>

using namespace std;

int main() {
	int h, m, s;
	cout << "������ٵ���Сʱ����";
	cin >> h;
	cout << "������ٵ��ķ�������";
	cin >> m;
	cout << "������ٵ���������";
	cin >> s;

	int totalSeconds = h * 3600 + m * 60 + s;

	cout << "�������ٵ��� " << totalSeconds << " ��" << endl;

	return 0;
}