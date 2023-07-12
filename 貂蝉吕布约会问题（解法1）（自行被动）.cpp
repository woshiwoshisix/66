
#include <iostream>

using namespace std;

int main() {
	int h, m, s;
	cout << "请输入迟到的小时数：";
	cin >> h;
	cout << "请输入迟到的分钟数：";
	cin >> m;
	cout << "请输入迟到的秒数：";
	cin >> s;

	int totalSeconds = h * 3600 + m * 60 + s;

	cout << "貂蝉共迟到了 " << totalSeconds << " 秒" << endl;

	return 0;
}