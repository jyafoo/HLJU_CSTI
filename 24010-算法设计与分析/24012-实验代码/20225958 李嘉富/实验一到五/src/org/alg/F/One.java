package org.alg.F;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 实验五：分支界限
 */

public class One {
    /**
     * 边
     */
    static class Edge {
        int destination;        // 目的地
        int weight;     // 权重

        public Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        // 邻接矩阵
        int[][] graph = {
                {0, 30, 6, 4},
                {30, 0, 5, 10},
                {6, 5, 0, 20},
                {4, 10, 20, 0}
        };
        int startNode = 2;

        shortestPath(graph, startNode);
    }

    /**
     * 计算给定有向图中从指定起点到所有顶点的最短路径。
     *
     * @param graph     二维数组表示的有向图，graph[i][j]的值代表从顶点i到顶点j的边的权重，若不存在边则为0。
     * @param startNode 起点的索引。
     */
    public static void shortestPath(int[][] graph, int startNode) {
        int n = graph.length;
        int[] dist = new int[n]; // 用于存储起点到各个顶点的最短距离
        int[] pre = new int[n]; // 用于记录每个顶点的前置顶点，即最短路径上的前一个顶点
        Arrays.fill(dist, Integer.MAX_VALUE); // 初始化dist数组
        dist[startNode] = 0; // 起点到自身的距离为0

        PriorityQueue<Edge> pq = new PriorityQueue<>((a,b) -> a.weight - b.weight);
        pq.offer(new Edge(startNode, 0));

        while (!pq.isEmpty()) {
            Edge curr = pq.poll();
            int currNode = curr.destination;
            int currWeight = curr.weight;

            // 如果当前顶点经过已有路径到达的距离大于已知的最短距离，则忽略
            if (currWeight > dist[currNode]) {
                continue;
            }

            // 遍历当前顶点的所有邻接顶点
            for (int i = 0; i < n; i++) {
                // 如果存在边从当前顶点到邻接顶点
                if (graph[currNode][i] != 0) {

                    int newWeight = currWeight + graph[currNode][i];
                    // 如果新距离小于已知的最短距离，则更新最短距离和前置顶点，并将邻接顶点加入优先队列
                    if (newWeight < dist[i]) {
                        dist[i] = newWeight;
                        pre[i] = currNode;
                        pq.offer(new Edge(i, newWeight));
                    }
                }
            }
        }

        // 打印所有顶点到起点的最短路径长度和路径
        System.out.println("顶点到起点" + startNode + "的最短路径长度为:");
        for (int i = 0; i < n; i++) {
            System.out.println("顶点" + i + ": " + dist[i]);
            System.out.print("路径：");
            printPath(pre, i, startNode);
            System.out.println();
        }
    }

    /**
     * 打印路径
     *
     * @param pre
     * @param node
     * @param startNode
     */
    private static void printPath(int[] pre, int node, int startNode) {
        if (node == startNode) {
            System.out.print(startNode);
            return;
        }
        printPath(pre, pre[node], startNode);
        System.out.print(" -> " + node);
    }
}
