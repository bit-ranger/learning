#!/usr/bin/env python
# -*- coding: utf-8 -*-

from flask import Flask, request

from lxml import objectify, etree

app = Flask(__name__)

@app.route('/', methods=['GET'])
def test():
    return "It Works"

@app.route('/basic/message', methods=['GET', 'POST'])
def msg():
    request.args.get('timestamp')
    request.args.get('nonce')
    request.args.get('openid')
    request.args.get('signature')
    data = request.data
    xml = objectify.fromstring(data)
    return etree.tostring(xml)


if __name__ == '__main__':
    app.run(port=8080)