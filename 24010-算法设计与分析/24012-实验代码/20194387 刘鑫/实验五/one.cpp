#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <algorithm>
#define N 110
int c[N][N], dis[N], pre[N];
struct node {
    int x, y;
}q[N];
int top;

void down(int x)
{
    int k = x << 1, t = x;
    if(k > top)     return ;
    if(q[k].x < q[t].x)     t = k;
    if(k + 1 <= top && q[k + 1].x < q[t].x)     t = k + 1;
    if(t != x) {
        node tmp = q[t];
        q[t] = q[x];
        q[x] = tmp;
        down(t);
    }
}
void up(int x)
{
    int fa = x >> 1;
    if(fa < 1)      return ;
    if(q[x].x < q[fa].x) {
        node tmp = q[x];
        q[x] = q[fa];
        q[fa] = tmp;
        up(fa);
    }
}
void dfs(int x)
{
    if(pre[x] == 0) {
        printf("%d", x);
        return ;
    }
    dfs(pre[x]);
    printf(" %d", x);
}
int main()
{
    int n, m, s;
    memset(c, 0x3f, sizeof c);
    memset(dis, 0x3f, sizeof dis);
    printf("请输入结点数和路径数: ");
    scanf("%d%d", &n, &m);
    printf("请输入相邻结点和其路径:\n");
    srand((unsigned)time(0));
    for(int i = 0; i < m; i ++ ) {
        int x, y, w;
//        scanf("%d%d%d", &x, &y, &w);
        x = rand() % n + 1;
        do {
            y = rand() % n + 1;
        }while(y == x);
        w = rand() % 20 + 1;
        printf("%d %d %d\n", x, y, w);
        c[y][x] = c[x][y] = std::min(c[x][y], w);
    }
    do {
        printf("\n请输入起点(0则退出): ");
        scanf("%d", &s);
        if(!s)      break;
        dis[s] = 0;
        q[++ top] = {0, s};
        while(top > 0) {
            node t = q[1];
            q[1] = q[top --];
            down(1);
            for(int i = 1; i <= n; i ++ ) {
                if(t.y == i)     continue;
                if(dis[i] > t.x + c[t.y][i]) {
                    dis[i] = t.x + c[t.y][i];
                    q[++ top] = {dis[i], i};
                    up(top);
                    pre[i] = t.y;
                }
            }
        }
        for(int i = 1; i <= n; i ++ ) {
            if(dis[i] >= 0x3f) {
                printf("起点与点%d无路径\n", i);
                continue;
            }
            printf("到点%d的最短路径长度为%d, 路径为: ", i, dis[i]);
            dfs(i);
            puts("");
        }
        memset(dis, 0x3f, sizeof dis);
        memset(pre, 0, sizeof pre);
    }while(1);
    return 0;
}

/*
5 5
5 2 1
2 1 4
2 1 8
2 5 10
1 2 13
*/
