#include <iostream>

using namespace std;

void reserve(int a[], int len)
{
    int temp;
    for (int i = 0; i + i < len; i++)
    {
        temp = a[i];
        a[i] = a[len - i - 1];
        a[len - i - 1] = temp;
    }
}

int main()
{
    int a[5] = {0};
    cout << "请输入5个整数: ";
    for (int i = 0; i < 5; i++)
        cin >> a[i];
    reserve(a, 5);
    cout << "反转后的结果为: ";
    for (int i = 0; i < 5; i++)
        cout << a[i] << ' ';
    cout << endl;
}