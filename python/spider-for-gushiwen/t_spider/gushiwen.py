#!/usr/bin/env python
# -*- coding: utf-8 -*-
import re
import urllib2

import sqlite3

from t_spider import viewSpider

request = urllib2.Request(r"http://www.gushiwen.org/gushi/songsan.aspx")
try:
    response = urllib2.urlopen(request)
    resp_content = response.read()
    # 返回pattern对象
    pattern = re.compile("http://so\.gushiwen\.org/view_\d+\.aspx")
    # 以下为匹配所用函数
    match_result = re.finditer(pattern, resp_content)
    conn = sqlite3.connect('gushiwen.db')
    # 创建一个Cursor:
    cursor = conn.cursor()
    # 执行一条SQL语句，创建诗表:
    cursor.execute('create table poetry (id INTEGER primary key AUTOINCREMENT , title varchar(20), poetry varchar(400))')
    for m in match_result:
        view_url = m.group()
        #print view_url
        view_result = viewSpider.resolve_view(view_url)
        insertSql = "insert into poetry (title, poetry) values ('" + view_result["title"] +"','" + view_result["poetry"] +"')"
        cursor.execute(insertSql)
        # 提交事务:
        conn.commit()
    # 关闭Cursor:
    cursor.close()
    # 关闭Connection:
    conn.close()
except urllib2.HTTPError, e:
    print e.code
except urllib2.URLError, e:
    print e.reason
except Exception, e:
    print e.message




