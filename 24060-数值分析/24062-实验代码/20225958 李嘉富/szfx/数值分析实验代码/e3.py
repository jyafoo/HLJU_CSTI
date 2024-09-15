import numpy as np
import matplotlib.pyplot as plt

# 数据点
x = np.array([0, 10, 20, 30, 40, 50, 60, 70, 80, 90])
y = np.array([68, 67.1, 66.4, 65.6, 64.6, 61.8, 61.0, 60.8, 60.4, 60])

# 计算线性最小二乘法的系数
n = len(x)
sum_x = np.sum(x)
sum_y = np.sum(y)
sum_xy = np.sum(x * y)
sum_x2 = np.sum(x * x)

a = (n * sum_xy - sum_x * sum_y) / (n * sum_x2 - sum_x**2)
b = (sum_y * sum_x2 - sum_x * sum_xy) / (n * sum_x2 - sum_x**2)

# 预测x=55处的y值
x_predict = 55
y_predict = a * x_predict + b

print(f"拟合直线方程: y = {a:.4f} * x + {b:.4f}")
print(f"在x=55处的预测值: y = {y_predict:.4f}")

# 画出实验数据和拟合直线
plt.scatter(x, y, color='red', label='实验数据')
plt.plot(x, a * x + b, color='blue', label='拟合直线')
plt.scatter(x_predict, y_predict, color='green', label=f'预测点 (55, {y_predict:.4f})')
plt.xlabel('x')
plt.ylabel('y')
plt.legend()
plt.title('线性最小二乘法曲线拟合')
plt.show()
