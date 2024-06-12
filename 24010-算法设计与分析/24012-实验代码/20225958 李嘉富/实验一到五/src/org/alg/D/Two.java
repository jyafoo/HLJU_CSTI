package org.alg.D;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 实验四：子集
 */

public class Two {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入集合S的元素，以空格分隔（例如：7 5 1 2 10）：");
        String[] elements = scanner.nextLine().split(" ");
        int[] set = new int[elements.length];
        for (int i = 0; i < elements.length; i++) {
            set[i] = Integer.parseInt(elements[i]);
        }

        System.out.println("请输入目标整数M：");
        int target = scanner.nextInt();

        List<List<Integer>> subsets = findSubsets(set, target);
        if (subsets.isEmpty()) {
            System.out.println("无解");
        } else {
            System.out.println("满足条件的子集合S1中各元素的值为：");
            for (List<Integer> subset : subsets) {
                System.out.println(subset);
            }
        }

        scanner.close();
    }

    public static List<List<Integer>> findSubsets(int[] set, int target) {
        List<List<Integer>> subsets = new ArrayList<>();
        backtrack(set, target, subsets, new ArrayList<>(), 0);
        return subsets;
    }

    public static void backtrack(int[] set, int target, List<List<Integer>> subsets, List<Integer> currentSubset, int start) {
        if (target == 0) {
            subsets.add(new ArrayList<>(currentSubset));
            return;
        }

        for (int i = start; i < set.length; i++) {
            if (target - set[i] >= 0) {
                currentSubset.add(set[i]);
                backtrack(set, target - set[i], subsets, currentSubset, i + 1);
                currentSubset.remove(currentSubset.size() - 1);
            }
        }
    }
}
