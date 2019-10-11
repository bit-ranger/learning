#!/usr/bin/env python
# -*- coding: utf-8 -*-
from customFunction import my_abs, move
import math

name = raw_input('please enter your name: ')
print '你好,%s:%d%%' % (name, 98)

nameList = ['Michael', 'Bob', 'Tracy']
print 'You are the %dth user' % (len(nameList) + 1)
nameList.pop()
nameList.pop(0)
nameList[0] = 666
nameList.append(998)
nameList.insert(0, name)
print nameList

#tuple中每个元素的指向不变
#括号()既可以表示tuple，又可以表示数学公式中的小括号，这就产生了歧义，
# 因此，Python规定，这种情况下，按小括号进行计算，计算结果自然是1。
# 所以，只有1个元素的tuple定义时必须加一个逗号,，来消除歧义：
tupleNameList_s = ('Michael')
print tupleNameList_s
tupleNameList = ('Michael',)
print tupleNameList, len(tupleNameList)

if len(nameList) == 0:
    print "空的"
elif len(nameList) >= 10:
    print "多于%d个" % 10
else:
    print "共有%d个" % len(nameList)

for ipt in ('birth', 'sex'):
    iptTet = raw_input(ipt+':')
    if ipt == 'birth':
        if iptTet > '1900':
            print '90后'
sum = 0
for x in range(1, 101, 1):
    sum += x
print sum

sum = 0
n = 1
while n <= 100:
    if n % 2 == 2:
        continue
    elif n == 50:
        break
    sum += n
    n += 1
print sum

dict = {'Michael': 95, 'Bob': 75, 'Tracy': 85}
dict['Adam'] = 67
print dict['Michael']
print dict.get("default", -1)
print 'Adam' in dict
dict.pop('Adam')
print dict

s1 = set([1, 2, 3])
s1.add(4)
s1.remove(2)
s2 = set([1, 5, 6])
print s1 & s2
print s1 | s2

print my_abs(-8)

#假象，其实返回的是一个tuple
x, y = move(100, 100, 60, math.pi / 6)
print x, y



