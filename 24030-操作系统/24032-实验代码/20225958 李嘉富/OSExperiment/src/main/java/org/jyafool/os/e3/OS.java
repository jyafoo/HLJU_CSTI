package org.jyafool.os.e3;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

/**
 * 模拟单道处理操作系统
 *
 * @Author jyafool
 * @Version 1.0
 * @Since 2024/6/13
 */
public class OS {
    private Scanner scanner;

    /**
     * 代表某磁盘块为空块的FAT表项
     */
    public static final int EMPTY_BLOCK = 0x0000;
    /**
     * 代表某磁盘块为文件或目录的最后一块的FAT表项
     */
    public static final int LAST_BLOCK = 0xFFFF;

    /**
     * 块大小
     */
    public static final int BLOCK_SIZE = 1024;

    /**
     * 运行标志
     */
    private boolean isRun = true;

    /**
     * FAT大小
     */
    public static final int FAT_SIZE = 8;

    /**
     * 文件分配表
     */
    private int[] FAT = new int[FAT_SIZE];

    /**
     * FCB栈，模拟目录的进入和退出，栈顶元素代表当前工作目录。
     */
    private final Stack<FCB> stack = new Stack<>();

    public OS() {
        this.scanner = new Scanner(System.in);
        this.stack.push(FCB.mainFCB);
        FAT[0] = LAST_BLOCK;
    }

    /**
     * 启动方法
     */
    public void start() {
        System.out.println("输入 h 查看帮助");
        while (isRun) {
            System.out.println("---------------------------------------");
            for (FCB fcb : stack) {
                System.out.print(fcb.getName() + "\\");
            }
            System.out.print(">>");
            String command = scanner.next().toUpperCase();
            switch (command) {
                case "Q":
                    isRun = false;
                    break;
                case "H":
                    help();
                    break;
                case "MD":
                    md();
                    break;
                case "CD":
                    cd();
                    break;
                case "RD":
                    rd();
                    break;
                case "MK":
                    mk();
                    break;
                case "DEL":
                    del();
                    break;
                case "DIR":
                    dir();
                    break;
                case "TREE":
                    showTree();
                    break;
                case "FAT":
                    showFat();
                    break;
                default:
                    System.out.println("无效命令，请重新输入");
                    break;
            }
        }
    }

    /**
     * 检查文件分配表（FAT）以找到第一个空闲的块。
     *
     * @return 如果找到空闲块，则返回第一个空闲块的索引；如果所有块都已使用，则返回0。
     */
    public int checkFAT() {
        int flag = 0;
        int firstBlock = 0;
        for (int i = 0; i < FAT_SIZE; i++) {
            if (FAT[i] == EMPTY_BLOCK) {
                firstBlock = i;
                flag = 1;
                break;
            }
        }
        if (flag == 0) {
            System.out.println("无空闲空间！");
            return 0;
        }
        return firstBlock;
    }

    /**
     * 在当前目录下创建新目录
     */
    private void md() {
        int firstBlock = checkFAT();
        if (firstBlock == 0) {
            System.out.println("创建失败！");
            return;
        }
        stack.peek().addCatalogue(scanner.next(), firstBlock);
        FAT[firstBlock] = LAST_BLOCK;
        System.out.println("创建成功");
    }

    /**
     * 改变当前工作目录
     */
    private void cd() {
        String s = scanner.next();
        if (s.equals("..") || s.equals(".")) {
            if (s.equals("..") && stack.size() > 1) {
                stack.pop();
            }
        } else {
            FCB i = stack.peek().findCatalogue(s);
            if (i != null) {
                stack.push(i);
            } else {
                System.out.println(stack.peek().getName() + "目录下未找到" + s + "目录");
            }
        }
    }

    /**
     * 删除指定的目录
     *
     * 从栈顶的分区目录中找到这个目录，
     *      如果找到，则将该目录在FAT（文件分配表）中的入口标记为空，表示该目录已被删除；
     *      如果找不到，则提示用户该目录不存在。
     *
     * @see FAT FAT（文件分配表）用于管理磁盘上的空闲块和已占用块。
     * @see EMPTY_BLOCK 表示空闲块的标志，在FAT中使用。
     */
    private void rd() {
        String s = scanner.next();
        int firstBlock = stack.peek().removeCatalogue(s);
        if (firstBlock == -1) {
            System.out.println(stack.peek().getName() + "目录下未找到空的" + s + "目录");
        } else {
            FAT[firstBlock] = EMPTY_BLOCK;
            System.out.println("删除成功");
        }
    }

    /**
     * 在当前目录下创建新文件
     *
     * 用户输入中获取文件名和大小，然后在文件分配表（FAT）中寻找足够的连续空闲块来存储该文件。
     *      如果找到足够的空间，它将为文件分配这些块，并在FAT中进行相应的链接，最后将文件信息添加到栈顶的目录项中。
     *      如果没有足够的空间，则提示用户空间不足。
     */
    private void mk() {
        System.out.println("输入名称和大小：");
        String name = scanner.next();
        int size = scanner.nextInt();
        // 计算所需的块数，确保至少为1
        int count = size / BLOCK_SIZE + 1;
        int c = 0;
        // 存储找到的空闲块的索引
        int[] a = new int[FAT_SIZE];

        boolean flag = false;
        int firstBlock = -1;
        for (int i = 0; i < FAT_SIZE; i++) {
            if (FAT[i] == EMPTY_BLOCK) {
                if (firstBlock == -1) {
                    firstBlock = i;
                }
                a[c] = i;
                c++;
                if (c == count) {
                    flag = true;
                    break;
                }
            }
        }
        if (!flag) {
            System.out.println("空间不足。");
            return;
        }
        if (count > 1) {
            // 占用一块以上
            for (int i = 0; i < count - 1; i++) {
                // 除了最后一块都赋值加1
                FAT[a[i]] = a[i + 1]; // 标记fat
            }
            // 最后一块赋值FFFF
            FAT[a[count - 1]] = LAST_BLOCK;
        } else {
            FAT[firstBlock] = LAST_BLOCK;
        }
        stack.peek().addFile(name, size, firstBlock);

        System.out.println("创建成功");
    }


    /**
     * 删除文件夹中的文件。
     *      如果文件不存在，则未找到文件。
     *      如果文件存在，遍历文件的FAT表，将所有属于该文件的块标记为空闲块，从而从文件系统中删除该文件。
     */
    private void del() {
        String s = scanner.next();
        int removedFile = stack.peek().removeFile(s);
        if (removedFile == -1) {
            System.out.println(stack.peek().getName() + "目录下未找到" + s + "文件");
        } else {
            int i = removedFile;
            int temp = 0;
            while (FAT[i] != LAST_BLOCK) {
                temp = FAT[i];
                FAT[i] = EMPTY_BLOCK;
                i = temp;
            }
            FAT[i] = EMPTY_BLOCK;
            System.out.println("删除成功");
        }
    }

    /**
     * 查看当前目录下的文件信息
     */
    private void dir() {
        stack.peek().show();
    }

    /**
     * 从根目录开始展示全部信息
     */
    private void showTree() {
        FCB.mainFCB.showFCB(FCB.mainFCB);
    }

    /**
     * 输出FAT表
     */
    private void showFat() {
        System.out.println
                ("------------------------------FAT-----------------------------");
        for (int i = 0; i < FAT_SIZE; i++) {
            System.out.printf("%04x\t", FAT[i]);

        }
        // System.out.println();
        System.out.println
                ("--------------------------------------------------------------");
    }

    /**
     * 帮助菜单
     */
    private void help() {
        String[][] helpDoc = {
                {"创建空目录：MD"},
                {"切换当前目录：CD "},
                {"删除空目录：RD"},
                {"创建文件：MK"},
                {"删除文件：DEL"},
                {"列出目录信息：DIR"},
                {"列出全部信息：Tree"},
                {"显示fat表：fat"},
                {"退出：Q"}
        };
        for (String[] s : helpDoc) {
            System.out.println(Arrays.toString(s));
        }
    }

    public static void main(String[] args) {
        OS os = new OS();
        os.start();
    }
}
