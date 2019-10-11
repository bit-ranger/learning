#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os

print os.name
#print os.uname()
print os.environ
print os.getenv("PATH")

#绝对路径
print os.path.abspath('.')
#拼接路径
print os.path.join(os.path.abspath('.'), 'testdir')
#拆分路径
print os.path.split(os.path.abspath('.'))
#扩展名
print os.path.splitext("textIO.txt")

#创建目录
os.mkdir(os.path.join(os.path.abspath('.'), 'testdir'))
#删除目录
os.rmdir(os.path.join(os.path.abspath('.'), 'testdir'))

# os.rename('test.txt', 'test.py')
# os.remove('test.py')

dirList = [x for x in os.listdir('..') if os.path.isdir(x)]
print dirList
