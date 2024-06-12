package org.alg.A;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 实验一：众数重数
 */

public class Two {
    static int mode;                // 众数
    static int modeCount = 0;       // 众数个数

    public static void solve( int[] arr,int n, int left, int right) {
        // 递归出口
        if (left > right) {
            return;
        }
        // 划分数组并找到左侧第一个不等于a[mid]的位置
        int mid = (left + right) >>> 1;
        int i = mid, j = mid;
        // 找到左侧第一个不等于a[mid]的位置
        while (i >= 0 && arr[i] == arr[mid]) {
            i--;
        }
        // 找到右侧第一个不等于a[mid]的位置
        while (j <= n - 1 && arr[j] == arr[mid]) {
            j++;
        }
        // 处理众数在中间的情况
        // j-i-1为当前元素的重数
        if (j - i - 1 > modeCount) {
            mode = arr[mid];
            modeCount = j - i - 1;
        }
        // 分治策略：众数可能在左侧
        if (i - left + 1 > modeCount) {
            solve(arr,n, left, i);
        }
        // 分治策略：众数可能在右侧
        if (right - j + 1 > modeCount) {
            solve(arr,n, j, right);
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入元素个数：");
        int n = scanner.nextInt();
        int[] arr = new int[n];
        System.out.println("请输入元素值：");
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }
        Arrays.sort(arr);
        solve(arr,n, 0, n - 1);
        System.out.println("众数：" + mode);
        System.out.println("重数：" + modeCount);
        scanner.close();
    }
}
