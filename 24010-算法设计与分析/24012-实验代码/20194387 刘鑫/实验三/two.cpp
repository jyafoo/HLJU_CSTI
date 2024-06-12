#include <iostream>
using namespace std;

// 活动开始时间
int s[10010];

// 活动结束时间
int f[10010];

// 活动序列号
int num[10010];

// 是否选择该活动
bool flag[10010];

// 根据结束时间进行排序
void Sort(int n)
{   int i,j;
    for(i=0;i<n-1;i++)
    for(j=0;j<n-i-1;j++)
    {
        if(f[j]>f[j+1])
        {
            swap(s[j], s[j+1]);
            swap(f[j], f[j+1]);
            swap(num[j], num[j+1]);
        }
    }
}


void activity(int n)
{
    int j = 0;
    // 第一个肯定选
    flag[num[0]] = true;
    for(int i = 1; i < n; i++) {
        // 下一个活动的起始时间 > 当前活动的结束时间，则选择此活动
        if(s[i] >= f[j]) {
            flag[i] = true;
            j = i;
        }else {
            flag[i] = false;
        }
    }
}


int main()
{

    int n;
    cout << "请输入活动个数：";
    cin >> n;
    cout << "请输入活动开始时间和结束时间：";
    for(int i = 0; i < n; i++) {
        cin >> s[i] >> f[i];
        num[i] = i;
    }
    Sort(n);
    activity(n);

    for(int i = 0; i < n; i++) {
        if(flag[i]) {
            cout << "能举办的活动序号：" << num[i] << "   " << "活动开始时间：" << s[i] << "   " << "活动结束时间：" << f[i] << endl;
        }
    }

    return 0;
}

/*
11
1 4
3 5
0 6
5 7
3 8
5 9
6 10
8 11
8 12
2 13
12 14

*/






