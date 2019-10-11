#!/usr/bin/env python
# -*- coding: utf-8 -*-
import codecs

order_relation = []
with codecs.open(r'C:\Users\since\Downloads\20170209_task\order_relation.txt', 'r') as f:
    ln = 0
    for line in f:
        ln += 1
        if ln <= 1:
            continue
        arr = line.strip(' \t\n\r').split("	")
        obj = {"id": arr[0].strip(' \t\n\r'),
               "user_id": arr[1].strip(' \t\n\r'),
               "order_id": arr[2].strip(' \t\n\r'),
               "entity_id": arr[3].strip(' \t\n\r'),
               "entity_type": arr[4].strip(' \t\n\r')}
        order_relation.append(obj)
print 'order_relation:', len(order_relation)

order_num = []
with codecs.open(r'C:\Users\since\Downloads\20170209_task\order_num.txt', 'r') as f:
    ln = 0
    for line in f:
        ln += 1
        if ln <= 1:
            continue
        arr = line.strip(' \t\n\r').split("	")
        obj = {"buyer_id": arr[0].strip(' \t\n\r'), "num": arr[1].strip(' \t\n\r')}
        order_num.append(obj)
print 'order_num:', len(order_num)

userTaskId_userId = []
with codecs.open(r'C:\Users\since\Downloads\20170209_task\userTaskId_userId.txt', 'r') as f:
    ln = 0
    for line in f:
        ln += 1
        if ln <= 1:
            continue
        arr = line.strip(' \t\n\r').split("|")
        obj = {"user_task_id": arr[1].strip(' \t\n\r'), "user_id": arr[2].strip(' \t\n\r')}
        userTaskId_userId.append(obj)
print 'userTaskId_userId:', len(userTaskId_userId)

order_relation_distinct = []


def distinct_data():
    for rel in order_relation:
        for ut in userTaskId_userId:
            if (rel['entity_type'] == '1') and (rel['user_id'] == ut['user_id']) and (rel['entity_id'] == ut['user_task_id']):
                order_relation_distinct.append(rel)


distinct_data()
print 'order_relation_distinct:', len(order_relation_distinct)


order_num_group = {}
def group_order_num():
    for o in order_num:
        order_num_group[o['buyer_id']] = o['num']
group_order_num()


user_group = {}
def group_user():
    for u in order_relation_distinct:
        user_id = u['user_id']
        obj = user_group.get(user_id, None)
        if obj is None:
            obj = {'user_id': user_id, 'task_num': 0, 'true_num': 0, 'order_num': order_num_group.get(user_id, 0)}
            user_group[user_id] = obj
        obj['task_num'] += 1
        if u['order_id'] != '-1':
            obj['true_num'] += 1
group_user()
print len(user_group)


eventual_list = []
def eventual_filter():
    for key, value in user_group.iteritems():
        m = min(int(value['task_num']), int(value['order_num']))
        if int(value['true_num']) < m:
            value['reissue'] = m - int(value['true_num'])
            eventual_list.append(value)
eventual_filter()

with codecs.open(r'C:\Users\since\Downloads\20170209_task\eventual.txt', 'w') as f:
    for row in eventual_list:
        line = row['user_id'] + "," + \
               str(row['task_num']) + "," + \
               str(row['true_num']) + "," + \
               str(row['order_num']) + "," + \
               str(row['reissue'])
        f.write(line + "\n")
