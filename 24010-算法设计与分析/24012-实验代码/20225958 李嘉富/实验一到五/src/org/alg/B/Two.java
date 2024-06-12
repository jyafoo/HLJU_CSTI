package org.alg.B;

import java.util.Scanner;

/**
 * 实验二：最长公共子序列
 */

public class Two {
    public static int longestCommonSubsequence(String text1, String text2) {
        int[][] dp = new int[text1.length() + 1][text2.length() + 1];

        char[] t1 = text1.toCharArray();
        char[] t2 = text2.toCharArray();

        for (int i = 1; i <= t1.length; i++) {
            char c1 = t1[i - 1];
            for (int j = 1; j <= t2.length; j++) {
                char c2 = t2[j - 1];
                if (c1 == c2) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[t1.length][t2.length];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入字符序列X:");
        String sequenceX = scanner.nextLine();

        System.out.println("请输入字符序列Y:");
        String sequenceY = scanner.nextLine();

        int lcsLength = longestCommonSubsequence(sequenceX, sequenceY);
        System.out.printf("字符序列X和Y的最长公共子序列长度为: %d\n", lcsLength);

        // 如果需要输出具体的最长公共子序列，可以进一步扩展代码实现
    }
}
