package com.example.six.one;

import lombok.Data;

import java.util.NoSuchElementException;

@Data
public class LinkedList<E> {

    //链表头
     Node<E> head;
    //记录节点个数
    int size = 0;

    /**
     * 链表长度
     * @return
     */
    public int size() {
        return size;
    }


    /**
     * 添加节点
     * @param data
     */
    public void add(E data){
        //创建包含data值的node节点
        Node<E> newNode = new Node<E>(data);
        //判断列表是否为空
        //若为空，赋为头
        if (head == null) {
            head = newNode;
            size++;
            //不为空，找最后一个节点
        } else {
            //
            Node<E> temp = head;
            //利用temp遍历，直到最后一个节点
            while (temp.getNext() != null){
                //temp指向下一节点
                temp = temp.getNext();
            }
            //把当前节点连接在最后一个节点上
            temp.setNext(newNode);
            size++;
        }
    }

    /**
     * 删除节点
     * @param data
     * @return
     */
    public boolean remove(E data) {
        // 链表为空
        if (head == null) {
            return false;
        }
        // 删头节点
        if (head.getData().equals(data)) {
            head = head.getNext();
            size--;
            return true;
        }
        // 链表不为空且不为头节点
        // 当下一节点不为空且下一节点数据与要删除数据不同时，循环遍历至要删除节点
        Node current = head;
        while (current.getNext() != null && !current.getNext().getData().equals(data)) {
            current = current.getNext();
        }
        // 循环遍历完，发现无该书数据节点时
        if (current.getNext() == null) {
            return false;
        }
        current.setNext(current.getNext().getNext());
        size--;
        return true;
    }

    /**
     * 判断链表是否为空
     * @return 布尔值
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     *  根据下标返回节点对象
     * @param index
     * @return 节点
     */
    public E get(int index){
        if (isElementIndex(index)){
            // 为真，说明该下标存在
            Node<E> x = head;
            for (int i = 0; i < index; i++) {
                x = x.getNext();
            }
            return x.getData();
        }else {
            // 无此节点
            return null;
        }
    }

    public boolean set(int index,E data){
        if (isElementIndex(index)){
            // 为真，说明该下标存在
            Node<E> p = head;

            // 这里可以设置当下标是0时，直接为空赋值
            if (head == null)
                return false;

            for (int i = 0; i < index; i++) {
                if (p.getNext() != null){
                    p = p.getNext();
                }
            }
            p.setData(data);
            return true;
        }else {
            // 下标不存在
            return false;
        }
    }

    /**
     * 根据数据找节点
     * @param data
     * @return 节点
     */
    public Node<E> search(E data) {
        Node<E> p = head;
        while (p != null){
            if (p.getData().equals(data)){
                return p;
            }
            p = p.getNext();
        }
        return null;
    }

    public boolean insetAfter(Node<E> previous, E data){
        if (previous == null){
            return false;
        }

        Node<E> newNode = new Node<E>(data);
        newNode.setNext(previous.getNext());
        previous.setNext(newNode);
        size++;
        return true;

    }


    /**
     * 判断是下标是否合法
     * @param index
     * @return
     */
    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

   /* @Override
    public String toString() {
        return "LinkedList{" +
                "head=" + head +
                ", size=" + size +
                '}';
    }*/
}
