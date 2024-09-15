#include <iostream>
#include <stack>
#define MAX 100

typedef struct
{
    char vexs[MAX];
    int arcs[MAX][MAX];
    int vexnum, arcnum;
} AMGraph;

using namespace std;

bool vis[MAX];

void CreateUDN(AMGraph &G)
{
    char v1, v2;
    cout << "输入点的数量和边的数量: ";
    cin >> G.vexnum >> G.arcnum;
    cout << "输入各点的数据: ";
    for (int i = 0; i < G.vexnum; i++)
        cin >> G.vexs[i];
    for (int i = 0; i < G.vexnum; i++)
        for (int j = 0; j < G.vexnum; j++)
            G.arcs[i][j] = 0;
    cout << "输入各边的两个端点: ";
    for (int k = 0; k < G.arcnum; k++)
    {
        int i, j;
        cin >> v1 >> v2;
        for (i = 0; i < G.vexnum; i++)
        {
            if (G.vexs[i] == v1)
                break;
        }
        for (j = 0; j < G.vexnum; j++)
        {
            if (G.vexs[j] == v2)
                break;
        }
        G.arcs[i][j] = 1;
    }
}

void dfs(AMGraph G, char v, char aim)
{
    int i;
    for (i = 0; i < G.vexnum; i++)
        if (G.vexs[i] == v)
            break;
    vis[i] = true;
    cout << v << ' ';
    if (v == aim)
        return;
    for (int j = 0; j < G.vexnum; j++)
        if ((G.arcs[i][j] != 0) && (!vis[j]))
            dfs(G, G.vexs[j], aim);
}

int main()
{
    AMGraph G;
    char i, j;
    cout << "输入路径的起点和终点: ";
    cin >> i >> j;
    CreateUDN(G);
    cout << "其中一条简单路径为: ";
    dfs(G, i, j);
    cout << endl;
    return 0;
}

/*
b d
4 6
a b c d
b a
a c
a d
c d
b b
d a
*/