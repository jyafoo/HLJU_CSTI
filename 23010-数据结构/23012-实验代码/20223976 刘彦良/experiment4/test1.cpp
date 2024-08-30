#include <iostream>

using namespace std;

struct List
{
    char data;
    List *next;
};

List *InitList(List *l)
{
    l = new List();
    int n;
    List *pr = l;
    l->next = nullptr;
    cout << "请输入链表的长度: ";
    cin >> n;
    cout << "请输入" << n << "个节点的值: ";
    while (n--)
    {
        List *p = new List();
        cin >> p->data;
        p->next = nullptr;
        pr->next = p;
        pr = pr->next;
    }
    return l;
}

void ShowList(List *l)
{
    if (l == nullptr || l->next == nullptr)
    {
        cout << "链表没有节点！" << endl;
        return;
    }
    List *p = l->next;
    while (p != nullptr)
    {
        cout << p->data;
        p = p->next;
    }
    cout << endl;
}

List *DeleteList(List *l)
{
    List *pre = l;
    while (pre != nullptr)
    {
        List *p = pre->next;
        delete pre;
        pre = p;
    }
    return nullptr;
}

bool CheckList(List *l)
{
    List *p1 = l;
    List *p2 = l;
    List *stack = new List();
    bool flag = false;
    stack->next = nullptr;
    while (p2->next != nullptr)
    {
        p1 = p1->next;
        p2 = p2->next;
        if (p2->next != nullptr)
            p2 = p2->next;
        else
            flag = true;
        List *pr = new List();
        pr->data = p1->data;
        pr->next = stack->next;
        stack->next = pr;
    }
    if (flag)
        p2 = p1;
    else
        p2 = p1->next;
    p1 = stack->next;
    while (p1 != nullptr && p2 != nullptr)
    {
        if (p1->data != p2->data)
        {
            DeleteList(stack);
            return false;
        }
        p1 = p1->next;
        p2 = p2->next;
    }
    DeleteList(stack);
    return true;
}

int main()
{
    List *l;
    int select = 1;
    while (select)
    {
        l = InitList(l);
        cout << "链表的内容为: ";
        ShowList(l);
        if (CheckList(l))
            cout << "字符串是回文串! " << endl;
        else
            cout << "字符串不是回文串! " << endl;
        cout << "是否继续? " << endl;
        cout << " 1. 继续" << endl;
        cout << " 0. 结束" << endl;
        cin >> select;
    }
    return 0;
}