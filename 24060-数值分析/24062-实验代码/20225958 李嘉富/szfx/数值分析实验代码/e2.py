from matplotlib import pyplot
import numpy


def function_this(x):
    for i in range(len(ltx)):
        if ltx[i] > x:
            break
    cnt1 = (x - ltx[i]) / (ltx[i - 1] - ltx[i])
    cnt2 = (x - ltx[i - 1]) / (ltx[i] - ltx[i - 1])
    return pow(cnt1, 2) * (1 + 2 * cnt2) * lty[i - 1] + pow(cnt2, 2) * (1 + 2 * cnt1) * lty[i] + pow(cnt1, 2) * (x - ltx[i - 1]) * ltm[i - 1] + pow(cnt2, 2) * (x - ltx[i]) * ltm[i]


ltx = [0.10, 0.20, 0.30, 0.40, 0.50, 0.60, 0.70, 0.80, 0.90, 1.00]
lty = [0.904837, 0.818731, 0.740818, 0.670320, 0.606531, 0.548812, 0.496585, 0.449329, 0.406570, 0.367879]
ltm = [-0.904837, -0.81873, -0.740818, -0.670320, -0.606531, -0.548812, -0.496585, -0.449329, -0.406570, -0.367879]

x2 = numpy.linspace(-2, 2, endpoint=False)
y2 = []
for i in range(len(x2)):
    y2.append(function_this(x2[i]))

print(function_this(0.55))
# 使用matplotlib进行图形绘制
pyplot.plot(x2, y2)
pyplot.scatter(ltx, lty, marker=".")
pyplot.show()

# 0.1 0.2 0.3 0.4 0.5 0.6 0.7 0.8 0.9 1.00
# 0.904837 0.818731 0.740818 0.670320 0.606531 0.548812	0.496585 0.449329 0.406570 0.367879
# -0.904837 -0.818731 -0.740818 -0.670320 -0.606531 -0.548812 -0.496585	-0.449329 -0.406570	-0.367879
