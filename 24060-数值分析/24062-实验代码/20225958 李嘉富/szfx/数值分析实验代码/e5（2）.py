import numpy as np


def gaussian_elimination_with_partial_pivoting(A, b, epsilon=1e-10):
    n = len(A)
    Ab = np.hstack((A, b.reshape(-1, 1)))  # 增强矩阵
    det = 1.0

    for k in range(n - 1):
        # 第 1 步：查找数据透视行
        max_index = np.argmax(np.abs(Ab[k:, k])) + k
        if np.abs(Ab[max_index, k]) < epsilon:
            det = 0
            break

        # 第 2 步：必要时交换行
        if max_index != k:
            Ab[[k, max_index]] = Ab[[max_index, k]]
            det = -det

        # 第 3 步：执行消除
        for i in range(k + 1, n):
            factor = Ab[i, k] / Ab[k, k]
            Ab[i, k:] -= factor * Ab[k, k:]

        det *= Ab[k, k]

    # 检查对角线上的零元素
    if np.abs(Ab[n - 1, n - 1]) < epsilon:
        det = 0
    else:
        det *= Ab[n - 1, n - 1]

        # 回代
        for i in range(n - 1, -1, -1):
            Ab[i, n] /= Ab[i, i]
            for j in range(i - 1, -1, -1):
                Ab[j, n] -= Ab[j, i] * Ab[i, n]

    x = Ab[:, n]
    return x, det


# 用法示例
A = np.array([[10, -7, 0, 1],
              [-3, 2.099999, 6, 2],
              [5, -1, 5, -1],
              [2, 1, 0, 2]], dtype=float)
b = np.array([8, 5.900001, 5, 1], dtype=float)


x, det = gaussian_elimination_with_partial_pivoting(A, b)

# print("线性方程组的解为:", x)
print("线性方程组的解为:", np.round(x, 4).astype(int))
print("系数矩阵的行列式值为:", int(det))
