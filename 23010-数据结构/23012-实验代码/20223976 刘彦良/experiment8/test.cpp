#include <iostream>
#include <string>
#include <stack>
#define MAX 100
#define INF 0x3f3f3f3f

using namespace std;

struct Spot
{
    int num;
    string name;
    string intro;
};

struct AMGraph
{
    Spot vexs[MAX];
    int arcs[MAX][MAX];
    int vexnum, arcnum;
};

int path[MAX][MAX];
int pathDoor[MAX];
int d[MAX][MAX];
int dDoor[MAX];

int BinarySearch(AMGraph G, int x)
{
    int left = 0;
    int right = G.vexnum - 1;
    while (left < right)
    {
        int mid = (left + right) >> 1;
        if (G.vexs[mid].num < x)
            left = mid + 1;
        else
            right = mid;
    }
    return left;
}

void CreateUDN(AMGraph &G)
{
    G.vexnum = 11;
    G.arcnum = 17;
    for (int i = 0; i < G.vexnum; i++)
        G.vexs[i].num = i + 1;
    G.vexs[0].name = "主楼";
    G.vexs[0].intro = "校外国语学部以及学校校长办公室、党委等行政机关等教学单位用楼";
    G.vexs[1].name = "联通广场";
    G.vexs[1].intro = "有草坪和跑到，通常是人们散步的地方";
    G.vexs[2].name = "图书馆";
    G.vexs[2].intro = "对全校师生提供借书服务，以及良好的自习环境";
    G.vexs[3].name = "汇文楼";
    G.vexs[3].intro = "学校最主要的教学楼";
    G.vexs[4].name = "食堂";
    G.vexs[4].intro = "提供饮食以及就餐环境";
    G.vexs[5].name = "游泳馆";
    G.vexs[5].intro = "对全校师生开放的公共泳池，游泳课的授课场地";
    G.vexs[6].name = "3号教学楼";
    G.vexs[6].intro = "部分课程的授课教室";
    G.vexs[7].name = "4号教学楼";
    G.vexs[7].intro = "计算机科学技、软件学院，计软学院实验各个实验室";
    G.vexs[8].name = "晨曦广场";
    G.vexs[8].intro = "被艺术楼环绕的广场";
    G.vexs[9].name = "宿舍楼";
    G.vexs[9].intro = "学生休息的地方";
    G.vexs[10].name = "大门";
    G.vexs[10].intro = "出入学校的大门";
    for (int i = 0; i < G.vexnum; i++)
    {
        for (int j = 0; j < G.vexnum; j++)
            G.arcs[i][j] = INF;
        G.arcs[i][i] = 0;
    }
    G.arcs[0][1] = G.arcs[1][0] = 20;
    G.arcs[0][2] = G.arcs[2][0] = 70;
    G.arcs[0][4] = G.arcs[4][0] = 70;
    G.arcs[1][2] = G.arcs[2][1] = 40;
    G.arcs[1][3] = G.arcs[3][1] = 50;
    G.arcs[1][4] = G.arcs[4][1] = 40;
    G.arcs[2][3] = G.arcs[3][2] = 40;
    G.arcs[3][4] = G.arcs[4][3] = 70;
    G.arcs[3][5] = G.arcs[5][3] = 70;
    G.arcs[4][6] = G.arcs[6][4] = 50;
    G.arcs[5][6] = G.arcs[6][5] = 80;
    G.arcs[5][8] = G.arcs[8][5] = 40;
    G.arcs[6][7] = G.arcs[7][6] = 10;
    G.arcs[7][8] = G.arcs[8][7] = 80;
    G.arcs[7][10] = G.arcs[10][7] = 50;
    G.arcs[8][9] = G.arcs[9][8] = 20;
    G.arcs[8][10] = G.arcs[10][8] = 30;
}

void Dijkstra(AMGraph G, int v0)
{
    int n = G.vexnum;
    bool s[MAX];
    for (int v = 0; v < n; v++)
    {
        s[v] = false;
        dDoor[v] = G.arcs[v0][v];
        if (dDoor[v] < INF)
            pathDoor[v] = v0;
        else
            pathDoor[v] = -1;
    }
    s[v0] = true;
    dDoor[v0] = 0;
    for (int i = 1; i < n; i++)
    {
        int min = INF;
        int v;
        for (int w = 0; w < n; w++)
        {
            if (!s[w] && dDoor[w] < min)
            {
                v = w;
                min = dDoor[w];
            }
        }
        s[v] = true;
        for (int w = 0; w < n; w++)
        {
            if (!s[w] && (dDoor[v] + G.arcs[v][w] < dDoor[w]))
            {
                dDoor[w] = dDoor[v] + G.arcs[v][w];
                pathDoor[w] = v;
            }
        }
    }
}

void Floyd(AMGraph G)
{
    for (int i = 0; i < G.vexnum; i++)
    {
        for (int j = 0; j < G.vexnum; j++)
        {
            d[i][j] = G.arcs[i][j];
            if (d[i][j] < INF && i != j)
                path[i][j] = i;
            else
                path[i][j] = -1;
        }
    }
    for (int k = 0; k < G.vexnum; k++)
    {
        for (int i = 0; i < G.vexnum; i++)
        {
            for (int j = 0; j < G.vexnum; j++)
            {
                if (d[i][k] + d[k][j] < d[i][j])
                {
                    d[i][j] = d[i][k] + d[k][j];
                    path[i][j] = path[k][j];
                }
            }
        }
    }
}

void Init(AMGraph &G)
{
    CreateUDN(G);
    Dijkstra(G, 10);
    Floyd(G);
}

void SpotSearch(AMGraph G)
{
    int num;
    int i;
    cout << "请输入想要查询的景点编号: ";
    cin >> num;
    i = BinarySearch(G, num);
    cout << "景点名称: " << G.vexs[i].name  << endl;
    cout << "简介: " << G.vexs[i].intro << endl;
}

void DoorPathSearch(AMGraph G)
{
    stack<int> st;
    for (int i = 0; i < G.vexnum; i++)
    {
        if (G.vexs[i].name == "大门")
            continue;
        cout << "景点: " << G.vexs[i].name << "\t距离: " << dDoor[i] << "米" << endl;
        int t = i;
        while (G.vexs[t].name != "大门")
        {
            st.push(t);
            t = pathDoor[t];
        }
        cout << "路径: ";
        cout << "大门";
        while (!st.empty())
        {
            cout << "->" << G.vexs[st.top()].name;
            st.pop();
        }
        cout << endl
             << endl;
    }
}

void PathSearch(AMGraph G)
{
    int v1, v2;
    stack<int> st;
    cout << "请输入路径的起始景点和终点景点的编号: ";
    cin >> v1 >> v2;
    int i = v1 = BinarySearch(G, v1);
    int j = v2 = BinarySearch(G, v2);
    while (i != j)
    {
        st.push(j);
        j = path[i][j];
    }
    cout << "起始景点: " << G.vexs[v1].name << "\t终点景点: " << G.vexs[v2].name << "\t距离: " << d[v1][v2] << "米" << endl
         << "路径: ";
    cout << G.vexs[v1].name;
    while (!st.empty())
    {
        cout << "->" << G.vexs[st.top()].name;
        st.pop();
    }
    cout << endl;
}

int main()
{
    AMGraph G;
    int select;
    Init(G);
    while (1)
    {
        cout << "******************************************************************************" << endl
             << "*                              校园导游系统                                  *" << endl
             << "*                                                                            *" << endl
             << "*    --------------------------------------------------------------------    *" << endl
             << "*    |                                                                  |    *" << endl
             << "*    |  [1]主楼 *  *  *  *  *  *  * [3]图书馆                           |    *" << endl
             << "*    |    *   *                         *   *                           |    *" << endl
             << "*    |     *      *                    *     *                          |    *" << endl
             << "*    |      *        [2]联通广场  *  *        *                         |    *" << endl
             << "*    |       *         *      *                *                        |    *" << endl
             << "*    |        *       *          *  *  *  *[4]汇文楼                    |    *" << endl
             << "*    |         *      *                     *   *                       |    *" << endl
             << "*    |           *    *          *  *  *  *     *                       |    *" << endl
             << "*    |             [5]食堂  *  *                *                       |    *" << endl
             << "*    |               *                          *                       |    *" << endl
             << "*    |               *                         *                        |    *" << endl
             << "*    |              *                          *                        |    *" << endl
             << "*    |             *                           *                        |    *" << endl
             << "*    |            *                      * [6]游泳馆                    |    *" << endl
             << "*    |      [7]3号教学楼 *  *  *  *  *  *       *                       |    *" << endl
             << "*    |            *                            *                        |    *" << endl
             << "*    |      [8]4号教学楼                       *                        |    *" << endl
             << "*    |          *    *                         *                        |    *" << endl
             << "*    |          *      *  *  *  *  *  *  *[9]晨曦广场                   |    *" << endl
             << "*    |            *                         *         *   *             |    *" << endl
             << "*    |                  *                *                 [10]宿舍楼   |    *" << endl
             << "*    |                         *      *                                 |    *" << endl
             << "*    |                          [11]大门                                |    *" << endl
             << "*    |                                                                  |    *" << endl
             << "*    --------------------------------------------------------------------    *" << endl
             << "*                                                                            *" << endl
             << "*       1. 景点信息查询                                                      *" << endl
             << "*       2. 校门口到其他景点的路径查询                                        *" << endl
             << "*       3. 校园各景点间的路径查询                                            *" << endl
             << "*       4. 退出                                                              *" << endl
             << "*                                                                            *" << endl
             << "******************************************************************************" << endl;
        cin >> select;
        switch (select)
        {
        case 1:
            SpotSearch(G);
            break;
        case 2:
            DoorPathSearch(G);
            break;
        case 3:
            PathSearch(G);
            break;
        case 4:
            return 0;
        }
        getchar();
        if (select >= 0 && select <= 4)
            getchar();
    }
    return 0;
}