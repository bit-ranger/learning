#!/usr/bin/env python
# -*- coding: utf-8 -*-
import codecs

userIdList = []
with codecs.open(r'C:\Users\since\Downloads\task_user_id.txt', 'r') as f:
    for line in f:
        userIdList.append(line.strip(' \t\n\r'))

rows = []
with codecs.open(r'C:\Users\since\Downloads\user_taskNum_TrueNum_falseNum_orderNum.txt', 'r') as f:
    for line in f:
        colVals = line.split(",")
        try:
            dict = {'user_id': colVals[0],
                    'task_num': int(colVals[1]),
                    'false_num': int(colVals[2]),
                    'true_num': int(colVals[3]),
                    'order_num': int(colVals[4].strip(' \t\n\r'))}
        except StandardError, e:
            print line
        if (dict['task_num'] == dict['order_num']) and (dict['false_num'] > 0):
            if dict['user_id'] in userIdList:
                rows.append(dict)

print len(rows)

with codecs.open('C:\Users\since\Downloads\user_taskNum_TrueNum_falseNum_orderNum_resolved.txt', 'w') as f:
    for row in rows:
        line = row['user_id'] + "," + \
               str(row['task_num']) + "," + \
               str(row['false_num']) + "," + \
               str(row['true_num']) + "," + \
               str(row['order_num'])
        f.write(line + "\n")
