#include <iostream>

using namespace std;

struct NODE
{
    int data;
    NODE *next;
};

void insert(NODE *head)
{
    NODE *node = nullptr, *p = head->next, *temp = head;
    node = (NODE*)malloc(sizeof(NODE));
    cin >> node->data;
    node->next = nullptr;
    if (head->next == nullptr)
    {
        head->next = node;
        return;
    }
    while (p->next != nullptr && p->data < node->data)
    {
        temp = p;
        p = p->next;
    }
    if (p->data > node->data)
        p = temp;
    node->next = p->next;
    p->next = node;
}

void reserve(NODE *head)
{
    NODE *p1 = head->next;
    NODE *p2 = nullptr;
    NODE *p3 = nullptr;
    if (p1 != nullptr)
        p2 = p1->next;
    else
        return;
    while (p2 != nullptr)
    {
        p3 = p2->next;
        p2->next = p1;
        p1 = p2;
        p2 = p3;
    }
    head->next->next = nullptr;
    head->next = p1;
}

NODE *merge(NODE *l1, NODE *l2)
{
    NODE *p1 = l1->next;
    NODE *p2 = l2->next;
    NODE *p3 = nullptr;
    if (p1 == nullptr)
    {
        reserve(p2);
        return p2;
    }
    if (p2 == nullptr)
    {
        reserve(p1);
        return p1;
    }
    if (p1->data <= p2->data)
    {
        p3 = p1;
        p1 = p1->next;
    }
    else
    {
        p3 = p2;
        p2 = p2->next;
    }
    while (p1 != nullptr && p2 != nullptr)
    {
        if (p1->data <= p2->data)
        {
            p3->next = p1;
            p1 = p1->next;
            p3 = p3->next;
        }
        else
        {
            p3->next = p2;
            p2 = p2->next;
            p3 = p3->next;
        }
    }
    if (p1 == nullptr)
        p3->next = p2;
    else
        p3->next = p1;
    if (l1->next == nullptr)
    {
        free(l1);
        reserve(l2);
        return l2;
    }
    else
    {
        free(l2);
        reserve(l1);
        return l1;
    }
}

int main()
{
    int n;
    NODE *head, *p;
    NODE *l1, *l2, *l3;
    head = (NODE*)malloc(sizeof(NODE));
    head->next = nullptr;
    cout << "输入要插入节点的数量: ";
    cin >> n;
    while (n--)
    {
        cout << "还需插入" << n + 1 << "个节点: " << endl;
        insert(head);
        cout << "按序插入后的结果为: ";
        p = head->next;
        while (p != nullptr)
        {
            cout << p->data << ' ';
            p = p->next;
        }
        cout << endl;
    }
    cout << "反转后的结果为: ";
    reserve(head);
    p = head->next;
    while (p != nullptr)
    {
        cout << p->data << ' ';
        p = p->next;
    }
    cout << endl;
    l1 = (NODE*)malloc(sizeof(NODE));
    l2 = (NODE *)malloc(sizeof(NODE));
    l1->next = l2->next = nullptr;
    cout << "输入l1要插入节点的数量: ";
    cin >> n;
    cout << "输入" << n << "个节点的数据: ";
    while (n--)
        insert(l1);
    cout << "输入l2要插入节点的数量: ";    
    cin >> n;
    cout << "输入" << n << "个节点的数据: ";
    while (n--)
        insert(l2);
    l3 = merge(l1, l2);
    cout << "合并后的结果为: ";
    p = l3->next;
    while (p != nullptr)
    {
        cout << p->data << ' ';
        p = p->next;
    }
    cout << endl;
    return 0;
}