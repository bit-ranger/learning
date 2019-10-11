#!/usr/bin/env python
# -*- coding: utf-8 -*-
from types import MethodType

from t_objectOriented.Student import Student


# 定义一个函数作为实例方法
def set_age(self, age):
    self.age = age


bart = Student("Bart", 18)
#给一个实例绑定的方法，对另一个实例是不起作用的
bart.set_age = MethodType(set_age, bart, Student)
#为了给所有实例都绑定方法，可以给class绑定方法
Student.set_age = MethodType(set_age, None, Student)