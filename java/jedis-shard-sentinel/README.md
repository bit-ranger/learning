# jedis-shard-sentinel

扩展了jedis客户端，配合数据分片与sentinel使用

发送命令时，先分片，再获取分片对应的sentinel，然后从sentinel中获取Jedis客户端执行命令


用法：
~~~java
    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    Set<String> sentinelAddrSet = new HashSet<String>(Arrays.asList("127.0.0.1:26379","127.0.0.1:26380","127.0.0.1:26381"));
    JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("master1", sentinelAddrSet, jedisPoolConfig);

    JedisShardSentinelInfo jedisShardSentinelInfo1 = new JedisShardSentinelInfo(jedisSentinelPool, "shard111", 100);
    JedisShardSentinelInfo jedisShardSentinelInfo2 = new JedisShardSentinelInfo(jedisSentinelPool, "shard222", 100);

    ShardedSentinelJedisPool shardedSentinelJedisPool = new ShardedSentinelJedisPool(Arrays.asList(jedisShardSentinelInfo1, jedisShardSentinelInfo2));
    ShardedSentinelJedis ssj = shardedSentinelJedisPool.getResource();
    for (int i = 0; i < 100; i++) {
        String key = "test" + i;
        String value = "hello world" + i;
        ssj.set(key, value);
        String result = ssj.get(key);
        Assert.assertEquals(value, result);
    }
~~~
