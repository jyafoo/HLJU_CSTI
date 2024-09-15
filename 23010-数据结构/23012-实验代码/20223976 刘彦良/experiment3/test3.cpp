#include <iostream>

using namespace std;

struct List
{
    int data;
    List *prior;
    List *next;
    int freq;
};

List *InitList()
{
    int n;
    List *p;
    List *head = new List();
    List *pr = head;
    head->prior = nullptr;
    head->next = nullptr;
    cout << "请输入链表的长度: ";
    cin >> n;
    cout << "请输入" << n << "个节点的值: ";
    while (n--)
    {
        p = new List();
        cin >> p->data;
        p->next = nullptr;
        p->freq = 0;
        p->prior = pr;
        pr->next = p;
        pr = pr->next;
    }
    return head;
}

void ShowList(List *l)
{
    List *p = nullptr;
    if (l == nullptr || l->next == nullptr)
    {
        cout << "链表没有节点！" << endl;
        return;
    }
    p = l->next;
    cout << "链表中的元素为: ";
    while (p != nullptr)
    {
        cout << p->data << ' ';
        p = p->next;
    }
    cout << endl;
    p = l->next;
    cout << "各个元素的访问频率为: ";
    while (p != nullptr)
    {
        cout << p->freq << ' ';
        p = p->next;
    }
    cout << endl;
}

void LOCATE(List *l, int x)
{
    List *p1 = nullptr;
    List *p2 = nullptr;
    List *p3 = nullptr;
    if (l == nullptr || l->next == nullptr)
    {
        cout << "链表没有节点！" << endl;
        return;
    }
    p1 = l->next;
    while (p1 != nullptr)
    {
        if (p1->data == x)
        {
            p1->freq++;
            if (p1->freq <= p1->prior->freq || p1->prior == l)
            {
                p1 = p1->next;
                continue;
            }
            p2 = p1;
            p1 = p1->prior;
            p2->prior->next = p2->next;
            if (p2->next != nullptr)
            {
                p2->next->prior = p2->prior;
            }
            p2->prior = nullptr;
            p2->next = nullptr;
            p3 = l->next;
            while (p3 != nullptr)
            {
                if (p3->freq <= p2->freq)
                {
                    p2->prior = p3->prior;
                    p2->next = p3;
                    p3->prior->next = p2;
                    p3->prior = p2;
                    break;
                }
                p3 = p3->next;
            }
        }
        p1 = p1->next;
    }
}

int main()
{
    List *l = InitList();
    int n = 0;
    int x = 0;
    ShowList(l);
    cout << "请输入访问次数: ";
    cin >> n;
    while (n--)
    {
        cout << "请输入想要访问的节点: ";
        cin >> x;
        LOCATE(l, x);
        ShowList(l);
    }
    return 0;
}