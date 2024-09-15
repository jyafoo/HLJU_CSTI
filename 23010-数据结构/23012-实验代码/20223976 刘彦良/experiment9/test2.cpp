#include <iostream>
#include <random>
#include <ctime>
#include <conio.h>

using namespace std;

typedef struct
{
    int *r;
    int length;
} Array;

typedef struct BSTNode
{
    int data;
    struct BSTNode *lchild, *rchild;
} BSTNode, *BSTree;

BSTree SearchBST(BSTree T, int key, int &cnt)
{
    if (!T)
        return T;
    cnt++;
    if (key == T->data)
        return T;
    else if (key < T->data)
        return SearchBST(T->lchild, key, cnt);
    else
        return SearchBST(T->rchild, key, cnt);
}

void InsertBST(BSTree &T, int e)
{
    if (!T)
    {
        BSTree S = new BSTNode;
        S->data = e;
        S->lchild = S->rchild = nullptr;
        T = S;
    }
    else if (e < T->data)
        InsertBST(T->lchild, e);
    else if (e > T->data)
        InsertBST(T->rchild, e);
}

void CreatBST(BSTree &T, Array &a)
{
    T = nullptr;
    int n;
    int low, high;
    int x;
    cout << "输入树中元素的个数: ";
    cin >> n;
    a.length = n;
    a.r = new int[n];
    cout << "输入元素的下限: ";
    cin >> low;
    cout << "输入元素的上限: ";
    cin >> high;
    uniform_int_distribution<int> u(low, high);
    default_random_engine e;
    e.seed(time(0));
    for (int i = 0; i < n; i++)
    {
        a.r[i] = u(e);
        if (SearchBST(T, a.r[i], x))
        {
            i--;
            continue;
        }
        InsertBST(T, a.r[i]);
    }
}

void DeleteBST(BSTree &T, int key)
{
    BSTree p = T;
    BSTree f = nullptr;
    while (p)
    {
        if (p->data == key)
            break;
        f = p;
        if (p->data > key)
            p = p->lchild;
        else
            p = p->rchild;
    }
    if (!p)
    {
        cout << "未找到该元素" << endl;
        return;
    }
    BSTree q = p;
    if ((p->lchild) && (p->rchild))
    {
        BSTree s = p->lchild;
        while (s->rchild)
        {
            q = s;
            s = s->rchild;
        }
        p->data = s->data;
        if (q != p)
            q->rchild = s->lchild;
        else
            q->lchild = s->lchild;
        delete s;
        cout << "删除完毕" << endl;
        return;
    }
    else if (!p->rchild)
        p = p->lchild;
    else if (!p->lchild)
        p = p->rchild;
    if (!f)
        T = p;
    else if (q == f->lchild)
        f->lchild = p;
    else
        f->rchild = p;
    delete q;
    cout << "删除完毕" << endl;
}

void MidOrderTraverse(BSTree T)
{
    if (T)
    {
        MidOrderTraverse(T->lchild);
        cout << T->data << ' ';
        MidOrderTraverse(T->rchild);
    }
}

int main()
{
    BSTree T;
    BSTree S;
    int e;
    int select;
    int cnt;
    Array a;
    while (1)
    {
        cout << "=============================" << endl
             << "*         二叉排序树        *" << endl
             << "*                           *" << endl
             << "*     1-----建立            *" << endl
             << "*     2-----输出            *" << endl
             << "*     3-----插入            *" << endl
             << "*     4-----查找            *" << endl
             << "*     5-----删除            *" << endl
             << "*     0-----退出            *" << endl
             << "=============================" << endl;
        cin >> select;
        switch (select)
        {
        case 1:
            CreatBST(T, a);
            break;
        case 2:
            cout << "初始数组为: ";
            for (int i = 0; i < a.length; i++)
                cout << a.r[i] << ' ';
            cout << endl;
            cout << "中序遍历二叉排序树的结果为: ";
            MidOrderTraverse(T);
            cout << endl;
            break;
        case 3:
            cout << "输入插入元素的大小: ";
            cin >> e;
            InsertBST(T, e);
            cout << "插入完成" << endl;
            break;
        case 4:
            cout << "请输入想要查找的元素: ";
            cin >> e;
            cnt = 0;
            S = SearchBST(T, e, cnt);
            if (S)
                cout << "查找成功" << endl;
            else
                cout << "未查找到该元素" << endl;
            cout << "查找次数为: " << cnt << endl;
            break;
        case 5:
            cout << "请输入想要删除的元素: ";
            cin >> e;
            DeleteBST(T, e);
            break;
        case 0:
            return 0;
        }
        if (select >= 0 && select <= 5)
        {
            cout << "按下任意键继续...";
            getch();
            cout << endl;
        }
    }
    return 0;
}