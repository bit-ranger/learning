from concurrent.futures import ProcessPoolExecutor, ThreadPoolExecutor
from time import sleep


def func(n):
    sleep(1)
    print(n)


if __name__ == '__main__':
    # executor = ProcessPoolExecutor(1)
    executor = ThreadPoolExecutor(1)
    for n in range(10):
        executor.submit(func, n)

