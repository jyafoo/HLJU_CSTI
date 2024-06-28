package org.jyafool.os.e4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 进程控制块PCB
 * @Author jyafool
 * @Version 1.0
 * @Since 2024/6/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PCB {
    /**
     * 进程名称
     */
    private String name;
    /**
     * 进程内存大小
     */
    private int size;
    /**
     * 进程到达时间
     */
    private int arrivalTime;
    /**
     * 进程服务时间
     */
    private int burstTime;
    /**
     * 开始运行时间
     */
    private int firstTime;
    /**
     * 结束运行时间
     */
    private int finishedTime;
    /**
     * 已经运行的时间
     */
    private int runnedTime;
    /**
     * 运行时间
     */
    private int runningTime;
    /**
     * 完成标志：记录该进程是否服务完毕
     */
    private int flag;
    /**
     * 进程优先级
     */
    private int prior;
    /**
     * pcb指针
     */
    private PCB next;
    /**
     *
     */
    private int begin;


    public PCB(String name, int size, int arrivalTime,
               int burstTime, int prior) {
        this.name = name;
        this.size = size;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.firstTime = -1;
        this.finishedTime = -1;
        this.runnedTime = -1;
        this.runningTime = 0;
        this.flag = 0;
        this.prior = prior;
    }

    /**
     * 初始化进程控制块（PCB）并将其添加到链表的末尾。
     * 这个方法用于创建一个新的PCB对象，并将其插入到现有的PCB链表的末尾。
     *
     * @param name 进程名称
     * @param size 进程内存大小
     * @param arrival_time 进程到达时间
     * @param burst_time 进程执行时间
     * @param prior 进程优先级
     */
    public void initPCB(String name, int size, int arrival_time, int burst_time, int prior) {
        PCB p, pr = this;
        while (pr.getNext() != null) {
            pr = pr.getNext();
        }
        p = new PCB(name, size, arrival_time,
                burst_time, prior);
        pr.setNext(p);
        p.setNext(null);
    }

    /**
     * 清理所有进程控制块（PCB）的标志和运行时间。
     * 遍历进程链表，将每个进程的标志（flag）和运行时间（runningTime）重置为 0。
     */
    void cleanFlag() {
        PCB p = this.getNext();
        while (p != null) {
            p.setFlag(0);
            p.setRunningTime(0);
            p = p.getNext();
        }
    }

    /**
     * 显示所有PCB（进程控制块）的信息，查看系统中当前进程的状态。
     */
    void showPCB() {
        PCB p = this.getNext();
        while (p != null) {
            System.out.println(p.getName() + "  优先级:" + p.getPrior() + "  " +
                    " 到达时刻:" + p.getArrivalTime() + "  " +
                    "服务时间:" + p.burstTime);
            p = p.getNext();
        }
        System.out.println();
    }

   /* void Delete() {
        PCB p = this, pr = null;
        while (p != null) {
            pr = p;
            p = p.getNext();
        }
    }*/
}
