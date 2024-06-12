#include<iostream>
#include<algorithm>
using namespace std;

// 众数
int mode;
// 重数
int modeCount = 0;

int arr[10010];

void solve(int n,int left, int right)
{
    // 函数递归出口
    if(left > right) {
        return;
    }
    // 划分找到左侧第一个不等于a[mid]的位置
    int mid = (left + right) >> 1;
    int i = mid, j = mid;
    // 找到左侧第一个不等于a[mid]的位置
    while(i >= 0 && arr[i] == arr[mid]) {
        i--;
    }
    // 找到右侧第一个不等于a[mid]的位置
    while(j <= n-1 && arr[j] == arr[mid]) {
        j++;
    }
    // 众数在中间的情况
    // j-i-1为中位数的重数
    if(j - i - 1 > modeCount) {
        mode = arr[mid];
        modeCount = j - i - 1;
    }
    // 分治：众数可能在左侧
    if(i - left + 1 > modeCount) {
        solve(n,left,i);
    }
    // 分治：众数可能在右侧
    if(right - j + 1 > modeCount) {
        solve(n,j,right);
    }
}


main()
{
    int n;
    cout << "请输入元素个数：";
    cin >> n;
    cout << "请输入元素值：";
    for(int i = 0; i < n; i++) {
        cin >> arr[i];
    }
    sort(arr,arr+n);
    solve(n,0,n);
    cout << "众数：" << mode;
    cout << "重数：" << modeCount;
    return 0;
}

// 1 2 2 7 2 7 5
// 1 2 3 3 3 4 4 5
