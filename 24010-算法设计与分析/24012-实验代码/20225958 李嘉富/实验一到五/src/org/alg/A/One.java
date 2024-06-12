package org.alg.A;

/**
 * 实验一：最大子段和
 */

public class One {
    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        Result result = maxSubArray(nums, 0, nums.length - 1);
        System.out.println("最大字段和为: " + result.maxSum);
        System.out.println("最大字段和的起始下标为: " + result.startIndex);
        System.out.println("最大字段和的终止下标为: " + result.endIndex);
    }

    private static Result maxSubArray(int[] nums, int left, int right) {
        if (left == right) {
            return new Result(left, right, nums[left]);
        }

        int mid = left + (right - left) / 2;

        Result leftResult = maxSubArray(nums, left, mid);
        Result rightResult = maxSubArray(nums, mid + 1, right);
        Result crossResult = maxCrossingSubarray(nums, left, mid, right);

        if (leftResult.maxSum >= rightResult.maxSum && leftResult.maxSum >= crossResult.maxSum) {
            return leftResult;
        } else if (rightResult.maxSum >= leftResult.maxSum && rightResult.maxSum >= crossResult.maxSum) {
            return rightResult;
        } else {
            return crossResult;
        }
    }

    private static Result maxCrossingSubarray(int[] nums, int left, int mid, int right) {
        int leftSum = Integer.MIN_VALUE;
        int sum = 0;
        int maxLeft = mid;

        for (int i = mid; i >= left; i--) {
            sum += nums[i];
            if (sum > leftSum) {
                leftSum = sum;
                maxLeft = i;
            }
        }

        int rightSum = Integer.MIN_VALUE;
        sum = 0;
        int maxRight = mid + 1;

        for (int i = mid + 1; i <= right; i++) {
            sum += nums[i];
            if (sum > rightSum) {
                rightSum = sum;
                maxRight = i;
            }
        }

        return new Result(maxLeft, maxRight, leftSum + rightSum);
    }

    static class Result {
        int startIndex;
        int endIndex;
        int maxSum;

        public Result(int startIndex, int endIndex, int maxSum) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.maxSum = maxSum;
        }
    }

}
