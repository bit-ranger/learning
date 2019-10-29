#!/usr/bin/env python
# -*- coding: utf-8 -*-
import hashlib
import logging
import time

import sys
from lxml import objectify, etree
from flask import Flask, request
import gushiwen

logger = logging.getLogger("wechat-back-end")

formatter = logging.Formatter('%(name)-12s %(asctime)s %(levelname)-8s %(message)s', '%a, %d %b %Y %H:%M:%S',)
file_handler = logging.FileHandler("/var/log/wechat-back-end/wechat-back-end.log")
file_handler.setFormatter(formatter)
stream_handler = logging.StreamHandler(sys.stderr)
stream_handler.setFormatter(formatter)

# logger.addHandler(stream_handler)
logger.addHandler(file_handler)
logger.setLevel(logging.DEBUG)

app = Flask(__name__)

@app.route('/basic/message', methods=['GET'])
def check_token():
    timestamp = request.args.get('timestamp')
    nonce = request.args.get('nonce')
    signature = request.args.get('signature')
    echostr = request.args.get('echostr')
    token = ur"HSXYHJC"
    pl = [token, timestamp, nonce]
    logger.debug("check_token >>>> " + str(pl))
    try:
        pl_str = reduce(lambda x, y: x+y, sorted(pl))
        pl_sr_sha1 = hashlib.sha1(pl_str).hexdigest()
        if signature == pl_sr_sha1:
            return echostr
        else:
            return ""
    except Exception, e:
        logger.exception(e.message)



@app.route('/basic/message', methods=['POST'])
def msg():
    requ_data = ""
    resp_content = ""
    try:
        xml = request.data
        requ_data = objectify.fromstring(xml)

        if requ_data.MsgType.text == ur"text":
            if requ_data.Content.text.startswith(ur"1:"):
                poetry_list = gushiwen.search(requ_data.Content.text[2:])
                for t in poetry_list:
                    if len(resp_content.encode("utf-8")) + len((t[0] + "\n" + t[1] + "\n\n").encode("utf-8")) > 2048:
                        break
                    resp_content += t[0] + "\n" + t[1] + "\n\n"

            elif requ_data.Content.text.startswith(ur"2:"):
                poetry_list = gushiwen.match(requ_data.Content.text[2:])
                titles = gushiwen.title_list(poetry_list)
                for t in titles:
                    if len(resp_content.encode("utf-8")) + len((t + "\n").encode("utf-8")) > 2048:
                        break
                    resp_content += t + "\n"

            else:
                resp_content = ur"1:按标题搜索" + "\n" + ur"2:按格式匹配"

        else:
            resp_content = ur"无法识别的消息"

        if len(resp_content) == 0:
            resp_content = ur"无匹配结果"

    except Exception, e:
        logger.exception(e.message)
        resp_content = ur"内部错误"
    finally:
        resp_data = etree.Element("xml")
        etree.SubElement(resp_data, 'ToUserName').text = etree.CDATA(requ_data.FromUserName.text)
        etree.SubElement(resp_data, 'FromUserName').text = etree.CDATA(requ_data.ToUserName.text)
        etree.SubElement(resp_data, 'MsgType').text = etree.CDATA("text")
        etree.SubElement(resp_data, 'CreateTime').text = str(int(time.time()))
        etree.SubElement(resp_data, 'Content').text = etree.CDATA(resp_content)
        out = etree.tostring(resp_data, encoding='utf-8')
        logger.debug("\n" + request.data + "\n >>> \n" + out)
        return out


@app.route('/')
def hello_world():
    return 'Hello World!'


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=8080)
