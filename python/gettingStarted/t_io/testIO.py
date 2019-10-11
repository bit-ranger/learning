#!/usr/bin/env python
# -*- coding: utf-8 -*-
import codecs
import logging

try:
    #file-like Object
    f = open('testIO.txt', 'r')
    content = f.read(1024)
    print content
except StandardError, e:
    logging.exception(e)
finally:
    if f:
        f.close()


with codecs.open('testIO.txt', 'r') as f:
    print "with:", f.readline()

with codecs.open('testIO.txt', 'w') as f:
    f.write("你好，世界！")
