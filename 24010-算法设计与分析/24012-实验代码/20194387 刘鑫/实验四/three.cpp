#include <iostream>
using namespace std;

void Subset(int S[], int x[], int n, int i) {
    if (i == n) {
        cout << "{";
        bool isFirst = true;
        for (int j = 0; j < i; j++) {
            if (x[j] == 1) {
                if (isFirst) {
                    isFirst = false;
                    cout << S[j];
                } else {
                    cout << ", " << S[j];
                }
            }
        }
        cout << "}" << endl;
        return;
    }

    x[i] = 0;
    Subset(S, x, n, i+1);

    x[i] = 1;
    Subset(S, x, n, i+1);
}

int main() {
    int S[] = {7,5,1,2,10 };
    int n = sizeof(S) / sizeof(S[0]);
    int x[n] = { 0 };

    Subset(S, x, n, 0);

    return 0;
}

