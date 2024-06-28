package com.example.six.one;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Node<E> {
    // 定义数据
    private E data;
    // 定义下一节点
    private Node<E> next;

    // 自定义单个参数构造方法
    public Node(E data) {
        this.data = data;
    }
}
