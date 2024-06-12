package org.alg.C;

import java.util.Scanner;

/**
 * 实验三：活动安排
 */

public class Two {

    // 活动开始时间
    private static int[] s = new int[10010];

    // 活动结束时间
    private static int[] f = new int[10010];

    // 活动序列号
    private static int[] num = new int[10010];

    // 是否选择该活动
    private static boolean[] flag = new boolean[10010];

    public static void sort(int n) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (f[j] > f[j + 1]) {
                    swap(s, j, j + 1);
                    swap(f, j, j + 1);
                    swap(num, j, j + 1);
                }

            }
        }

        for (int i = 0; i < n; i++) {
            System.out.println(num[i] + ","+ s[i]+","+ f[i]);
        }

    }

    public static void activity(int n) {
        int j = 0;
        // 第一个肯定选
        flag[0] = true;
        for (int i = 1; i < n; i++) {
            // 下一个活动的起始时间 > 当前活动的结束时间，则选择此活动
            if (s[i] >= f[j]) {
                flag[i] = true;
                j = i;
            } else {
                flag[i] = false;
            }
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入活动个数：");
        int n = scanner.nextInt();

        System.out.println("请输入活动开始时间和结束时间：");
        for (int i = 0; i < n; i++) {
            s[i] = scanner.nextInt();
            f[i] = scanner.nextInt();
            num[i] = i;
        }

        sort(n);
        activity(n);

        for (int i = 0; i < n; i++) {
            if (flag[i]) {
                System.out.println("能举办的活动序号：" + num[i] + "   " +
                        "活动开始时间：" + s[i] + "   " +
                        "活动结束时间：" + f[i]);
            }
        }

        scanner.close();
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
