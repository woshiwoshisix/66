#include <iostream>

using namespace std;

int main() {
	int l, w, c, s;
	cin >> l >> w;
	c = 2 * (l + w);
	s = l * w;
	cout << "周长=" << c << endl;
	cout << "面积=" << s << endl;
	return 0; //返回语句
}