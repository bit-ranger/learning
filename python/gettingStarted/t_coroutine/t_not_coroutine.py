def consumer(n):

    while True:
        print('[CONSUMER] Consuming %s...' % n)
        return '200 OK'

def produce(c):
    n = 0
    while n < 5:
        n = n + 1
        print('[PRODUCER] Producing %s...' % n)
        r = c(n)
        print('[PRODUCER] Consumer return: %s' % r)

produce(consumer)