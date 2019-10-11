#!/usr/bin/env python
# -*- coding: utf-8 -*-
# metaclass是创建类，所以必须从`type`类型派生：
class ListMetaclass(type):
    def __new__(cls, name, bases, attrs):
        attrs['add'] = lambda self, value: self.append(value)
        #当前准备创建的类的对象, 类的名字, 类继承的父类集合, 类的方法集合
        return type.__new__(cls, name, bases, attrs)

class MyList(list):
    __metaclass__ = ListMetaclass # 指示使用ListMetaclass来定制类