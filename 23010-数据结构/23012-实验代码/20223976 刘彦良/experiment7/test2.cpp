#include <iostream>
#include <stack>
#define MAX 100
#define INF 0x3f3f3f3f

using namespace std;

struct ArcNode
{
    int adjvex;
    struct ArcNode *nextarc;
    int w;
};

typedef struct
{
    char data;
    ArcNode *firstarc;
} VNode, AdjList[MAX];

struct ALGraph
{
    AdjList vertices;
    int vexnum, arcnum;
};

int d[MAX][MAX];
int path[MAX][MAX];

void CreateUDG(ALGraph &G)
{
    char v1, v2;
    int w;
    int i, j;
    ArcNode *p;
    cout << "输入点和边的数量: ";
    cin >> G.vexnum >> G.arcnum;
    cout << "输入各点的数据: ";
    for (int i = 0; i < G.vexnum; i++)
    {
        cin >> G.vertices[i].data;
        G.vertices[i].firstarc = nullptr;
    }
    cout << "输入各边的起点终点和权值:\n";
    for (int k = 0; k < G.arcnum; k++)
    {
        cin >> v1 >> v2 >> w;
        for (i = 0; i < G.vexnum; i++)
            if (v1 == G.vertices[i].data)
                break;
        for (j = 0; j < G.vexnum; j++)
            if (v2 == G.vertices[j].data)
                break;
        p = new ArcNode;
        p->adjvex = j;
        p->w = w;
        p->nextarc = G.vertices[i].firstarc;
        G.vertices[i].firstarc = p;
    }
}

void Floyd(ALGraph G)
{
    for (int i = 0; i < G.vexnum; i++)
    {
        for (int j = 0; j < G.vexnum; j++)
        {
            path[i][j] = -1;
            d[i][j] = INF;
        }
    }
    for (int i = 0; i < G.vexnum; i++)
    {
        for (ArcNode *j = G.vertices[i].firstarc; j != nullptr; j = j->nextarc)
        {
            d[i][j->adjvex] = j->w;
            path[i][j->adjvex] = i;
        }
    }
    for (int i = 0; i < G.vexnum; i++)
    {
        for (ArcNode *k = G.vertices[i].firstarc; k != nullptr; k = k->nextarc)
        {
            for (ArcNode *j = G.vertices[k->adjvex].firstarc; j != nullptr; j = j->nextarc)
            {
                if (d[i][k->adjvex] + d[k->adjvex][j->adjvex] < d[i][j->adjvex])
                {
                    d[i][j->adjvex] = d[i][k->adjvex] + d[k->adjvex][j->adjvex];
                    path[i][j->adjvex] = path[k->adjvex][j->adjvex];
                }
            }
        }
    }
}

int main()
{
    char beg, end;
    ALGraph G;
    stack<int> st;
    int i, j;
    cout << "输入路径的起点和重点: ";
    cin >> beg >> end;
    CreateUDG(G);
    for (i = 0; i < G.vexnum; i++)
        if (G.vertices[i].data == beg)
            break;
    for (j = 0; j < G.vexnum; j++)
        if (G.vertices[j].data == end)
            break;
    Floyd(G);
    while (j != path[i][j] && ~j)
    {
        st.push(j);
        j = path[i][j];
    }
    if (j == path[i][j])
        st.push(j);
    cout << "一条最短路径为: ";
    while (!st.empty())
    {
        for (i = 0; i < G.vexnum; i++)
        {
            if (G.vertices[i].data == beg)
            {
                cout << G.vertices[st.top()].data << ' ';
                break;
            }
        }
        st.pop();
    }
    cout << endl;
    return 0;
}

/*
a c
4 8
a b c d
a b 1
a d 4
b c 9
b d 2
c a 3
c b 5
c d 8
d c 6
*/

/*
b d
4 6
a b c d
b a 1
a c 1
a d 1
c d 1
b b 1
d a 1
*/