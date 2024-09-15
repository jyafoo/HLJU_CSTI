#include <iostream>

using namespace std;

struct List
{
    char data;
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

void ListClassify(List *l, List *res[])
{
    List *p1 = res[0];
    List *p2 = res[1];
    List *p3 = res[2];
    List *p = nullptr;
    List *pre = nullptr;
    int len;
    if (l == nullptr)
    {
        return;
    }
    p = l->next;
    pre = l;
    do
    {
        if (p->data >= 'a' && p->data <= 'z' || p->data >= 'A' && p->data <= 'Z')
        {
            p1 = p;
            pre->next = p->next;
            if (res[0] == nullptr)
            {
                res[0] = p1;
                res[0]->next = res[0];
            }
            else
            {
                p1->next = res[0]->next;
                res[0]->next = p1;
                res[0] = p1;
            }
            p = pre;
        }
        else if (p->data >= '0' && p->data <= '9')
        {
            p2 = p;
            pre->next = p->next;
            if (res[1] == nullptr)
            {
                res[1] = p2;
                res[1]->next = res[1];
            }
            else
            {
                p2->next = res[1]->next;
                res[1]->next = p2;
                res[1] = p2;
            }
            p = pre;
        }
        else
        {
            p3 = p;
            pre->next = p->next;
            if (res[2] == nullptr)
            {
                res[2] = p3;
                res[2]->next = res[2];
            }
            else
            {
                p3->next = res[2]->next;
                res[2]->next = p3;
                res[2] = p3;
            }
            p = pre;
        }
        pre = p;
        p = p->next;
    } while(p != l);
    if (l->data >= 'a' && l->data <= 'z' || l->data >= 'A' && l->data <= 'Z')
    {
        l->next = res[0]->next;
        res[0]->next = l;
        res[0] = l;
    }
    else if (l->data >= '0' && l->data <= '9')
    {
        l->next = res[1]->next;
        res[1]->next = l;
        res[1] = l;
    }
    else
    {
        l->next = res[2]->next;
        res[2]->next = l;
        res[2] = l;
    }
}

int main()
{
    List *L = InitList();
    List *l[3];
    for (int i = 0; i < 3; i++)
    {
        l[i] = nullptr;
    }
    ListClassify(L, l);
    for (int i = 0; i < 3; i++)
    {
        ShowList(l[i]);
    }
    return 0;
}