#!/usr/bin/env python
# -*- coding: utf-8 -*-
import re
import urllib2

import sqlite3

from t_spider import viewSpider


def get_pattern(poetry):
    p = ""
    phrase_list = re.split('，|,|;|\.|。|、|？|\?|\n|\s|\(|\)|（|）|:|：|!|！', poetry)
    for i in phrase_list:
        p = p + str(len(i.decode('utf-8')))
    print p
    return p

def wash(poetry):
    poetry = poetry.replace("<br />", "")
    poetry = poetry.replace("<p><span style=\"font-family:SimSun;\">", "")
    poetry = poetry.replace("<p>", "")
    poetry = poetry.replace("</p>", "")
    poetry = poetry.replace("\n", "")
    poetry = poetry.replace("《", "")
    poetry = poetry.replace("》", "")
    poetry = poetry.replace("“", "")
    poetry = poetry.replace("”", "")
    poetry = poetry.replace("’", "")
    poetry = poetry.replace("‘", "")
    poetry = poetry.replace("<span>", "")
    poetry = poetry.replace("</span>", "")
    bi = poetry.find("(")
    if bi >= 0:
        ei = poetry.find(")")
        poetry = poetry[:bi] + poetry[ei+1:]
    return poetry

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
    cursor.execute('create table poetry (id INTEGER primary key AUTOINCREMENT , title varchar(20), poetry varchar(400), pattern varchar(24))')
    for m in match_result:
        view_url = m.group()
        #print view_url
        view_result = viewSpider.grab_view(view_url)
        view_result["poetry"] = wash(view_result["poetry"])
        insertSql = "insert into poetry (title, poetry, pattern) values ('" + view_result["title"] +"','" + view_result["poetry"] +"','" + get_pattern(view_result["poetry"]) + "')"
        cursor.execute(insertSql)
        # 提交事务:
        conn.commit()
    # 关闭Cursor:
    cursor.close()
    # 关闭Connection:
    conn.close()
except urllib2.HTTPError, e:
    print e.code, e
except urllib2.URLError, e:
    print e.reason, e
except Exception, e:
    print e.message, e






