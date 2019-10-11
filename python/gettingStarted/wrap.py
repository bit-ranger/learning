import functools


def log(func):
    @functools.wraps(func)
    def wrapper(*args, **kw):
        print 'call %s():' % func.__name__
        return func(*args, **kw)
    return wrapper


int2 = functools.partial(int, base=2)
print int2("0001")

max2 = functools.partial(max, 10)
print max2(1,2)