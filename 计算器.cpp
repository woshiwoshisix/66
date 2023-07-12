
#include <iostream>
#include <cmath>
#include <stack>
#include <string>
#include <algorithm>

using namespace std;

// �ж��ַ��Ƿ�Ϊ������
bool isOperator(char ch) {
	return (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^');
}

// ��ȡ�����������ȼ�
int getPriority(char op) {
	if (op == '+' || op == '-')
		return 1;
	else if (op == '*' || op == '/')
		return 2;
	else if (op == '^')
		return 3;
	else
		return 0;
}

// ���������������м���
double calculate(double a, double b, char op) {
	if (op == '+')
		return a + b;
	else if (op == '-')
		return a - b;
	else if (op == '*')
		return a * b;
	else if (op == '/')
		return a / b;
	else if (op == '^')
		return pow(a, b);
	else
		return 0;
}

// ���б��ʽ��ֵ
double evaluateExpression(string expr) {
	stack<char> opStack;
	stack<double> numStack;

	for (size_t i = 0; i < expr.size(); i++) {
		char ch = expr[i];

		// ��������֣���������ջ
		if (isdigit(ch)) {
			string numStr = "";
			while (i < expr.size() && (isdigit(expr[i]) || expr[i] == '.')) {
				numStr += expr[i];
				i++;
			}
			i--;
			numStack.push(stod(numStr));
		}
		// ����ǲ�����
		else if (isOperator(ch)) {
			// ���������ջΪ�ջ�ǰ���������ȼ�����ջ������������ֱ����ջ
			if (opStack.empty() || getPriority(ch) > getPriority(opStack.top())) {
				opStack.push(ch);
			}
			// �����Ƚ�ջ�����������м��㣬ֱ��ջΪ�ջ��������ȼ����ڵ�ǰ�������Ĳ�����
			else {
				while (!opStack.empty() && getPriority(ch) <= getPriority(opStack.top())) {
					char op = opStack.top();
					opStack.pop();
					if (numStack.size() < 2) {
						cout << "Invalid expression!" << endl;
						return 0;
					}
					double b = numStack.top();
					numStack.pop();
					double a = numStack.top();
					numStack.pop();
					numStack.push(calculate(a, b, op));
				}
				opStack.push(ch);
			}
		}
		// ����������ţ���ֱ����ջ
		else if (ch == '(') {
			opStack.push(ch);
		}
		// ����������ţ���ջ�еĲ��������м��㣬ֱ������������
		else if (ch == ')') {
			while (!opStack.empty() && opStack.top() != '(') {
				char op = opStack.top();
				opStack.pop();
				if (numStack.size() < 2) {
					cout << "Invalid expression!" << endl;
					return 0;
				}
				double b = numStack.top();
				numStack.pop();
				double a = numStack.top();
				numStack.pop();
				numStack.push(calculate(a, b, op));
			}
			// ����������
			opStack.pop();
		}
	}

	// ���������ջ�л��в���������ȫ�����м���
	while (!opStack.empty()) {
		char op = opStack.top();
		opStack.pop();
		if (numStack.size() < 2) {
			cout << "Invalid expression!" << endl;
			return 0;
		}
		double b = numStack.top();
		numStack.pop();
		double a = numStack.top();
		numStack.pop();
		numStack.push(calculate(a, b, op));
	}

	// �������ս��
	if (numStack.size() == 1) {
		return numStack.top();
	} else {
		cout << "Invalid expression!" << endl;
		return 0;
	}
}

int main() {
	string expr;
	cout << "������һ�����ʽ��";
	getline(cin, expr);

	// ȥ���ո�
	expr.erase(remove(expr.begin(), expr.end(), ' '), expr.end());

	double result = evaluateExpression(expr);

	cout << "������Ϊ��" << result << endl;

	return 0;
}