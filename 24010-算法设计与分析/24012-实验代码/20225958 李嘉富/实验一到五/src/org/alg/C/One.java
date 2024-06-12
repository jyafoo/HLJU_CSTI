package org.alg.C;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 实验三：贪心背包
 */

public class One {
    /**
     *
     * @param weight 物品重量
     * @param value 物品价值
     * @param capacity 背包容量
     * @return
     */
    public static double greedy(double[] weight,double[] value,int capacity){
        double maxValue = 0;
        double curWeight = 0;

        for(int i = 0;i < value.length;i++){
            if (curWeight + weight[i] <= capacity) {
                curWeight += weight[i];
                maxValue += value[i];
            } else {
                double residual = capacity - curWeight;
                if (weight[i] != 0) {
                    double p = residual / weight[i];
                    maxValue += value[i] * p;
                }
                break; // 当背包装满时，结束循环
            }
        }

        return maxValue;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 输入物品的数量
        System.out.print("请输入物品的数量: ");
        int n = scanner.nextInt();

        // 输入物品的重量和价值
        double[] weights = new double[n];
        double[] values = new double[n];
        System.out.println("请依次输入每个物品的重量和价值：");
        for (int i = 0; i < n; i++) {
            System.out.print("物品 " + (i + 1) + " 的重量: ");
            weights[i] = scanner.nextDouble();
            System.out.print("物品 " + (i + 1) + " 的价值: ");
            values[i] = scanner.nextDouble();
        }

        // 输入背包的容量
        System.out.print("请输入背包的容量: ");
        int capacity = scanner.nextInt();

        // 调用贪心算法求解
        double totalValue = greedy(weights, values, capacity);

        // 输出结果
        System.out.println("装入背包中的物品信息：");
        System.out.println("---------------------------------");
        System.out.println("物品\t重量\t价值\t装入比例");
        System.out.println("---------------------------------");
        double cur = 0;
        for (int i = 0; i < n; i++) {
            double proportion = cur + weights[i] <= capacity ? weights[i] / capacity : 0;
            cur += weights[i];
            System.out.printf("%d\t%.2f\t%.2f\t%.2f\n", i + 1, weights[i], values[i], proportion);
        }
        System.out.println("---------------------------------");
        System.out.println("装入背包中物品的总价值为: " + totalValue);
    }

}
