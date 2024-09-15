#include <iostream>

using namespace std;

void move(int a[], int len, int k)
{
    int temp;
    if (len == 0)
        return;
    while (k--)
    {
        temp = a[len - 1];
        for (int i = len - 1; i >= 0; i--)
            a[i] = a[i - 1];
        a[0] = temp;
    }
}

int main()
{
    int a[5] = {1, 2, 3, 4, 5};
    int k;
    cout << "顺序表的内容为: ";
    for (int i = 0; i < 5; i++)
        cout << a[i] << ' ';
    cout << endl;
    cout << "请输入一个整数: ";
    cin >> k;
    move(a, 5, k);
    cout << "顺序表循环右移k位后的结果为: ";
    for (int i = 0; i < 5; i++)
        cout << a[i] << ' ';
    cout << endl;
    return 0;
}