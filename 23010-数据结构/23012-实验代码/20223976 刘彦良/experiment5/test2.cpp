#include <iostream>
#define MAXTSIZE 100
#define MAXSIZE 100

using namespace std;

typedef int SqBiTree[MAXTSIZE];

typedef struct
{
    int *base;
    int *top;
    int stacksize;
} Stack;

int InitStack(Stack &s)
{
    s.base = new int[MAXSIZE];
    if (!s.base)
        exit(0);
    s.top = s.base;
    s.stacksize = MAXSIZE;
    return 1;
}

int Push(Stack &s, int e)
{
    if (s.top - s.base == s.stacksize)
        return 0;
    *s.top++ = e;
    return 1;
}

int Pop(Stack &s, int &e)
{
    if (s.top == s.base)
        return 0;
    e = *--s.top;
    return 1;
}

int GetTop(Stack s)
{
    if (s.top != s.base)
        return *(s.top - 1);
    return -1;
}

int StackEmpty(Stack s)
{
    if (s.base == s.top)
        return 1;
    else
        return 0;
}

int CreateBiTree(SqBiTree &t)
{
    int n;
    for (int i = 0; i < MAXTSIZE; i++)
        t[i] = 0;
    cout << "请输入完全二叉树的节点数量: ";
    cin >> n;
    if (n > MAXTSIZE) 
    {
        cout << "节点数量大于最大值" << endl;
        return 0;
    }
    cout << "请输入n个节点的整数型数据: " << endl;
    for (int i = 0; i < n; i++)
        cin >> t[i];
    return 1;
}

void PreOrderTraverse(SqBiTree t)
{
    Stack s;
    InitStack(s);
    int i = 0;
    int tmp;
    while (t[i] || !StackEmpty(s))
    {
        if (t[i])
        {
            Push(s, i);
            i = (i + 1) * 2 - 1;
        }
        else
        {
            Pop(s, tmp);
            cout << t[tmp] << ' ';
            i = (tmp + 1) * 2;
        }
    }
    cout << endl;
}

int main()
{
    SqBiTree t;
    CreateBiTree(t);
    cout << "先序遍历的结果为: " << endl;
    PreOrderTraverse(t);
    return 0;
}