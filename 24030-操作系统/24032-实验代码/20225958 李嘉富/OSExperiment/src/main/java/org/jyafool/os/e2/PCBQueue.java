package org.jyafool.os.e2;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;

/**
 * PCB存储队列
 *
 * @author JIA
 * @version 1.0
 * @since 2024/5/27
 */
@Data
@AllArgsConstructor
public class PCBQueue {
    /**
     * 存储pcb的队列
     */
    private final LinkedList<PCB> queue;

    /**
     * 队列名称
     */
    private final String name;

    /**
     * 队列大小
     */
    private final int capacity;

    public PCBQueue(String name, int capacity) {
        queue = new LinkedList<>();
        this.name = name;
        this.capacity = capacity;
    }

    /**
     * 将pcb添加至队尾
     * @param pcb 进程控制块
     * @return ture:添加成功；false：添加失败
     */
    public boolean offer(PCB pcb){
        if(pcb != null && queue.size() < capacity){
            queue.offerLast(pcb);
            System.out.println("进程" + pcb.getId() + "已加入" + name + "队尾");
            return true;
        }
        return false;
    }

    /**
     * 从队头移除PCB
     * @return pcb
     */
    public PCB remove(){
        if(!queue.isEmpty()){
            return queue.removeFirst();
        }
        return null;
    }

    /**
     * 判断队列是否为空
     * @return ture：空；false：不为空
     */
    public boolean isEmpty(){
        return queue.isEmpty();
    }

    /**
     * 输出队列中的pcb信息
     */
    public void show(){
        System.out.print(this.name+": ");
        for (PCB pcb : queue) {
            System.out.print(pcb.getId() + " ");
        }
        System.out.println();
    }

    public PCB getFirst() {
        return queue.getFirst();
    }
}
