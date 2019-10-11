#!/usr/bin/env python
# -*- coding: utf-8 -*-
#序列化
try:
    import cPickle as pickle
except ImportError:
    import pickle
import json

d = dict(name='Bob', age=20, score=88)
print d
print json.dumps(d, default=lambda obj:obj.__dict__)

with open("testIO.txt", "w") as f:
    json.dump(d, f, default=lambda obj:obj.__dict__ )

with open("testIO.txt", "r") as f:
    obj = json.load(f)
    print obj
