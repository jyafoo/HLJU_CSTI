package org.jyafool.os.e1;

import lombok.Data;

import java.util.Scanner;

/**
 * 模拟单道操作系统
 * @author JIA
 * @version 1.0
 * @since 2024/5/9
 */
@Data
public class OS {

    /**
     * 操作系统运行状态：true/false
     */
    private boolean isRun;

    /**
     * 就绪队列、执行队列、阻塞队列
     */
    private final PCBQueue readyQueue,runingQueue,blockQueue;

    /**
     * 内存
     */
    private final Memory memory;

    /**
     * 键盘输入
     */
    private Scanner scanner;

    /**
     * 初始化
     */
    public OS() {
        this.readyQueue = new PCBQueue("就绪队列",10);
        this.runingQueue = new PCBQueue("执行队列",1);
        this.blockQueue = new PCBQueue("阻塞队列",10);

        scanner = new Scanner(System.in);
        System.out.println("请输入内存基址和大小：");
        int baseAddr = scanner.nextInt();
        int capacity = scanner.nextInt();
        this.memory = new Memory(baseAddr,capacity);

        isRun = true;
    }

    /**
     * 运行界面
     */
    public void run() {
        System.out.println("进程管理系统运行中：输入 H(help) 查看命令列表");
        while(isRun){
            System.out.println("******************************");
            System.out.print("->");
            String command = scanner.next().toUpperCase();
            switch (command) {
                case "Q" :
                    isRun = false;
                    break;
                case "H" :
                    help();
                    break;
                case "C" :
                    creat();
                    break;
                case "T" :
                    terminate();
                    break;
                case "E" :
                    end();
                    break;
                case "B" :
                    block();
                    break;
                case "W" :
                    wake();
                    break;
                case "S" :
                    show();
                    break;
                default :
                    System.out.println("无效命令，请重新输入!");
                    break;
            }
        }
    }

    /**
     * 创建进程
     */
    private void creat() {
        System.out.println("请输入新进程的名字与大小(空格隔开)：");
        PCB pcb =new PCB(scanner.next(), scanner.nextInt());
        if (memory.add(pcb)){
            readyQueue.offer(pcb);
            if(runingQueue.isEmpty()) {
                runingQueue.offer(readyQueue.remove());
            }
        }
    }

    /**
     * 将当前运行态的进程阻塞
     */
    private void block(){
        blockQueue.offer(runingQueue.remove());

        if(readyQueue.isEmpty()){
            System.out.println("就绪队列为空");
        }else{
            runingQueue.offer(readyQueue.remove());
        }
    }

    /**
     * 唤醒进程
     * 1.将当前阻塞队列的进程唤醒
     * 2.添加到就绪队列
     * 3.将就绪态的进程更为执行态
     */
    private void wake() {
        if(blockQueue.isEmpty()){
            System.out.println("阻塞队列为空");
        }else {
            readyQueue.offer(blockQueue.remove());
        }

        if(runingQueue.isEmpty()) {
            runingQueue.offer(readyQueue.remove());
        }
    }

    /**
     * cpu时间片用完，重新添加至就绪队列尾,将就绪队列头的pcb变为执行态
     */
    private void end() {
        readyQueue.offer(runingQueue.remove());
        runingQueue.offer(readyQueue.remove());
    }

    /**
     * 终止正在执行的进程
     */
    private void terminate(){
        if(runingQueue.isEmpty()){
            System.out.println("执行态为空");
        }

        if (memory.remove(runingQueue.remove())) {
            runingQueue.offer(readyQueue.remove());
        }
    }

    /**
     * 展示各队列和内存信息
     */
    private void show(){
        readyQueue.show();
        runingQueue.show();
        blockQueue.show();
        memory.show();
    }


    /**
     * 查看操作系统命令
     */
    private void help(){
        System.out.println("查看队列：S (show：查看各队列情况以及剩余内存)");
        System.out.println("创建进程：C (create：创建一个新进程加入就绪队列尾)");
        System.out.println("终止进程：T (terminate：终止处于执行队列的进程)");
        System.out.println("时间片到：E (end：将执行进程放入就绪队列尾，将就绪队列头加入执行队列)");
        System.out.println("阻塞进程：B (block：阻塞执行进程，加入阻塞队列)");
        System.out.println("唤醒进程：W (wake：唤醒阻塞队列队首进程，加入就绪队列尾)");
        System.out.println("退出程序：Q (quit)");
    }

    public static void main(String[] args) {
        OS os = new OS();
        os.run();
    }
}
