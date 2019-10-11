#!/usr/bin/env python
# -*- coding: utf-8 -*-
import types
from t_objectOriented.Student import Student

bart = Student("Bart", 18)
lisa = Student("lisa", 20)
lisa.birth = "1991"

bart.print_self()
lisa.print_self()

print callable(lisa)

print isinstance(bart, Student)

print type(Student)
print type("123") == types.StringType, type("123") == types.TypeType

print dir(Student)

def fnHello(self, name=' world'):
    print "hello, %s" % name

#动态创建类
HelloClass = type("HelloClass", (object,), dict(hello=fnHello))
h = HelloClass()
h.hello(" ... world")


