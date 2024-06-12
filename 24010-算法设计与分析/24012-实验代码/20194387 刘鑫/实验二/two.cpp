#include <iostream>
#include <string>
using namespace std;

int dp[10010][10010];
int flag[10010][10010];
string x;
string y;

void lcs_length(int m, int n)
{
    for(int i = 1; i <= m; i++) {
        for(int j = 1; j <= n; j++) {
            // 如果x和y的字符相等
            if(x[i-1] == y[j-1]) {
                // 取对角线上的值+1
                dp[i][j] = dp[i-1][j-1] + 1;
                // 记录标志
                flag[i][j] = -1;
            }else if(dp[i-1][j] >= dp[i][j-1]) {
                // 如果x和y不相等，那么就取相邻（左上）的最大值
                dp[i][j] = dp[i-1][j];
                flag[i][j] = -2;
            }else {
                dp[i][j] = dp[i][j-1];
                flag[i][j] = -3;
            }
        }
    }
}

void print_lcs(int i, int j) {
    if( i == 0 || j == 0) {
        return;
    }
    // 根据条件依次入栈
    if(flag[i][j] == -1) {
        print_lcs(i-1,j-1);
        // 只有符合相等条件-1时，才能够输出，其它的弹栈后无操作。
        cout << x[i-1];
    }else if(flag[i][j] == -2) {
        // i-1大，走的-2
        print_lcs(i-1,j);
    }else {
        // j-1大，走的-3
        print_lcs(i,j-1);
    }
        /*
    for(int n = 0; n <= i; n++) {
        for(int m = 0; m <= j; m++) {
            if(flag[n][m] == -1) {
                // 用x，n代表x的长度，所以是n-1，而不是m-1，
                // 因为n表示x字符串的位置
                // 如果用y，m则是y[m-1]，同理。
                cout << x[n-1];
            }
        }
    }    */
}

int main()
{

    cout << "请输入字符串x：";
    cin >> x;
    cout << "请输入字符串y：";
    cin >> y;
    // 表格初始化
    for(int i = 0; i <= x.length(); i++) {
        dp[i][0] = 0;
    }
    for(int j = 0; j <= y.length(); j++) {
        dp[0][j] = 0;
    }
    lcs_length(x.length(), y.length());
    cout << "公共子序列：";
    print_lcs(x.length(), y.length());
    cout <<"" <<endl;
    int j = 0;
    for(int i = 0; i <= x.length(); i++) {
    for(j = 0; j <= y.length(); j++) {
        cout << dp[i][j] << " ";
    }
    if(j == y.length()+1) {
        cout <<"" <<endl;
    }
}
    return 0;
}
/*
ALCHEMIST
ALGORITHMS

    A L C H E M I S T
  0 0 0 0 0 0 0 0 0 0
A 0 1 1 1 1 1 1 1 1 1
L 0 1 2 2 2 2 2 2 2 2
G 0 1 2 2 2 2 2 2 2 2
O 0 1 2 2 2 2 2 2 2 2
R 0 1 2 2 2 2 2 2 2 2
I 0 1 2 2 2 2 2 3 3 3
T 0 1 2 2 2 2 2 3 3 4
H 0 1 2 2 3 3 3 3 3 4
M 0 1 2 2 3 3 4 4 4 4
S 0 1 2 2 3 3 4 4 5 5

*/
