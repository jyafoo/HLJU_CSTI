import numpy as np


# 高斯消元法
def gaussian_elimination_with_partial_pivoting(A, b, epsilon=1e-10):
    n = len(A)
    Ab = np.hstack((A, b.reshape(-1, 1)))  # 增强矩阵
    # 第一步
    det = 1.0

    # 第二步
    for k in range(n - 1):
        # 按列选取主元
        max_index = np.argmax(np.abs(Ab[k:, k])) + k

        if np.abs(Ab[max_index, k]) < epsilon:
            det = 0
            break

        if max_index != k:
            Ab[[k, max_index]] = Ab[[max_index, k]]
            det = -det

        # 消元计算
        for i in range(k + 1, n):
            factor = Ab[i, k] / Ab[k, k]
            Ab[i, k:] -= factor * Ab[k, k:]

        det *= Ab[k, k]

    # 第三步
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
print("线性方程组的解为:", x)
print("系数矩阵的行列式值为:", det)
