#!/usr/bin/env python
# -*- coding: utf-8 -*-

from collections import Iterable

L = range(100)

#从索引0到索引9, 每2个元素取一个

print(L[0:10:2])

d = {'a': 1, 'b': 2, 'c': 3}
for key in d:
    print(key)

isIte = isinstance('abc', Iterable)
print(isIte)


for i, v in enumerate(['A', 'B', 'C']):
    print(i, v)

for x, y in [(1, 1), (2, 4), (3, 9)]:
    print(x, y)


#列表生成式
gen = [x * x for x in range(1, 11) if x % 2 == 0]
print(gen)

gen2 = [m + n for m in 'ABC' for n in 'XYZ']
print(gen2)

genD = [k + '=' + str(v) for k, v in d.items()]
print(genD)

#生成器
g = (x * x for x in range(10))
print(g)
print(next(g))
print(next(g))
for i in g:
    print(i)

#yield将函数变成生成器
#generator和函数的执行流程不一样。函数是顺序执行，遇到return语句或者最后一行函数语句就返回。
#而变成generator的函数，在每次调用next()的时候执行，遇到yield语句返回，再次执行时从上次返回的yield语句处继续执行。
def fib(max):
    n, a, b = 0, 0, 1
    while n < max:
        yield b
        a, b = b, a + b
        n = n + 1

gf = fib(10)
for i in gf:
    print(i);