#include <iostream>
#include <math.h>
using namespace std;

int arr[10010];
// 存储子段和数组
int dp[10010];
int index[3] = {0,0,0};
int maxValue = -99999;

int maxSum(int n)
{
    dp[0] = arr[0];
    for(int i = 1; i < n; i++) {
        //dp[i] = dp[i-1] > 0 ? dp[i-1] + arr[i] : arr[i];
        // 如果前i-1项和大于0
        if(dp[i-1] > 0) {
            // 就计算前i项最大子段和
            dp[i] = dp[i-1] + arr[i];
        }else {
            // 如果前i-1项和小于0
            dp[i] = arr[i];
            // 更新起始下标
            index[0] = i;
        }
        //maxValue = max(maxValue,dp[i]);
        // 新的前i项和  与  maxValue比较
        if(dp[i] > maxValue) {
            // 更新maxValue值
            maxValue = dp[i];
            // 再次记录起始下标（有可能上面的起始下标更新了，但是它不是最大子段和，所以保证起始下标不变）
            index[2] = index[0];
            // 记录终止下标
            index[1] = i;
        }
    }
    return maxValue;
}


int main()
{
    cout << "请输入元素个数：";
    int n;
    cin >> n;
    cout<< "请输入元素值：";
    for(int i = 0; i < n; i++) {
        cin >> arr[i];
    }
    int res = maxSum(n);
    cout << "起始下标：" << index[2] << endl;
    cout << "终止下标：" << index[1] << endl;
    cout << "最大子段和：" << res << endl;
    return 0;
}


/*
7
2 -4 3 -1 2 -4 3
6
-2 11 -4 13 -5 -2
*/
