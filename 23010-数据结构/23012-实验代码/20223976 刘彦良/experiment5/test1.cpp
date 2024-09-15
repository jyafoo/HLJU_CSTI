#include <iostream>

using namespace std;

typedef struct BiTNode
{
    char data;
    BiTNode *lchild, *rchild;
} * BiTree;

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

int Depth(BiTree t)
{
    if (t == nullptr)
        return 0;
    else
    {
        int m = Depth(t->lchild);
        int n = Depth(t->rchild);
        if (m > n)
            return (m + 1);
        else
            return (n + 1);
    }
}

int main()
{
    BiTree t;
    cout << "按照先序遍历的方式输入二叉树中的字符型数据，当数据值为\"#\"时代表该节点为空: " << endl;
    CreateBiTree(t);
    cout << "二叉树的深度为: " << Depth(t) << endl;
    return 0;
}

/*
AB#CD##E##F#GH###
*/