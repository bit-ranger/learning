package com.bitranger.learning.curator.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.framework.recipes.locks.RevocationListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class Lock {

    // ZooKeeper 锁节点路径, 分布式锁的相关操作都是在这个节点上进行
    private final String lockPath = "/demo/curator/distributed-lock";

    // ZooKeeper 服务地址, 单机格式为:(127.0.0.1:2181), 集群格式为:(127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183)
    private String connectString;
    // Curator 客户端重试策略
    private RetryPolicy retry;
    // Curator 客户端对象
    private CuratorFramework client;
    // client2 用户模拟其他客户端
    private CuratorFramework client2;

    // 初始化资源
    @Before
    public void init() throws Exception {
        // 设置 ZooKeeper 服务地址为本机的 2181 端口
        connectString = "127.0.0.1:2181";
        // 重试策略
        // 初始休眠时间为 1000ms, 最大重试次数为 3
        retry = new ExponentialBackoffRetry(1000, 3);
        // 创建一个客户端, 60000(ms)为 session 超时时间, 15000(ms)为链接超时时间
        client = CuratorFrameworkFactory.newClient(connectString, 60000, 15000, retry);
        client2 = CuratorFrameworkFactory.newClient(connectString, 60000, 15000, retry);
        // 创建会话
        client.start();
        client2.start();
    }

    // 释放资源
    @After
    public void close() {
        CloseableUtils.closeQuietly(client);
    }


    @Test
    public void semmaphore() throws Exception {
        InterProcessLock lock = new InterProcessSemaphoreMutex(client, lockPath);
        // lock2 用于模拟其他客户端
        InterProcessLock lock2 = new InterProcessSemaphoreMutex(client2, lockPath);

        // 获取锁对象
        lock.acquire();

        // 测试是否可以重入
        // 超时获取锁对象(第一个参数为时间, 第二个参数为时间单位), 因为锁已经被获取, 所以返回 false
        Assert.assertFalse(lock.acquire(2, TimeUnit.SECONDS));
        // 释放锁
        lock.release();

        // lock2 尝试获取锁成功, 因为锁已经被释放
        Assert.assertTrue(lock2.acquire(2, TimeUnit.SECONDS));
        lock2.release();
    }

    @Test
    public void reentrantLock() throws Exception {
        InterProcessMutex lock = new InterProcessMutex(client, lockPath);
        lock.makeRevocable(new RevocationListener<InterProcessMutex>(){

            public void revocationRequested(InterProcessMutex forLock) {

            }
        });
        // lock2 用于模拟其他客户端
        InterProcessLock lock2 = new InterProcessMutex(client2, lockPath);
        // lock 获取锁
        lock.acquire();
        try {
            // lock 第二次获取锁
            lock.acquire();
            try {
                // lock2 超时获取锁, 因为锁已经被 lock 客户端占用, 所以获取失败, 需要等 lock 释放
                Assert.assertFalse(lock2.acquire(2, TimeUnit.SECONDS));
            } finally {
                lock.release();
            }
        } finally {
            // 重入锁获取与释放需要一一对应, 如果获取 2 次, 释放 1 次, 那么该锁依然是被占用, 如果将下面这行代码注释, 那么会发现下面的 lock2 获取锁失败
            lock.release();
        }
        // 在 lock 释放后, lock2 能够获取锁
        Assert.assertTrue(lock2.acquire(2, TimeUnit.SECONDS));
        lock2.release();
    }
}
