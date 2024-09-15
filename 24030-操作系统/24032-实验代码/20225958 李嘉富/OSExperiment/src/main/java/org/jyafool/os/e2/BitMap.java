package org.jyafool.os.e2;

import java.util.Random;

/**
 * 位图
 *
 * @author JIA
 * @version 1.0
 * @since 2024/5/27
 */

public class BitMap {
    /**
     * 块大小
     */
    public static final int size = 1024;
    /**
     * 块个数
     */
    public static final int MEM_SIZE = 64;

    /**
     * 位视图：0表示空闲，1表示已分配
     */
    public int[][] map = new int[8][8];

    /**
     * 随机创建位图
     */
    public BitMap() {
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                map[i][j] = random.nextInt(10) % 2;
            }
        }
    }

    /**
     * @param n 这个参数指定了在初始化BitMap时，要随机置为0的位的数量。
     */
    public BitMap(int n) {
        int p, q, m;
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                map[i][j] = 1;
            }
        }
        // 随机选择n个位，并将它们设置为0
        for (int k = 0; k < n; k++) {
            m = random.nextInt(1000) % 63;
            p = m / 8;
            q = m % 8;
            map[p][q] = 0;
        }
    }

    /**
     * 设置繁忙位图
     *
     * @return 返回1表示成功设置了一个繁忙位，返回0表示所有位都已设置为繁忙。
     */
    int setBusyMap() {
        // 遍历map数组的每个元素
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // 如果找到未使用的元素，则标记为已使用并返回1
                if (map[i][j] == 0) {
                    map[i][j] = 1;
                    return 1;
                }
            }
        }
        // 如果所有元素都已使用，则返回0
        return 0;
    }

    /**
     * 设置指定位置的map值为x
     *
     * @param i 纵坐标
     * @param j 横坐标
     * @param x 要设置的值
     * @return 总是返回0，目前未使用实际返回值
     */
    int setMap(int i, int j,int x) {
        map[i][j] = x;
        return 0;
    }

    /**
     * 获取 bitmap 中第一个为 0 的像素的索引,并返回其在二维数组中的位置。
     *
     * @return 如果找到值为 0 的元素，返回该元素在二维数组中的索引（行*8 + 列）；否则返回 -1。
     */
    int getMap() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (map[i][j] == 0) {
                    return 8 * i + j;
                }
            }
        }
        return -1;
    }


    /**
     * 获取位图某个位置的值
     *
     * @param i 行
     * @param j 列
     * @return 对应的值
     */
    int getMap(int i, int j) {
        return map[i][j];
    }

    /**
     * 展示位图
     */
    public void showMap() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }
}
