#include <iostream>
#define MAXTSIZE 100

using namespace std;

typedef struct BiTNode
{
    char data;
    BiTNode *lchild, *rchild;
} *BiTree;

typedef struct StackNode
{
    BiTree data;
    StackNode *next;
} *Stack;

int InitStack(Stack &s)
{
    s = nullptr;
    return 1;
}

int Push(Stack &s, BiTree e)
{
    StackNode *p = new StackNode;
    p->data = e;
    p->next = s;
    s = p;
    return 1;
}

int Pop(Stack &s, BiTree &e)
{
    StackNode* p;
    if (s == nullptr)
        return 0;
    e = s->data;
    p = s;
    s = s->next;
    delete p;
    return 1;
}

BiTree GetTop(Stack s)
{
    if (s != nullptr)
        return s->data;
    return nullptr;
}

int StackEmpty(Stack s)
{
    if (s == nullptr)
        return 1;
    return 0;
}

void CreateBiTree(BiTree &t)
{
    char ch;
    cin >> ch;
    if (ch == '#')
        t = nullptr;
    else
    {
        t = new BiTNode;
        t->data = ch;
        CreateBiTree(t->lchild);
        CreateBiTree(t->rchild);
    }
}

void PreOrderTraverse(BiTree t)
{
    Stack s;
    BiTree p = t;
    BiTree q = new BiTNode;
    InitStack(s);
    if (p)
        Push(s, p);
    while (!StackEmpty(s))
    {
        Pop(s, q);
        cout << q->data;
        if (q->rchild)
            Push(s, q->rchild);
        if (q->lchild)
            Push(s, q->lchild);
    }
    cout << endl;
}

void InOrderTraverse(BiTree t)
{
    Stack s;
    BiTree p = t;
    BiTree q = new BiTNode;
    InitStack(s);
    while (p || !StackEmpty(s))
    {
        if (p)
        {
            Push(s, p);
            p = p->lchild;
        }
        else
        {
            Pop(s, q);
            cout << q->data;
            p = q->rchild;
        }
    }
    cout << endl;
}

void PostOrderTraverse(BiTree t)
{
    Stack s;
    BiTree p = t;
    BiTree pre = nullptr;
    InitStack(s);
    while (p || !StackEmpty(s))
    {
        if (p)
        {
            Push(s, p);
            p = p->lchild;
        }
        else
        {
            p = GetTop(s);
            if (p->rchild && p->rchild != pre)
                p = p->rchild;
            else
            {
                Pop(s, p);
                cout << p->data;
                pre = p;
                p = nullptr;
            }
        }
    }
    cout << endl;
}

int main()
{
    BiTree t;
    cout << "按照先序遍历的方式输入二叉树中的字符型数据，当数据值为\"#\"时代表该节点为空: " << endl;
    CreateBiTree(t);
    cout << "先序遍历的结果为: " << endl;
    PreOrderTraverse(t);
    cout << "中序遍历的结果为: " << endl;
    InOrderTraverse(t);
    cout << "后序遍历的结果为: " << endl;
    PostOrderTraverse(t);
    return 0;
}
