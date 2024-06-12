package org.alg.B;

import java.util.Scanner;



/**
 * 实验二：最大子段和
 */

public class One {

    public static int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length];

        dp[0] = nums[0];
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            max = Math.max(max,dp[i]);
        }

        return max;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入元素个数：");
        int n = scanner.nextInt();

        System.out.println("请输入元素值：");
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        int res = maxSubArray(arr);

        System.out.println("最大子段和：" + res);

        scanner.close();
    }
}
