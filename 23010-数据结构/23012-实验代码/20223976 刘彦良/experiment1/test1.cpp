#include <iostream>

using namespace std;

void insert(int a[], int *len, int x)
{
    if (*len == 0)
    {
        a[0] = x;
        (*len)++;
        return;
    }
    if (a[*len - 1] <= x)
    {
        a[*len] = x;
        (*len)++;
        return;
    }
    for (int i = 0; i < *len; i++)
    {
        if (a[i] > x)
        {
            for (int j = *len; j >= i + 1; j--)
                a[j] = a[j - 1];
            a[i] = x;
            (*len)++;   
            break;
        }
    }
}

int main()
{
    int a[30] = {0};
    int len = 0;
    int x;
    cout << "请输入一组整数，若要停止输入，请输入-1: ";
    for (int i = 1; i <= 30; i++)
    {
        cin >> x;
        if (x == -1)
            break;
        insert(a, &len, x);
    }
    cout << "按序插入后的结果为: ";
    for (int i = 0; i < len; i++)
        cout << a[i] << ' ';
    cout << endl;
    return 0;
}