#!/usr/bin/env python
# -*- coding: utf-8 -*-
class Student(object):
    # 用tuple定义允许绑定的属性名称
    __slots__ = ('__name', '__score', '__birth')

    def __init__(self, name, score):
        # 结尾不要下划线
        self.__name = name
        self.__score = score
        self.__birth = None



    @property
    def birth(self):
        return self.__birth

    @birth.setter
    def birth(self, value):
        self.__birth = value

    def print_self(self):
        print "name:%s, score:%s, birth:%s" % (self.__name, self.__score, self.__birth)

    #toString
    def __str__(self):
        return "name:%s, score:%s, birth:%s" % (self.__name, self.__score, self.__birth)

    __repr__ = __str__

    def __iter__(self):
        return self  # 实例本身就是迭代对象，故返回自己

    def __getitem__(self, n):
        if isinstance(n, int):
            a, b = 1, 1
            for x in range(n):
                a, b = b, a + b
            return a
        if isinstance(n, slice):
            start = n.start
            stop = n.stop
            a, b = 1, 1
            L = []
            for x in range(stop):
                if x >= start:
                    L.append(a)
                a, b = b, a + b
            return L

    #当调用不存在的属性时，比如score，Python解释器会试图调用__getattr__(self, 'score')
    #来尝试获得属性
    def __getattr__(self, attr):
        return None

    def __call__(self):
        print('My name is %s.' % self.name)