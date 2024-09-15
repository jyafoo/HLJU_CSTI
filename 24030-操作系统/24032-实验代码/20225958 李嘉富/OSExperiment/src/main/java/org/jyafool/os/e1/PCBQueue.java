package org.jyafool.os.e1;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 存储不同PCB状态的队列：阻塞，就绪，执行
 * @author JIA
 * @version 1.0
 * @since 2024/5/9
 */
@Data
@AllArgsConstructor
public class PCBQueue {
    /**
     * 双端队列存储 PCB
     */
    private final Deque<PCB> queue = new LinkedList<>();

    /**
     * 队列名称
     */
    private final String name;

    /**
     * 队列容量
     */
    private final int capacity;


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
}
