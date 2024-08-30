#include <iostream>
#include <random>
#include <ctime>

using namespace std;

typedef struct
{
    int *r;
    int length;
} SqList;

int Partition(SqList &L, int low, int high, int &cmpNum, int &swapNum)
{
    int primLow = low;
    L.r[0] = L.r[low];
    int pivotkey = L.r[low];
    while (low < high)
    {
        while (low < high && L.r[high] >= pivotkey)
        {
            high--;
            cmpNum++;
        }
        L.r[low] = L.r[high];
        if (low != high)
            swapNum++;
        while (low < high && L.r[low] <= pivotkey)
        {
            low++;
            cmpNum++;
        }
        L.r[high] = L.r[low];
        if (low != high)
            swapNum++;
    }
    L.r[low] = L.r[0];
    return low;
}

void QSort(SqList &L, int low, int high, int &cmpNum, int &swapNum)
{
    if (low < high)
    {
        int pivotloc = Partition(L, low, high, cmpNum, swapNum);
        QSort(L, low, pivotloc - 1, cmpNum, swapNum);
        QSort(L, pivotloc + 1, high, cmpNum, swapNum);
    }
}

void QuickSort(SqList &L, int &cmpNum, int &swapNum)
{
    cmpNum = swapNum = 0;
    QSort(L, 1, L.length, cmpNum, swapNum);
}

void Merge(int R[], int T[], int low, int mid, int high, int &cmpNum, int &swapNum)
{
    int i = low;
    int j = mid + 1;
    int k = low;
    int cnt = 0;
    while (i <= mid && j <= high)
    {
        if (R[i] <= R[j])
            T[k++] = R[i++];
        else
        {
            T[k++] = R[j++];
            cnt++;
        }
        cmpNum++;
    }
    while (i <= mid)
        T[k++] = R[i++];
    while (j <= high)
        T[k++] = R[j++];
    swapNum += cnt;
}

void MSort(int R[], int T[], int low, int high, int &cmpNum, int &swapNum)
{
    if (low == high)
        T[low] = R[low];
    else
    {
        int mid = low + high >> 1;
        MSort(R, T, low, mid, cmpNum, swapNum);
        MSort(R, T, mid + 1, high, cmpNum, swapNum);
        Merge(R, T, low, mid, high, cmpNum, swapNum);
        for (int i = low; i <= high; i++)
            R[i] = T[i];
    }
}

void MergeSort(SqList &L, int &cmpNum, int &swapNum)
{
    int S[L.length];
    cmpNum = swapNum = 0;
    MSort(L.r, S, 1, L.length, cmpNum, swapNum);
}

int main()
{
    default_random_engine e;
    e.seed(time(0));
    SqList L, L1, L2;
    int n;
    int low, high;
    int cmpNum1, swapNum1;
    int cmpNum2, swapNum2;
    for (int k = 1; k <= 3; k++)
    {
        cout << "输入第" << k << "组数据元素的个数: ";
        cin >> n;
        L.length = L1.length = L2.length = n;
        L.r = new int[n + 1];
        L1.r = new int[n + 1];
        L2.r = new int[n + 1];
        cout << "输入第" << k << "组数据元素大小的下限与上限: ";
        cin >> low;
        cout << "输入元素大小的上限: ";
        cin >> high;
        uniform_int_distribution<int> u(low, high);
        for (int i = 1; i <= n; i++)
            L.r[i] = L1.r[i] = L2.r[i] = u(e);
        cout << endl
             << "初始数据为: ";
        for (int i = 1; i <= n; i++)
            cout << L.r[i] << ' ';
        cout << endl
             << endl;
        QuickSort(L1, cmpNum1, swapNum1);
        cout << "快速排序结果为: ";
        for (int i = 1; i <= n; i++)
            cout << L1.r[i] << ' ';
        cout << endl
             << "比较次数为: " << cmpNum1 << '\t' << "交换次数为: " << swapNum1 << endl
             << endl;
        MergeSort(L2, cmpNum2, swapNum2);
        cout << "归并排序结果为: ";
        for (int i = 1; i <= n; i++)
            cout << L2.r[i] << ' ';
        cout << endl
             << "比较次数为: " << cmpNum2 << '\t' << "交换次数为: " << swapNum2 << endl
             << endl;
        delete L.r;
        delete L1.r;
        delete L2.r;
    }
    return 0;
}