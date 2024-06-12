#include <iostream>
using namespace std;

// 存放物品重量
double w[10010];

// 存放物品价值
double v[10010];

// 问题的解
double x[10010];

// 根据单位价值排序
void Sort(int n)
{
    int i,j;
    double temp1,temp2;
    for(i = 0; i < n; i++)
        for(j = 0; j < n-i; j++)//冒泡排序
        {
            temp1 = v[j] / w[j];
            temp2 = v[j+1] / w[j+1];
            if(temp1 < temp2)
            {
                swap(w[j], w[j+1]);
                swap(v[j], v[j+1]);
            }
        }
}
int main()
{
    double maxValue = 0;
    int n;
    int C;
    cout << "请输入物品的数量和背包最大容量：";
    cin >> n;
    cin >> C;
    for(int i = 0; i < n; i++) {
        cout << "请输入物品重量和价值：";
        cin >> w[i] >> v[i];
    }
    Sort(n);
    int i = 0;
    while(w[i] < C) {
        x[i] = 1; // 将第i个物品放入背包
        C = C - w[i];
        maxValue=maxValue+v[i];
        i++;
    }
    if(i < n) {
        x[i] = C / w[i];
        maxValue=maxValue+x[i]*v[i];
    }
    for(int j = 0; j < n; j++) {
        cout << "重量为：" << w[j] << "   " << "价值为：" << v[j] << "   " << "放入比例：" << x[j] << endl;
    }
    cout<<"放入背包物品的总价值为"<<maxValue<<endl;
    return 0;
}
/*
5 50
10 70
20 60
30 50
40 40
50 30
*/
















