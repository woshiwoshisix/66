
#include <iostream>
#include <cmath>
#include <stack>
#include <string>
#include <algorithm>

using namespace std;

// 判断字符是否为操作符
bool isOperator(char ch) {
	return (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^');
}

// 获取操作符的优先级
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

// 对两个操作数进行计算
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

// 进行表达式求值
double evaluateExpression(string expr) {
	stack<char> opStack;
	stack<double> numStack;

	for (size_t i = 0; i < expr.size(); i++) {
		char ch = expr[i];

		// 如果是数字，则将数字入栈
		if (isdigit(ch)) {
			string numStr = "";
			while (i < expr.size() && (isdigit(expr[i]) || expr[i] == '.')) {
				numStr += expr[i];
				i++;
			}
			i--;
			numStack.push(stod(numStr));
		}
		// 如果是操作符
		else if (isOperator(ch)) {
			// 如果操作符栈为空或当前操作符优先级大于栈顶操作符，则直接入栈
			if (opStack.empty() || getPriority(ch) > getPriority(opStack.top())) {
				opStack.push(ch);
			}
			// 否则，先将栈顶操作符进行计算，直到栈为空或遇到优先级低于当前操作符的操作符
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
		// 如果是左括号，则直接入栈
		else if (ch == '(') {
			opStack.push(ch);
		}
		// 如果是右括号，则将栈中的操作符进行计算，直到遇到左括号
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
			// 弹出左括号
			opStack.pop();
		}
	}

	// 如果操作符栈中还有操作符，则全部进行计算
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

	// 返回最终结果
	if (numStack.size() == 1) {
		return numStack.top();
	} else {
		cout << "Invalid expression!" << endl;
		return 0;
	}
}

int main() {
	string expr;
	cout << "请输入一个表达式：";
	getline(cin, expr);

	// 去除空格
	expr.erase(remove(expr.begin(), expr.end(), ' '), expr.end());

	double result = evaluateExpression(expr);

	cout << "计算结果为：" << result << endl;

	return 0;
}