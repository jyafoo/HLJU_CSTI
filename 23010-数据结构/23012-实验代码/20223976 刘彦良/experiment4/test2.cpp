#include <iostream>
#define MAX_SIZE 100

using namespace std;

struct Stack
{
    char data[MAX_SIZE];
    char *top;
    char *base;
};

bool BracketCheck(string equation)
{
    Stack stack;
    stack.top = stack.base = stack.data;
    for (int i = 0; i < equation.size(); i++)
    {
        if (equation[i] == '(')
            *stack.top++ = '(';
        else if (equation[i] == ')')
        {
            if (stack.top == stack.base)
                return false;
            else
                stack.top--;
        }
    }
    if (stack.top == stack.base)
        return true;
    else
        return false;
}

int main()
{
    string equation;
    int select = 1;
    while (select)
    {
        cout << "请输入算式: " << endl;
        cin >> equation;
        if (BracketCheck(equation))
            cout << "该算式括号匹配! " << endl;
        else
            cout << "该算式括号不匹配! " << endl;
        cout << "是否继续? " << endl;
        cout << " 1. 继续" << endl;
        cout << " 0. 结束" << endl;
        cin >> select;
    }
    return 0;
}