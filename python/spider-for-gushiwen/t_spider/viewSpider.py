#!/usr/bin/env python
# -*- coding: utf-8 -*-


import urllib2
import re

id_pattern=re.compile("(?<=http://so\.gushiwen\.org/view_)(.+?)(?=\.aspx)")

def resolve_view(url):
    view_id = re.search(id_pattern, url).group()
    request = urllib2.Request(url)
    try:
        response = urllib2.urlopen(request)
        resp_content = response.read()
        #print resp_content
        title_pattern = re.compile("(?<=<h1\sstyle=\"font-size:20px;\sline-height:22px;\sheight:22px;\smargin-bottom:10px;\">)(.+?)(?=</h1>)", re.S)
        poetry_pattern = re.compile("(?<=<div\sclass=\"contson\"\sid=\"contson" + view_id + "\">)(.+?)(?=</div>)", re.S)

        result = dict()
        title_match_result = re.finditer(title_pattern, resp_content)
        for m in title_match_result:
            title = m.group().strip()
            print title
            result["title"] = title

        poetry_match_result = re.finditer(poetry_pattern, resp_content)
        for m in poetry_match_result:
            poetry = m.group().strip()
            print poetry
            result["poetry"] = poetry

        if len(result) < 1:
            print "[FAILED]", url, "match nothing"
        return result
    except urllib2.HTTPError, e:
        print "[FAILED]", url, e.code
        return dict()
    except urllib2.URLError, e:
        print "[FAILED]", url, e.reason
        return dict()
    except Exception, e:
        print "[FAILED]", url, e.message
        return dict()


#grab_view("http://so.gushiwen.org/view_47069.aspx")
