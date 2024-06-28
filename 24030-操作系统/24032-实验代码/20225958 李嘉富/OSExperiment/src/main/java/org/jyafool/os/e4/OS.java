package org.jyafool.os.e4;

import java.util.Scanner;

/**
 * 模拟操作系统
 *
 * @Author jyafool
 * @Version 1.0
 * @Since 2024/6/26
 */
public class OS {

    /**
     * 最大需求矩阵：max[n][m]:表示n个进程，m中资源
     */
    private int[][] max;
    /**
     * 分配矩阵：allocation[n][m]:表示所有进程的分配情况
     */
    private int[][] allocation;

    /**
     * 最多需求矩阵：need[n][m]:表示各进程最多还需要多少资源 = max - allocation
     */
    private int[][] need;
    /**
     * 可用资源数组：available[m]:表示当前系统中还有多少可用资源
     */
    private int[] available;
    /**
     * 申请资源数组：request[m]:表示某进程本次申请的各种资源量
     */
    private int[] request;
    /**
     * 安全标志：1表示处于安全状态，0表示处于不安全状态
     */
    private int safeFlag;
    /**
     * 资源数量
     */
    private int sourceCount;
    /**
     * 进程数量
     */
    private int processCount;
    /**
     * 作业数量
     */
    private int jobCount;

    public OS() {
        max = new int[100][100];
        allocation = new int[100][100];
        available = new int[100];
        need = new int[100][100];
        request = new int[100];
    }

    /**
     * 先来先服务（FCFS）算法调度进程
     * 遍历所有进程控制块（PCB），计算并打印每个进程的调度信息，包括到达时间、服务时间、开始时间、结束时间、周转时间。
     * 最后，计算并打印所有进程的平均周转时间和平均带权周转时间。
     *
     * @param head 进程控制块链表的头节点。
     */
    void FCFS(PCB head) {
        int Begin = 0;
        PCB p = head.getNext();
        while (p != null) {
            if (Begin < p.getArrivalTime()) {
                Begin = p.getArrivalTime();
            }
            if (Begin == 0) {
                p.setFirstTime(p.getArrivalTime());
                p.setFinishedTime(p.getFirstTime() + p.getBurstTime());
                p.setRunnedTime(p.getFinishedTime() - p.getArrivalTime());
            } else {
                p.setFirstTime(Begin);
                p.setFinishedTime(p.getFirstTime() + p.getBurstTime());
                p.setRunnedTime(p.getFinishedTime() - p.getArrivalTime());
            }
            System.out.print(p.getName() + "  优先级:" + p.getPrior() +
                    "   到达时刻:" + p.getArrivalTime() + "  服务时间:" + p.getBurstTime()
                    + "  开始时刻:" + p.getFirstTime() + "  结束时刻:" + p.getFinishedTime() + "  ");

            System.out.println("  周转时间:" + p.getRunnedTime() +
                    "   带权周转时间:" + p.getRunnedTime() / (float) p.getBurstTime());

            Begin = p.getFinishedTime();
            p = p.getNext();
        }
        float sum1 = 0, sum2 = 0;
        p = head.getNext();
        while (p != null) {
            sum1 += p.getRunnedTime();
            sum2 += p.getRunnedTime() / (float) p.getBurstTime();
            p = p.getNext();
        }
        System.out.println("  平均周转时间:" + sum1 / (float) jobCount +
                "   平均带权周转时间:" + sum2 / (float) jobCount);

        head.cleanFlag();
    }

    /**
     * 寻找最短作业
     * 从进程链表中找到一个特定条件下的最短进程，并对其进行标记或更新起始时间。
     *
     * @param head  进程链表的头节点。
     * @param Begin 用于记录开始时间的数组，数组第一个元素为当前系统时间。
     * @param aim   用于存储找到的最短进程节点。
     * @return 返回找到的最短进程节点。
     */
    PCB findShortestJob(PCB head, int[] Begin) {
        int Min = 999, flag = 0;
        PCB p = head.getNext();
        PCB aim = null;
        while (p != null)// p非空
        {
            if (p.getFlag() == 0 && p.getBurstTime() < Min && p.getArrivalTime() <= Begin[0]) {
                flag = 1;
                Min = p.getBurstTime();
                aim = p;
            }
            p = p.getNext();
        }
        if (flag == 1) {
            aim.setFlag(1);
        } else {
            Begin[0]++;
        }
        return aim;
    }

    /**
     * 优先级调度算法（Shortest Job First，最短作业优先）。
     * 处理优先级调度算法下的所有进程控制块（PCB）。
     *
     * @param head     表示进程控制块链表头部的指针。
     * @param pcbcount 表示进程控制块的数量。
     */
    void SJF(PCB head, int pcbcount) {
        int i = pcbcount;
        int[] Begin = new int[1];
        PCB p = null;
        while (i-- != 0) {
            do {
                p = findShortestJob(head, Begin);
            } while (p == null);// p空

            if (Begin[0] == 0) {
                p.setFirstTime(p.getArrivalTime());
                p.setFinishedTime(p.getFirstTime() + p.getBurstTime());
                p.setRunnedTime(p.getFinishedTime() - p.getArrivalTime());
            } else {
                p.setFirstTime(Begin[0]);
                p.setFinishedTime(p.getFirstTime() + p.getBurstTime());
                p.setRunnedTime(p.getFinishedTime() - p.getArrivalTime());
            }
            Begin[0] = p.getFinishedTime();
            System.out.print(p.getName() + "  优先级:" + p.getPrior() +
                    "   到达时刻:" + p.getArrivalTime() + "  服务时间:" + p.getBurstTime()
                    + "  开始时刻:" + p.getFirstTime() + "  结束时刻:" + p.getFinishedTime() + "  ");

            System.out.println("  周转时间:" + p.getRunnedTime() +
                    "   带权周转时间:" + p.getRunnedTime() / (float) p.getBurstTime());

        }
        float sum1 = 0, sum2 = 0;
        p = head.getNext();
        while (p != null) {
            sum1 += p.getRunnedTime();
            sum2 += p.getRunnedTime() / (float) p.getBurstTime();
            p = p.getNext();
        }
        System.out.println("  平均周转时间:" + sum1 / (float) pcbcount +
                "   平均带权周转时间:" + sum2 / (float) pcbcount);

        head.cleanFlag();
    }

    /**
     * 检查所有进程是否结束。
     * 遍历进程控制块（PCB）链表，检查每个进程的标志位来确定是否所有进程都已结束。
     *
     * @param head PCB链表的头节点。链表中的每个节点代表一个进程。
     * @return 如果所有进程都已结束，则返回true；否则返回false。
     */
    boolean isOver(PCB head) {
        PCB p = head.getNext();
        while (p != null) {
            if (p.getFlag() == 0) {
                return false;
            }
            p = p.getNext();
        }
        return true;
    }

    /**
     * 时间片轮转（RR）
     *
     * @param head     进程控制块链表的头结点。
     * @param time     每个时间片的长度。
     * @param pcbcount 进程控制块的数量。
     */
    void RR(PCB head, int time, int pcbcount) {
        if (head.getNext() != null) {
            int Begin = head.getNext().getArrivalTime();
            PCB p = head.getNext();
            PCB pr;
            while (!isOver(head)) {
                if (p.getBurstTime() - p.getRunningTime() > time) {// 正常情况
                    p.setRunningTime(p.getRunningTime() + time);
                    System.out.println(p.getName() + ": " + Begin + "-" + (Begin + time));
                    Begin = Begin + time;
                } else if (p.getBurstTime() - p.getRunningTime() <= time && p.getFlag() == 0) {// 进程完成，
                    p.setFlag(1);
                    int temp = p.getBurstTime() - p.getRunningTime();
                    p.setRunningTime(p.getRunningTime() + p.getBurstTime() - p.getRunningTime());
                    System.out.println(p.getName() + ": " + Begin + "-" + (Begin + time));
                    Begin += temp;
                    p.setFinishedTime(Begin);
                    p.setRunnedTime(p.getFinishedTime() - p.getArrivalTime());
                }
                pr = p;
                p = p.getNext();
                if (p == null) {// 时间片轮一圈
                    p = head.getNext();
                }
                if (Begin < p.getArrivalTime()) {
                    p = pr;
                    if (p.getBurstTime() - p.getRunningTime() <= 0) {
                        Begin++;
                    }
                }
            }
        }
        PCB p;
        float sum1 = 0, sum2 = 0;
        p = head.getNext();

        while (p != null) {
            sum1 += p.getRunnedTime();
            sum2 += p.getRunnedTime() / (float) p.getBurstTime();
            p = p.getNext();
        }
        System.out.println("  平均周转时间:" + sum1 / (float) pcbcount +
                "   平均带权周转时间:" + sum2 / (float) pcbcount);

        head.cleanFlag();
    }

    /**
     * 在PCB链表中查找并返回优先级最高的等待进程。
     *
     * @param head  链表的头节点，表示 PCB 链表的开始。
     * @param Begin 一个整型数组，用于记录开始时间。在这里，我们只使用了数组的第一个元素。
     * @param aim   一个指向 PCB 的指针，用于存储找到的目标进程。
     * @return 返回找到的优先级最高的等待进程，如果没有符合条件的进程，则返回 null。
     */
    PCB findHighestPrior(PCB head, int[] Begin) {
        int Max = -1, flag = 0;
        PCB p = head.getNext();
        PCB aim = null;
        while (p != null) {
            if (p.getFlag() == 0 && p.getPrior() > Max && p.getArrivalTime() <= Begin[0]) {
                flag = 1;
                Max = p.getPrior();
                aim = p;
            }
            p = p.getNext();
        }
        if (flag == 1) {
            aim.setFlag(1);
        } else {
            Begin[0]++;
        }
        return aim;
    }

    /**
     * 优先级调度
     * 对给定的进程控制块链表进行优先级调度模拟，计算并输出每个进程的调度信息。
     *
     * @param head 进程控制块链表的头节点。
     */
    void priorityScheduling(PCB head) {
        int i = jobCount;
        int[] Begin = new int[1];
        PCB p = null;
        while (i-- != 0) {
            do {
                p = findHighestPrior(head, Begin);
            } while (p == null);// p空

            if (Begin[0] == 0) {
                p.setFirstTime(p.getArrivalTime());
                p.setFinishedTime(p.getFirstTime() + p.getBurstTime());
                p.setRunnedTime(p.getFinishedTime() - p.getArrivalTime());
            } else {
                p.setFirstTime(Begin[0]);
                p.setFinishedTime(p.getFirstTime() + p.getBurstTime());
                p.setRunnedTime(p.getFinishedTime() - p.getArrivalTime());
            }
            Begin[0] = p.getFinishedTime();
            System.out.print(p.getName() + "  优先级:" + p.getPrior() +
                    "   到达时刻:" + p.getArrivalTime() + "  服务时间:" + p.getBurstTime()
                    + "  开始时刻:" + p.getFirstTime() + "  结束时刻:" + p.getFinishedTime() + "  ");

            System.out.println("  周转时间:" + p.getRunnedTime() +
                    "   带权周转时间:" + p.getRunnedTime() / (float) p.getBurstTime());
        }
        float sum1 = 0, sum2 = 0;
        p = head.getNext();
        while (p != null) {
            sum1 += p.getRunnedTime();
            sum2 += p.getRunnedTime() / (float) p.getBurstTime();
            p = p.getNext();
        }
        System.out.println("  平均周转时间:" + sum1 / (float) jobCount +
                "   平均带权周转时间:" + sum2 / (float) jobCount);

        head.cleanFlag();
    }


    /**
     * 显示系统当前的资源分配情况和进程需求。
     * 输出系统中各个进程的资源需求、已分配资源、最大资源需求以及系统当前的可用资源情况。
     */
    void showInfo() {
        int i, j;
        System.out.println("系统目前可利用的资源数量:\n          A,B,C");
        System.out.print("resource:  ");
        for (j = 0; j < sourceCount; j++) {
            System.out.print(available[j] + " ");
        }
        System.out.println();

        System.out.println("各进程的最大资源需求:");
        for (i = 0; i < processCount; i++) {
            System.out.print("pr" + i + ": ");
            for (j = 0; j < sourceCount; j++) {
                System.out.print(max[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("各进程已分配资源:");
        for (i = 0; i < processCount; i++) {
            System.out.print("pr" + i + ": ");
            for (j = 0; j < sourceCount; j++) {
                System.out.print(allocation[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("各进程还需求资源:");
        for (i = 0; i < processCount; i++) {
            System.out.print("pr" + i + ": ");
            for (j = 0; j < sourceCount; j++) {
                System.out.print(need[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 检查系统是否处于安全状态，确定是否可以为每个进程分配资源，以避免死锁。
     */
    void safeAlgorithm() {
        /* 初始化工作数组，用于存储每个源在当前状态下可用的资源数量 */
        int[] Work = new int[sourceCount];
        /* 初始化完成数组，用于标记每个进程是否已经分配到足够的资源 */
        int[] Finish = new int[processCount];
        /* 初始化结果数组，用于存储安全序列中的进程编号 */
        int[] result = new int[processCount];
        /* 初始化循环变量 */
        int i, j, k = 0;

        /* 将可用资源的数量复制到工作数组 */
        for (i = 0; i < sourceCount; i++) {
            Work[i] = available[i];
        }

        /* 初始化完成数组，所有进程初始都未完成 */
        for (i = 0; i < processCount; i++) {
            Finish[i] = 0;// 初始化为false
        }

        /* 尝试为每个进程分配资源 */
        for (i = 0; i < processCount; i++) {
            /* 检查当前进程是否可以分配资源 */
            for (j = 0; j < sourceCount; j++) {
                /* 如果当前进程已经完成或需要的资源超过可用数量，则停止检查 */
                if (Finish[i] == 1 || need[i][j] > Work[j]) {
                    break;
                }
            }
            /* 如果所有资源都满足条件，更新工作数组和完成状态 */
            if (j == sourceCount) {
                for (int m = 0; m < sourceCount; m++) {
                    Work[m] = Work[m] + allocation[i][m];
                }
                Finish[i] = 1;
                result[k] = i;
                k++;
                /* 重置进程索引，准备检查下一个进程 */
                i = -1;
            }
            /* 如果所有进程都已检查，但仍有进程未完成，则系统不安全 */
            if (i == processCount - 1) {
                for (int l = 0; l < processCount; l++) {
                    if (Finish[l] == 0) {
                        System.out.println("系统不安全");
                        safeFlag = 0;
                        return;
                    }
                }
            }
        }

        /* 如果所有进程都完成，系统是安全的 */
        System.out.println("系统资源分配安全!");
        /* 输出安全序列 */
        System.out.println("安全序列为: ");
        for (i = 0; i < processCount; i++) {
            System.out.println("pr" + result[i] + "  ");
        }
    }

    /**
     * 释放资源函数
     *
     * @param i 表示需要释放资源的进程编号。
     */
    void release(int i) {
        int j;
        // 遍历所有资源类型
        for (j = 0; j < sourceCount; j++) {
            // 更新可用资源量，将进程i释放的资源量加到可用资源量中
            available[j] = available[j] + request[j];
            // 更新进程i的资源分配情况，减去释放的资源量
            allocation[i][j] = allocation[i][j] - request[j];
            // 更新进程i的资源需求，加上释放的资源量
            need[i][j] = need[i][j] + request[j];
        }
    }


    /**
     * 分配资源的函数。
     * 根据请求向特定的资源分配者分配资源，并更新资源的可用情况和分配需求。
     * 如果所有需求都得到满足，则将分配的资源从可用资源中扣除，并重置分配者的需求。
     *
     * @param i 资源分配者的索引，表示当前进行资源分配的分配者。
     */
    void distribute(int i) {
        // 初始化一个标志，用于判断是否所有资源需求都已满足。
        Boolean flag = true;

        // 遍历所有资源类型，进行资源的分配和需求更新。
        for (int j = 0; j < sourceCount; j++) {
            // 更新可用资源量，减去当前请求的资源量。
            available[j] = available[j] - request[j];
            // 更新当前分配者所分配到的资源量，加上当前请求的资源量。
            allocation[i][j] = allocation[i][j] + request[j];
            // 更新当前分配者的需求量，减去当前请求的资源量。
            need[i][j] = need[i][j] - request[j];
            // 如果当前分配者的需求量不为0，则设置标志为false。
            if (need[i][j] != 0) {
                flag = false;
            }
        }

        // 如果所有分配者的需求都已满足（flag为true），则进行资源的最终分配和重置。
        if (flag) {
            // 将分配的资源从可用资源中扣除。
            for (int k = 0; k < sourceCount; k++) {
                available[k] = available[k] + allocation[i][k];
                // 重置当前分配者的资源分配量为0。
                allocation[i][k] = 0;
            }
        }
    }


    /**
     * 执行银行家算法。
     * 银行家算法是一种用于避免系统进入不安全状态的资源分配算法，确保系统安全。
     * 本方法中，首先从用户输入获取进程需要的资源申请，然后检查资源是否可分配，
     * 如果可以，进行资源分配并检查系统是否处于安全状态。如果不安全，则释放已分配的资源。
     */
    void bankerAlgorithm() {
        // 初始化输入设备
        Scanner scanner = new Scanner(System.in);
        // 安全标志，1表示安全，0表示不安全
        safeFlag = 1;
        int i, j;
        // 请求用户输入需要分配资源的进程号
        System.out.println("\n请输入需要分配资源的进程序号:");
        System.out.print(">");
        i = scanner.nextInt();
        // 请求用户输入进程i需要申请的资源数量
        System.out.println("请输入进程 " + i + " 申请的资源:");
        for (j = 0; j < sourceCount; j++) {
            System.out.print("第 " + (j + 1) + " 个资源:");
            request[j] = scanner.nextInt();
        }
        // 检查进程i的资源申请是否符合要求
        for (j = 0; j < sourceCount; j++) {
            if (request[j] > need[i][j]) {
                // 如果申请的资源超过需要的资源量，提示用户并退出
                System.out.println("进程 " + i + " 申请的资源大于它需要的资源");
                return;
            } else if (request[j] > available[j]) {
                // 如果申请的资源超过当前可用的资源量，提示用户并退出
                System.out.println("进程 " + i + " 申请的资源大于当前可利用资源");
                return;
            }
        }
        // 如果资源申请符合条件，进行资源分配
        distribute(i);
        // 检查系统是否处于安全状态
        safeAlgorithm();
        if (safeFlag == 0) {
            // 如果系统不安全，释放进程i占用的资源
            release(i);
            // 提示用户重新分配资源
            System.out.println("请重新分配资源!");
        }
        // 显示系统当前状态信息
        showInfo();
    }


    /**
     * 主函数，程序的入口点。
     * 提供了进程调度和银行家算法的模拟功能。
     * 用户通过命令行输入选择不同的算法和操作。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        OS os = new OS(); // 创建操作系统模拟对象
        String name, choice;
        int jobCount, j, flag;
        int arrivalTime, size, burstTime, prior;

        os.jobCount = 0; // 初始化进程数量
        PCB pcbHead = new PCB();
        pcbHead.setName("head"); // 初始化头节点名称为空
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // 提供用户选择功能的菜单
            System.out.println("请选择功能");
            System.out.println("1:进程调度");
            System.out.println("2:银行家算法");
            System.out.println("3:退出");
            int c = scanner.nextInt();
            if (c == 1) {
                // 进程调度功能选择
                System.out.print("请输入要添加的进程数:");
                jobCount = scanner.nextInt();
                for (j = 0; j < jobCount; j++) {
                    // 逐一输入进程的属性
                    System.out.println("请输入第" + (j + 1) + "个进程的名字,到达时间,服务时间,优先级:");
                    name = scanner.next();
                    arrivalTime = scanner.nextInt();
                    burstTime = scanner.nextInt();
                    prior = scanner.nextInt();
                    size = 1;
                    pcbHead.initPCB(name, size, arrivalTime, burstTime, prior);
                    os.jobCount++;
                }
                while (true) {
                    // 提供多种调度算法的选择
                    System.out.println("1:FCFS算法");
                    System.out.println("2:SJF算法");
                    System.out.println("3:时间片");
                    System.out.println("4:优先级");
                    System.out.println("5:显示进程");
                    System.out.println("6:退出");
                    System.out.print("请输入你的选择:");
                    choice = scanner.next();
                    switch (choice) {
                        case "1":
                            os.FCFS(pcbHead);
                            break;
                        case "2":
                            os.SJF(pcbHead, os.jobCount);
                            break;
                        case "3":
                            System.out.print("请输入时间片的大小:");
                            int time = scanner.nextInt();
                            os.RR(pcbHead, time, os.jobCount);
                            break;
                        case "4":
                            os.priorityScheduling(pcbHead);
                            break;
                        case "5":
                            pcbHead.showPCB();
                            break;
                        case "6":
                            // pcbHead.Delete();
                            System.out.println("谢谢使用!");
                            return;
                        default:
                            System.out.println("输入错误！");
                    }
                }
            } else if (c == 2) {
                System.out.print("请输入进程个数：");
                os.processCount = scanner.nextInt();
                System.out.println("请输入资源个数：");
                os.sourceCount = scanner.nextInt();
                for (jobCount = 0; jobCount < os.processCount; jobCount++) {
                    System.out.println("请输入第" + (jobCount + 1) + "个进程的最大资源:");
                    for (j = 0; j < os.sourceCount; j++) {
                        os.max[jobCount][j] = scanner.nextInt();
                    }
                    System.out.println("请输入第" + (jobCount + 1) + "个进程的已分配资源:");
                    for (j = 0; j < os.sourceCount; j++) {
                        os.allocation[jobCount][j] = scanner.nextInt();
                        os.need[jobCount][j] = os.max[jobCount][j] - os.allocation[jobCount][j];
                    }

                }
                System.out.println("请输入剩余资源个数:");
                for (jobCount = 0; jobCount < os.sourceCount; jobCount++) {
                    os.available[jobCount] = scanner.nextInt();
                }
                os.showInfo();
                os.safeAlgorithm();
                System.out.println("是否继续分配资源  1 是 2否");
                flag = scanner.nextInt();
                while (flag == 1) {
                    os.bankerAlgorithm();
                }
            } else {
                return;
            }
        }
    }


}
/**
 5
 3
 7 5 3
 0 1 0
 3 2 2
 2 0 0
 9 0 2
 3 0 2
 2 2 2
 2 1 1
 4 3 3
 0 0 2
 3 3 2
 */