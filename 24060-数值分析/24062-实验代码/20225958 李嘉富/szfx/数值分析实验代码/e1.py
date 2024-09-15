import numpy as np
import matplotlib.pyplot as plt

# Lagrange 插值多项式
def lagrange_interpolation(x_points, y_points, x):
    n = len(x_points)
    result = 0
    for i in range(n):
        term = y_points[i]
        for j in range(n):
            if j != i:
                term = term * (x - x_points[j]) / (x_points[i] - x_points[j])
        result += term
    return result

# 数据点
x_points = np.array([0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120])
y_points = np.array([5, 1, 7.5, 3, 4.5, 8.8, 15.5, 6.5, -5, -10, -2, 4.5, 7])

# 计算 x=65 处的近似值
x_target = 65
y_target = lagrange_interpolation(x_points, y_points, x_target)
print(f"在 x={x_target} 处的近似值: {y_target}")

# 绘制 Lagrange 插值多项式曲线
x_range = np.linspace(0, 120, 500)
y_range = [lagrange_interpolation(x_points, y_points, x) for x in x_range]

plt.plot(x_range, y_range, label='Lagrange 插值多项式')
plt.scatter(x_points, y_points, color='red', label='数据点')
plt.scatter(x_target, y_target, color='green', label=f'插值点 (65, {y_target:.2f})')
plt.xlabel('x')
plt.ylabel('y')
plt.legend()
plt.title('Lagrange 插值多项式')
plt.show()
