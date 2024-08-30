#include <iostream>

using namespace std;

struct List
{
    int data;
    List *next;
};

List *InitList()
{
    int n;
    List *p;
    List *tail = nullptr;
    cout << "请输入链表的长度: ";
    cin >> n;
    cout << "请输入" << n << "个节点的值: ";
    while (n--)
    {
        p = new List();
        cin >> p->data;
        p->next = p;
        if (tail == nullptr)
        {
            tail = p;
        }
        else
        {
            p->next = tail->next;
            tail->next = p;
            tail = p;
        }
    }
    return tail;
}

void ShowList(List *l)
{
    List *p = nullptr;
    if (l == nullptr)
    {
        cout << "链表没有节点！" << endl;
        return;
    }
    p = l->next;
    do
    {
        cout << p->data << ' ';
        p = p->next;
    } while (p != l->next);
    cout << endl;
}

List *DeleteNode(List *l)
{
    int x = 0;
    List *p = nullptr;
    List *pre = nullptr;
    int first = 0;
    if (l == nullptr)
    {
        cout << "链表没有节点！" << endl;
        return l;
    }
    cout << "请输入想要删除前驱的节点: ";
    cin >> x;
    if (l->next == l && l->data == x)
    {
        delete l;
        l = nullptr;
        return l;
    }
    first = l->next->data;
    pre = l;
    p = l->next;
    do
    {
        if (p->next->data == x)
        {
            pre->next = p->next;
            delete p;
            p = pre;
        }
        pre = p;
        p = p->next;
    } while (p != l);
    if (first == x)
    {
        l = pre;
        pre->next = p->next;
        delete p;
        p = pre;
    }
    return l;
}

int main()
{
    List *l = InitList();
    cout << "链表的内容为: ";
    ShowList(l);
    l = DeleteNode(l);
    cout << "删除操作后，链表的内容为: ";
    ShowList(l);
    return 0;
}