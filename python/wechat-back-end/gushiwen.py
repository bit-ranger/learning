#!/usr/bin/env python
# -*- coding: utf-8 -*-
import logging
import re
import sqlite3
import sys

logger = logging.getLogger("gushiwen")

formatter = logging.Formatter('%(name)-12s %(asctime)s %(levelname)-8s %(message)s', '%a, %d %b %Y %H:%M:%S',)
file_handler = logging.FileHandler("/var/log/wechat-back-end/gushiwen.log")
file_handler.setFormatter(formatter)
stream_handler = logging.StreamHandler(sys.stderr)
stream_handler.setFormatter(formatter)

#logger.addHandler(stream_handler)
logger.addHandler(file_handler)
logger.setLevel(logging.DEBUG)


def get_pattern(poetry):
    try:
        p = ""
        phrase_list = re.split(ur'，|,|;|\.|。|、|？|\?|\n|\s|\(|\)|（|）|:|：|!|！', poetry,  flags=re.UNICODE)
        for i in phrase_list:
            p = p + str(len(i))
        logging.debug("get_pattern >>> " + poetry + " >>> " + p)
        return p
    except Exception, e:
        logger.exception(e.message)
        return 0


def match(poetry_text):
    cursor = None
    conn = None
    try:
        if not isinstance(poetry_text, basestring):
            return []

        p = get_pattern(poetry_text)
        conn = sqlite3.connect('/usr/share/sqlite3/gushiwen.db')
        cursor = conn.cursor()
        sql = "select title,poetry from poetry where pattern LIKE '%" + p + "%'"
        cursor.execute(sql)
        conn.commit()

        values = cursor.fetchall()
        logging.debug("match >>> " + poetry_text + " >>> " + str(len(values)))

        return values
    except Exception, e:
        logger.exception(e.message)
        return []
    finally:
        # 关闭Cursor:
        if cursor:
            cursor.close()
        # 关闭Connection:
        if conn:
            conn.close()


def title_list(poetry_list):
    tl = []
    for p in poetry_list:
        if p[0] not in tl:
            tl.append(p[0])
    return tl


def search(title_text):
    cursor = None
    conn = None
    try:
        if not isinstance(title_text, basestring):
            return []

        conn = sqlite3.connect('/usr/share/sqlite3/gushiwen.db')
        cursor = conn.cursor()
        sql = "select title,poetry from poetry where title LIKE '%" + title_text + "%'"
        cursor.execute(sql)
        conn.commit()

        values = cursor.fetchall()
        logging.debug("search >>> " + title_text + " >>> " + str(len(values)))

        return values
    except Exception, e:
        logger.exception(e.message)
        return []
    finally:
        # 关闭Cursor:
        if cursor:
            cursor.close()
        # 关闭Connection:
        if conn:
            conn.close()