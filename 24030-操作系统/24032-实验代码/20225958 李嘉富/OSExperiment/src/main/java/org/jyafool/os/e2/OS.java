package org.jyafool.os.e2;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

import static java.lang.Math.ceil;

/**
 * 模拟单道操作系统
 *
 * @author JIA
 * @version 1.0
 * @since 2024/5/28
 */

public class OS {

    private final Scanner scanner;
    /**
     * pcb就绪队列、执行队列、阻塞队列
     */
    private final PCBQueue pendingQueue, runningQueue, blockingQueue;
    /**
     * 分页式管理位图
     */
    private BitMap bitmap;
    /**
     * 请求分页式管理fifo位图
     */
    private BitMap fifoBitMap;
    /**
     * 请求分页式管理lru位图
     */
    private BitMap lruBitMap;
    /**
     * 请求分页式管理opt位图
     */
    private BitMap optBitMap;
    /**
     * 页表长度
     */
    private int pageLength;
    /**
     * 内存块长度
     */
    private int blockLength;
    /**
     * fifo页号队列
     */
    Queue<Integer> fifoPageNumQueue = new ArrayDeque<>();
    /**
     * lru页号队列
     */
    Queue<Integer> lruPageNumQueue = new ArrayDeque<>();

    /**
     * fifo缺页数
     */
    int fifoLackCount = 0;
    /**
     * lru缺页数
     */
    int lruLackCount = 0;
    /**
     * fifo访问总数
     */
    int fifoVisitCount = 0;
    /**
     * lru访问总数
     */
    int lruVisitCount = 0;

    /**
     *
     */
    int[] B = new int[100];
    /**
     * 计算opt算法的原始数组
     */
    int[] orginalOPT = new int[100];
    int t;

    public OS() {
        scanner = new Scanner(System.in);
        pendingQueue = new PCBQueue("就绪队列", 100);
        runningQueue = new PCBQueue("执行队列", 1);
        blockingQueue = new PCBQueue("阻塞队列", 100);

        bitmap = null;
        fifoBitMap = null;
        lruBitMap = null;
        optBitMap = null;

        pageLength = 0;
        blockLength = 0;
    }

    /**
     * 创建进程
     * 输入新进程的名称和大小，根据大小创建对应的页面数组，并为这些页面分配块号。
     * 如果成功创建进程并加入到进程队列，则返回true；否则返回false。
     *
     * @return boolean 表示是否成功创建进程并加入到进程队列。
     */
    private boolean creatProcess() {
        // 获取用户输入的进程名和大小
        System.out.println("请输入新进程的名字与大小(空格隔开)：");
        PCB p = new PCB(scanner.next(), scanner.nextInt());

        // 初始化页面链表
        Page pa = null, pr = null;
        int blockCount = (int) ceil(p.getLength() / (double) BitMap.size); // 计算所需块数
        for (int i = 0; i < blockCount; i++) {
            pa = new Page();
            pa.setNext(pr);
            pr = pa;
        }
        p.setPageTable(pa);

        // 为每个页面分配块号
        for (int j = 0; j < blockCount; j++) {
            pa.setSign(1);
            if (bitmap != null) {
                pa.setBlockNo(bitmap.getMap());
                bitmap.setBusyMap(); // 设置块号并更新位图
            } else {
                System.out.println("bitmap is null");
            }
            pa = pa.getNext();
        }

        // 尝试将新进程加入等待队列，如果成功，进一步尝试将其加入到运行队列
        if (pendingQueue.offer(p)) {
            if (runningQueue.isEmpty()) {
                runningQueue.offer(pendingQueue.remove());
            }
            System.out.println("创建进程成功！");
            return true;
        } else {
            System.out.println("创建进程失败！");
            return false;
        }
    }

    /**
     * 终止进程
     * 首先检查是否有正在执行的程序，如果没有则不执行任何操作并返回false。
     * 如果有，则从运行队列中移除一个进程控制块（PCB），然后遍历该PCB的页面表，
     * 更新对应的页面在内存中的状态。遍历完成后，如果运行队列为空，则尝试从等待队列中取一个进程加入运行队列。
     *
     * @return boolean 如果成功终止了进程返回true，如果运行队列为空返回false。
     */
    private boolean terminateProcess() {
        // 检查运行队列是否为空，为空则无法终止进程
        if (runningQueue.isEmpty()) {
            System.out.println("无正在执行的程序，无法终止！");
            return false;
        }

        // 从运行队列中取出一个PCB
        PCB pcb = runningQueue.remove();
        // 获取该PCB对应的页面表
        Page pageTable = pcb.getPageTable();

        // 遍历页面表，更新每个页面在内存中的状态
        while (pageTable != null) {
            int i = (int) ceil(pageTable.getBlockNo() / 8);
            int j = (int) (pageTable.getBlockNo() % 8);
            bitmap.setMap(i, j, 0);  // 更新位图表示页面状态
            pageTable = pageTable.getNext();  // 移动到下一个页面表项
        }

        // 如果运行队列为空，尝试从等待队列取一个进程加入运行队列
        if (runningQueue.isEmpty()) {
            runningQueue.offer(pendingQueue.remove());
        }

        // 输出信息，表示已终止一个进程
        System.out.println("已终止正在运行程序！");
        return true;
    }


    /**
     * 根据逻辑地址查询对应的物理地址
     * 该方法首先检查运行队列是否为空，如果为空，则输出"执行态为空"并返回0。
     * 如果运行队列不为空，方法会要求用户输入逻辑地址，并根据这个逻辑地址计算出对应的页号和页内偏移。
     * 然后，它会从运行队列的第一个元素的页表中查找对应的逻辑地址，如果找到，则输出逻辑地址和对应的物理地址。
     * 如果地址越界，则输出"越界！"并返回0。
     * 如果在整个页表中未找到对应的逻辑地址，则返回0。
     *
     * @return int 返回1表示找到了对应的逻辑地址并输出了物理地址，返回0表示未找到或存在错误（如执行态为空、地址越界）。
     */
    int findPhysicalAddrByLogicalAddr() {
        // 检查运行队列是否为空
        if (runningQueue.isEmpty()) {
            System.out.println("执行态为空");
            return 0;
        }

        // 输出提示信息并接收用户输入的逻辑地址
        System.out.println("逻辑地址: ");
        int address = scanner.nextInt();

        // 计算页号和页内偏移
        int pageNum = (address / BitMap.size);
        int pageAddress = (address % BitMap.size);

        // 从运行队列获取第一个元素的页表
        Page p = runningQueue.getFirst().getPageTable();

        // 检查地址是否越界
        if (address >= runningQueue.getFirst().getLength()) {
            System.out.println("越界！");
            return 0;
        }

        // 遍历页表查找对应的逻辑地址
        int i = 0;
        while (p.getNext() != null) {
            if (pageNum == i) {
                // 找到对应逻辑地址，输出结果
                System.out.println("逻辑地址为：" + address);
                System.out.println("物理地址：" + (pageAddress + p.getBlockNo() * BitMap.size));
                return 1;
            }
            i++;
            p = p.getNext();
        }
        // 未找到对应的逻辑地址
        return 0;
    }

    /**
     * 创建并初始化一个页面扩展表。
     *
     * @return 返回初始化后的页面扩展表的头结点。
     */
    PageTable creatPageExpend() {
        // 初始化两个临时变量，用于构建链表
        PageTable tmp = null, temp = null;
        // 循环创建pageLength个页面表项，并将它们链接起来
        for (int i = 0; i < pageLength; i++) {
            tmp = new PageTable();
            tmp.setNext(temp);
            temp = tmp;
        }

        PageTable p = null;
        p = tmp;
        // 遍历链表，为每个页面表项设置初始值
        for (int j = 0; j < pageLength; j++) {
            tmp.setNum(j);       // 设置页面编号
            tmp.setBlockNo(0);   // 设置块号为0，表示未分配
            tmp.setSign(0);      // 设置标志位为0，表示未修改
            tmp = tmp.getNext(); // 移动到下一个页面表项
        }
        return p; // 返回链表的头结点
    }

    /**
     * 显示页表的内容
     *
     * @param p 要显示的页表
     */
    void showPageTable(PageTable p) {
        PageTable pa = p;
        System.out.println("--------------------");
        System.out.println("页表");
        System.out.println("页号   块号   状态位");
        // 遍历页表，打印每一页的信息
        while (pa != null) {
            System.out.print(" " + pa.getNum() + "   "); // 打印页号
            System.out.print(pa.getBlockNo() + "   "); // 打印块号
            System.out.println(pa.getSign()); // 打印状态位
            pa = pa.getNext(); // 移动到下一个页表项
        }

        System.out.println("--------------------");
    }

    /**
     * 展示 FIFO 或 LRU 缓存策略下的页面映射关系，并打印出相应的逻辑地址到物理地址的转换。
     *
     * @param pa      当前的页面表
     * @param p       指向当前被访问页面的指针
     * @param address 要访问的逻辑地址
     */
    void showAddrMapping(PageTable pa, PageTable p, int address) {
        int pageNum = (int) (address / BitMap.size);
        int pageAddress = (int) (address % BitMap.size);

        System.out.print("逻辑地址：" + address + "-->");
        System.out.println("物理地址：" + (p.getBlockNo() * BitMap.size + pageAddress));
        showPageTable(pa);
        fifoBitMap.showMap();
        System.out.println();
        System.out.println("页号：" + pageNum + "  " + "页内偏移地址：" + pageAddress);
    }

    /**
     * 实现FIFO（先进先出）页面替换算法。
     *
     * @param fifoTable 当前页表
     * @param p         需要访问的页面
     * @param address   页面的地址
     */
    void FIFO(PageTable fifoTable, PageTable p, int address) {
        PageTable pr = fifoTable; // 指向当前页表的引用
        fifoVisitCount++; // 访问总数加1
        System.out.println("----------------------------FIFO-------------------------");
        // 1、如果访问的页面在内存中存在
        if (p.getSign() == 1) {
            showAddrMapping(fifoTable, p, address);
            System.out.print("\t状态：命中" + "\t缺页数：" + fifoLackCount); // 输出命中状态和当前缺页数
        }

        // 2、如果访问的页面不在内存中，且内存中有空闲页
        if (p.getSign() == 0 && fifoBitMap.getMap() >= 0) {
            // 将页面放入内存
            p.setSign(1);
            p.setBlockNo(fifoBitMap.getMap());

            fifoBitMap.setBusyMap(); // 标记该页面所在的物理页框为忙
            System.out.println(); // 换行
            fifoPageNumQueue.add(p.getNum());
            fifoLackCount++;
            showAddrMapping(fifoTable, p, address);
            System.out.print("\t状态：缺页" + "\t缺页数：" + fifoLackCount);
        }

        // 3、如果访问的页面不在内存中，且内存中没有空闲页
        if (p.getSign() == 0 && fifoBitMap.getMap() < 0) {
            int num = fifoPageNumQueue.poll();
            // 在页表中找到该页面并将其移除
            while (pr.getNum() != num && pr.getNext() != null) {
                pr = pr.getNext();
            }
            // 将需要访问的页面放入该物理页框
            p.setBlockNo(pr.getBlockNo());
            pr.setBlockNo(0); // 清除原页面的物理页框号
            pr.setSign(0); // 将原页面标记为未使用

            // 将新页面加入队列尾部和内存
            p.setSign(1);
            fifoPageNumQueue.add(p.getNum());
            fifoLackCount++;
            showAddrMapping(fifoTable, p, address);
            System.out.print("\t状态：置换" + "\t缺页数：" + fifoLackCount);
        }

        // 输出缺页率
        System.out.println("\t缺页率：" + fifoLackCount * 100 / fifoVisitCount + "%");
        System.out.println("队列（底->顶）：");
        // 输出队列内容
        for (int i = 0; i < fifoPageNumQueue.size(); i++) {
            int j = fifoPageNumQueue.peek(); // 取队首元素
            fifoPageNumQueue.poll(); // 移除队首元素
            System.out.print(j + " "); // 输出页面号
            fifoPageNumQueue.add(j); // 将页面号重新加入队列尾部
        }
        System.out.println();
        System.out.println("----------------------------FIFO-------------------------");
    }

    /**
     * 执行LRU（最近最久未使用）页面替换算法。
     *
     * @param pa      当前页表
     * @param p       需要访问的页面
     * @param address 页面的地址
     */
    void LRU(PageTable pa, PageTable p, int address) {
        PageTable pr = pa;
        lruVisitCount++;
        System.out.println("----------------------------LRU-------------------------");

        // 1.如果页面在内存中
        if (p.getSign() == 1) {
            lruPageNumQueue.add(lruPageNumQueue.poll()); // 将队首元素重新放到队尾
            showAddrMapping(pa, p, address); // 显示FIFO和LRU的状态
            System.out.print("\t状态：命中" + "\t缺页数：" + lruLackCount);
        }

        // 2.如果页面不在内存中，且内存中有空闲页
        if (p.getSign() == 0 && lruBitMap.getMap() >= 0) {
            p.setSign(1);
            p.setBlockNo(lruBitMap.getMap()); // 设置页面的块号
            lruBitMap.setBusyMap(); // 将空闲页标记为忙碌
            lruPageNumQueue.add(p.getNum()); // 将页面号加入队列
            lruLackCount++;
            showAddrMapping(pa, p, address); // 显示FIFO和LRU的状态
            System.out.print("\t状态：缺页" + "\t缺页数：" + lruLackCount);
        }

        // 3.如果页面不在内存中，且内存中没有空闲页
        if (p.getSign() == 0 && lruBitMap.getMap() < 0) {
            int num = lruPageNumQueue.poll();
            // 查找并替换掉最久未使用的页面
            while (pr.getNum() != num && pr.getNext() != null) {
                pr = pr.getNext();
            }
            p.setBlockNo(pr.getBlockNo()); // 替换页面的块号
            pr.setBlockNo(0); // 将被替换页面的块号置为0
            pr.setSign(0); // 将被替换页面的标志位置为0

            p.setSign(1); // 标记新页面为在内存中
            lruPageNumQueue.add(p.getNum()); // 将新页面号加入队列
            lruLackCount++;
            showAddrMapping(pa, p, address); // 显示FIFO和LRU的状态
            System.out.print("\t状态：置换" + "\t缺页数：" + lruLackCount);
        }

        // 输出缺页率
        System.out.println("\t缺页率：" + lruLackCount * 100 / lruVisitCount + "%");
        System.out.println("队列（底->顶）：");
        // 循环打印队列元素，实现队列的旋转显示
        for (int i = 0; i < lruPageNumQueue.size(); i++) {
            int j = lruPageNumQueue.peek(); // 取出队首元素
            lruPageNumQueue.poll(); // 删除队首元素
            System.out.print(j + " "); // 打印元素
            lruPageNumQueue.add(j); // 将元素重新加入队尾
        }
        System.out.println();
        System.out.println("----------------------------LRU-------------------------");
    }

    /**
     * 实现 FIFO 和 LRU 页面替换算法的函数。
     *
     * @param fifoTable 页表的头指针，用于 FIFO 算法。
     * @param lruTable  页表的头指针，用于 LRU 算法。
     * @return 如果执行了写操作则返回 1，否则返回 0。
     */
    int applyFIFOAndLRUPageReplacement(PageTable fifoTable, PageTable lruTable) {
        PageTable p = fifoTable, tmp = lruTable;
        int address, command;
        System.out.println("逻辑地址：");
        address = scanner.nextInt();
        System.out.print("是否为写指令(0：否 1：是)：");
        command = scanner.nextInt();
        if (command == 0) {
            return 0;
        }

        // 判断是否越界
        if (address >= pageLength * BitMap.size) {
            System.out.println("越界！");
            return 0;
        }

        // 计算页号
        int pageNum = (address / BitMap.size);
        orginalOPT[t++] = pageNum;  // 将页号记录到数组中

        // 在页表中找到对应的页
        while (p.getNum() != pageNum) {
            p = p.getNext();
        }
        // 执行 FIFO 页面替换算法
        FIFO(fifoTable, p, address);

        // 在页表中找到对应的页，用于 LRU 算法
        while (tmp.getNum() != pageNum) {
            tmp = tmp.getNext();
        }
        // 执行 LRU 页面替换算法
        LRU(lruTable, tmp, address);

        return 1;
    }

    /**
     * 执行OPT（Optimal Page Replacement）算法。
     * OPT算法是一种理论上的最优页面替换算法，它假设处理器能够预知未来访问的页面序列。
     * 该方法不接受参数，也没有返回值。
     * 主要步骤：
     * 1. 遍历页面访问序列；
     * 2. 对于每个页面访问，判断是否命中，未命中时根据算法进行页面置换；
     * 3. 输出每次访问的结果（命中、缺页或置换）。
     */
    void OPT() {
        int index = 0;

        int n = 0;
        for (int i = 0; i < t; i++) {
            // 判断是否命中
            if (OPTFind(i) != -1) {
                System.out.print("命中\t");
                showB();
            } else {
                if (n < blockLength) {
                    System.out.print("缺页\t");
                    B[n++] = orginalOPT[i];

                } else {
                    System.out.print("置换\t");
                    index = OPT_H(i + 1);
                    B[index] = orginalOPT[i];
                }
                showB();
            }
        }
    }

    /**
     * 计算给定条件下的一种优化策略。
     *
     * @param i 当前考虑的索引位置
     * @return 返回一个整数，表示根据算法计算出的最优选择。
     */
    int OPT_H(int i) {
        // 初始化变量
        int index = -1, count = 0;
        int[] f = new int[10];
        for (int k = 0; k < blockLength; k++) {
            f[k] = 0; // 初始化f数组
        }

        int num = t - i; // 计算剩余考虑范围的长度

        // 遍历当前到结束的位置
        for (int j = i; j < t; j++) {
            // 遍历每个位置检查字符是否匹配
            for (int k = 0; k < blockLength; k++) {
                if (B[k] == orginalOPT[j]) { // 找到匹配字符
                    index = k; // 更新匹配的索引
                    if (f[index] != 1) {
                        count++; // 增加计数器
                    }
                    f[index] = 1; // 标记已匹配
                }
            }
        }

        // 检查是否满足条件
        if (count < blockLength) {
            // 寻找未匹配的索引
            for (index = 0; index < blockLength; index++) {
                if (f[index] == 0) {
                    return index; // 返回第一个未匹配的索引
                }
            }
        }
        return index; // 返回最后的index值
    }


    /**
     * 如果找到匹配元素，则返回该元素在数组中的索引；否则返回-1。
     *
     * @param i T数组中要查找的元素的索引
     * @return 匹配元素的索引，如果未找到则返回-1
     */
    int OPTFind(int i) {
        // 遍历B数组，查找与T[i]相匹配的元素
        for (int j = 0; j < blockLength; j++) {
            if (B[j] == orginalOPT[i]) {
                // 找到匹配元素，返回其索引
                return j;
            }
        }
        // 未找到匹配元素，返回-1
        return -1;
    }


    /**
     * 展示数组
     */
    void showB() {
        System.out.print("队列（底->顶）：");
        for (int i = 0; i < blockLength; i++) {
            System.out.print(B[i] + " ");
        }
        System.out.println();
    }

    /**
     * 显示进程的页表信息
     *
     * @param p PCB头指针
     */
    void showPage(PCB p) {
        if (p == null) {
            System.out.println("无执行进程！");
        }
        while (p != null) {
            Page pa = p.getPageTable();
            int i = 0;
            System.out.println("页表");
            System.out.println("页号   块号   状态位");
            while (pa != null) {
                System.out.print(" " + i + "\t");
                System.out.print(pa.getBlockNo() + "\t");
                System.out.println(pa.getSign());
                i++;
                pa = pa.getNext();
            }
            p = p.getNext();
        }
    }


    /**
     * 菜单
     */
    static void menu() {
        System.out.println("------------------------------------------------------");
        System.out.println("\t\t1.  创建进程         ");
        System.out.println("\t\t2.  撤销进程         ");
        System.out.println("\t\t3.  显示进程状态     ");
        System.out.println("\t\t4.  地址变换         ");
        System.out.println("\t\t5.  显示当前进程页表 ");
        System.out.println("\t\t6.  显示位示图       ");
        System.out.println("\t\t7.  退出             ");
        System.out.println("\t\t0.  帮助文档         ");
        System.out.println("-------------------------------------------------------");
    }

    public static void main(String[] args) {
        OS os = new OS();

        Scanner in = new Scanner(System.in);
        while (true) {
            int w;
            System.out.println("\t--------------");
            System.out.println("\t1.分页式存储管理");
            System.out.println("\t2.请求分页式存储管理");
            System.out.println("\t--------------");
            System.out.println("请选择：");
            w = in.nextInt();
            if (w == 1) {
                menu();
                // 位视图
                os.bitmap = new BitMap();
                while (true) {
                    System.out.println("\n请选择：");
                    int choice = in.nextInt();
                    switch (choice) {
                        case 1:
                            os.creatProcess();
                            break;
                        case 2:
                            os.terminateProcess();
                            break;
                        case 3:
                            os.pendingQueue.show();
                            os.runningQueue.show();
                            os.blockingQueue.show();
                            break;
                        case 6:
                            os.bitmap.showMap();
                            break;
                        case 5:
                            if (!os.runningQueue.isEmpty()) {
                                os.showPage(os.runningQueue.getFirst());
                            } else {
                                System.out.println("执行队列为空！");
                            }
                            break;
                        case 4:
                            os.findPhysicalAddrByLogicalAddr();
                            break;
                        case 0:
                            menu();
                            break;
                        case 7:
                            break;
                        default:
                            System.out.println("无效的选择");
                            break;
                    }
                }
            }

            if (w == 2) {
                System.out.println("页长：");
                os.pageLength = in.nextInt();
                System.out.println("块长：");
                os.blockLength = in.nextInt();
                PageTable fifoTable = os.creatPageExpend();
                PageTable lruTable = os.creatPageExpend();

                os.fifoBitMap = new BitMap(os.blockLength);
                os.lruBitMap = new BitMap(os.blockLength);
                os.optBitMap = new BitMap(os.blockLength);
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        os.lruBitMap.setMap(i, j, os.fifoBitMap.getMap(i, j));
                        os.optBitMap.setMap(i, j, os.fifoBitMap.getMap(i, j));
                    }
                }

                os.fifoBitMap.showMap();// 输出位视图
                System.out.println();
                int y = 1;
                while (true) {
                    if (y == 1) {
                        os.applyFIFOAndLRUPageReplacement(fifoTable, lruTable);
                    } else {
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                os.fifoBitMap.setMap(i, j, os.optBitMap.getMap(i, j));
                            }
                        }
                        os.fifoBitMap.showMap();

                        System.out.print("队列（底->顶）：");
                        for (int j = 0; j < os.t; j++) {
                            System.out.print(os.orginalOPT[j] + " ");
                        }
                        System.out.println();
                        os.OPT();

                        break;
                    }
                    System.out.println("\n0：结束\t1：继续  请选择：");
                    y = in.nextInt();
                }
            }
        }
    }

}
