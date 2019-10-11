#!/usr/bin/env python
# -*- coding: utf-8 -*-
import logging

logging.basicConfig(level=logging.INFO)

def foo(s):
    n = int(s)
    return 10 / n

def bar(s):
    try:
        logging.info("====begin====")
        result = foo(s) * 2
    except ZeroDivisionError, e:
        logging.exception(e)
        raise e
    except StandardError, e:
        print 'Error!'
        raise #raise语句如果不带参数，就会把当前错误原样抛出
    else:
        print "no error"
        return result
    finally:
        print "finally"

def main():
    bar('0')

main()