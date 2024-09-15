#include <iostream>

using namespace std;

struct List
{
    int data;
    List *next;
};

struct Queue
{
    List *front;
    List *rear;
};

void InitQueue(Queue &queue)
{
    queue.front = queue.rear = new List();
    queue.front->next = queue.front;
}

void ShowQueue(Queue queue)
{
    List *p = nullptr;
    if (queue.front == queue.rear)
    {
        cout << "队列中没有元素！" << endl;
        return;
    }
    p = queue.front->next;
    while (p != queue.front)
    {
        cout << p->data << ' ';
        p = p->next;
    }
    cout << endl;
}

void QueuePush(Queue &queue, int x)
{
    List *p = new List();
    p->data = x;
    queue.rear->next = p;
    queue.rear = p;
    queue.rear->next = queue.front;
}

int QueuePop(Queue &queue)
{
    List *p = queue.front->next;
    if (queue.front == queue.rear)
    {
        cout << "该队列为空，没有元素可以出队列! " << endl;
        return -1;
    }
    int res = p->data;
    if (queue.rear == queue.front->next)
        queue.rear = queue.front;
    queue.front->next = queue.front->next->next;
    delete p;
    return res;
}

void QueueClear(Queue &queue)
{
    List *p = queue.front->next;
    while (p != queue.front)
    {
        List *pre = p;
        p = p->next;
        delete pre;
    }
    queue.rear = queue.front = nullptr;
}

int main()
{
    Queue queue;
    InitQueue(queue);
    int select = 1;
    while (select)
    {
        cout << "当前队列中元素为: ";
        ShowQueue(queue);
        cout << "请选择下一步操作: " << endl;
        cout << " 0. 结束" << endl;
        cout << " 1. 进队列" << endl;
        cout << " 2. 出队列" << endl;
        cout << " 3. 清空队列" << endl;
        cin >> select;
        switch (select)
        {
        case 1:
            int x;
            cout << "请输入进入队列的元素: ";
            cin >> x;
            QueuePush(queue, x); 
            break;
        case 2:
            QueuePop(queue);
            break;
        case 3:
            QueueClear(queue);
            break;
        }
    }
    return 0;
}