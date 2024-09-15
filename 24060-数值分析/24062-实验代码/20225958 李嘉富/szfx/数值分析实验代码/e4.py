import math


def function(x):
    return math.exp(x) * math.cos(x)


def T(a, b, h, N):
    f = 0
    for k in range(1, N):
        f += function(a + k * h)
    return (h / 2) * (function(a) + 2 * f + function(b))


def Fh(a, b, h, N):
    Hn = 0
    for i in range(1, N + 1):
        h = (b - a) / N
        Hn += function(a + (h * (2 * i - 1) / 2))
    return h * Hn


if __name__ == "__main__":
    a, b = 0, math.pi
    n = 1
    t = T(a, b, b - a, n)

Tn, T2n, Hn = 0, 0, 0
h = b - a

while True:
    Hn = Fh(a, b, h, n)
    Tn = t
    T2n = 0.5 * (T(a, b, h, n) + Hn)
    n = 2 * n
    h = h / 2
    t = T2n
    if abs(T2n - Tn) < 0.000001:
        break

print(f"Tn={Tn:.8f}")
print(f"等分数为：{n // 2}")
