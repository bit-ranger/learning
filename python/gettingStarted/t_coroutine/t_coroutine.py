def consumer():
    r = ''
    print('[CONSUMER] init %s...' % r)
    while True:
        print('[CONSUMER] before Consuming %s...' % r)
        #yield将会保存r的值，并不会每次重置
        #yield可以接收多个值
        n = yield r
        print('[CONSUMER] after  Consuming %s...' % n)
        r = 'OK'

def produce(c):
    print('[PRODUCER] init...')
    # 第一次发送None会使consumer初始化
    c.send(None)
    print('[PRODUCER] send None...')
    # 从第二次发送开始，将不再初始化，而是进入循环
    c.send(None)
    print('[PRODUCER] send None...')
    n = 0
    while n < 5:
        n = n + 1
        print('[PRODUCER] Producing %s...' % n)
        r = c.send(n)
        print('[PRODUCER] Consumer return: %s' % r)
    c.close()

c = consumer()
produce(c)