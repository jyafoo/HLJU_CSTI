#include <iostream>
using namespace std;

int sum = 0;
int M = 9;
int res[10010];
// x表示子集合的解
// n表示集合元素数量
// i表示当前考虑的元素位置
void Subset(int S[], int x[], int n, int i) {
    if (i == n) {
        bool isFirst = true;
        for (int j = 0; j < i; j++) {
            if (x[j] == 1) {
                if (isFirst) {
                    isFirst = false;
                    //cout << S[j];
                    sum += S[j];
                    res[j] = S[j];
                } else {
                    //cout << ", " << S[j];
                    sum += S[j];
                    res[j] = S[j];
                }
            }
        }
        // 符合条件输出
        if(sum == 9) {
            cout << "{";
            for(int j = 0; j < i; j++) {
                if(res[j] != 0)
                cout << res[j] << ",";
            }
            cout <<"}";
        }
        for(int j = 0; j < i; j++) {
            res[j] = 0;
        }
        sum = 0;
        return;
    }

    // 不取
    x[i] = 0;
    Subset(S, x, n, i+1);

    // 取
    x[i] = 1;
    Subset(S, x, n, i+1);
}

int main() {
    int S[] = { 7,5,1,2,10 };
    int n = sizeof(S) / sizeof(S[0]);
    int x[n] = { 0 };

    Subset(S, x, n, 0);

    return 0;
}
