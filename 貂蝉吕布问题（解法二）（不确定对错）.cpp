
#include <iostream>

using namespace std;

int main() {
	int h, m, s;
	cin >> h >> m ;
	s = h * 120;
	m = h * 60;
	cout << "吕布迟到的分钟=" << s << endl;
	cout << "吕布迟到的秒数=" << m << endl;
	return 0;
}