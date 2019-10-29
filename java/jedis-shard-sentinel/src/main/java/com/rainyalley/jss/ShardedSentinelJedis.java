package com.rainyalley.jss;

import redis.clients.jedis.*;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;
import redis.clients.util.Sharded;

import java.io.Closeable;
import java.util.*;

public class ShardedSentinelJedis extends Sharded<JedisSentinelPool, JedisShardSentinelInfo> implements JedisCommands, ScriptingCommands, Closeable{

    public ShardedSentinelJedis(List<JedisShardSentinelInfo> shards) {
        super(shards);
    }

    @Override
    public String set(String key, String value) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            String result = jedis.set(key, value);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

    }

    @Override
    public String set(String key, String value, String nxxx, String expx, long time) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            String result = jedis.set(key, value, nxxx, expx, time);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public String set(String key, String value, String nxxx) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            String result = jedis.set(key, value, nxxx);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public String get(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            String result = jedis.get(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Boolean exists(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Boolean result = jedis.exists(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long persist(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.persist(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public String type(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            String result = jedis.type(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long expire(String key, int seconds) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.expire(key, seconds);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long pexpire(String key, long milliseconds) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.pexpire(key, milliseconds);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long expireAt(String key, long unixTime) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.expireAt(key, unixTime);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long pexpireAt(String key, long millisecondsTimestamp) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.pexpireAt(key, millisecondsTimestamp);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long ttl(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.ttl(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long pttl(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.pttl(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Boolean setbit(String key, long offset, boolean value) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Boolean result = jedis.setbit(key, offset, value);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Boolean setbit(String key, long offset, String value) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Boolean result = jedis.setbit(key, offset, value);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Boolean getbit(String key, long offset) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Boolean result = jedis.getbit(key, offset);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long setrange(String key, long offset, String value) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.setrange(key, offset, value);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public String getrange(String key, long startOffset, long endOffset) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            String result = jedis.getrange(key, startOffset, endOffset);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public String getSet(String key, String value) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            String result = jedis.getSet(key, value);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long setnx(String key, String value) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.setnx(key, value);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public String setex(String key, int seconds, String value) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            String result = jedis.setex(key, seconds, value);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public String psetex(String key, long milliseconds, String value) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            String result = jedis.psetex(key, milliseconds, value);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long decrBy(String key, long integer) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.decrBy(key, integer);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long decr(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.decr(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long incrBy(String key, long integer) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.incrBy(key, integer);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Double incrByFloat(String key, double value) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Double result = jedis.incrByFloat(key, value);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long incr(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.incr(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long append(String key, String value) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.append(key, value);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public String substr(String key, int start, int end) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            String result = jedis.substr(key, start, end);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long hset(String key, String field, String value) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.hset(key, field, value);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public String hget(String key, String field) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            String result = jedis.hget(key, field);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long hsetnx(String key, String field, String value) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.hsetnx(key, field, value);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public String hmset(String key, Map<String, String> hash) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            String result = jedis.hmset(key, hash);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public List<String> hmget(String key, String... fields) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            List<String> result = jedis.hmget(key, fields);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long hincrBy(String key, String field, long value) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.hincrBy(key, field, value);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Double hincrByFloat(String key, String field, double value) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Double result = jedis.hincrByFloat(key, field, value);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Boolean hexists(String key, String field) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Boolean result = jedis.hexists(key, field);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long hdel(String key, String... field) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.hdel(key, field);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long hlen(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.hlen(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<String> hkeys(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<String> result = jedis.hkeys(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public List<String> hvals(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            List<String> result = jedis.hvals(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Map<String, String> hgetAll(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Map<String, String> result = jedis.hgetAll(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long rpush(String key, String... string) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.rpush(key, string);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long lpush(String key, String... string) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.lpush(key, string);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long llen(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.llen(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public List<String> lrange(String key, long start, long end) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            List<String> result = jedis.lrange(key, start, end);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public String ltrim(String key, long start, long end) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            String result = jedis.ltrim(key, start, end);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public String lindex(String key, long index) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            String result = jedis.lindex(key, index);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public String lset(String key, long index, String value) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            String result = jedis.lset(key, index, value);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long lrem(String key, long count, String value) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.lrem(key, count, value);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public String lpop(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            String result = jedis.lpop(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public String rpop(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            String result = jedis.rpop(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long sadd(String key, String... member) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.sadd(key, member);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<String> smembers(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<String> result = jedis.smembers(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long srem(String key, String... member) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.srem(key, member);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public String spop(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            String result = jedis.spop(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<String> spop(String key, long count) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<String> result = jedis.spop(key, count);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long scard(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.scard(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Boolean sismember(String key, String member) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Boolean result = jedis.sismember(key, member);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public String srandmember(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            String result = jedis.srandmember(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public List<String> srandmember(String key, int count) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            List<String> result = jedis.srandmember(key, count);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long strlen(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.strlen(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long zadd(String key, double score, String member) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.zadd(key, score, member);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long zadd(String key, double score, String member, ZAddParams params) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.zadd(key, score, member, params);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long zadd(String key, Map<String, Double> scoreMembers) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.zadd(key, scoreMembers);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.zadd(key, scoreMembers, params);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<String> zrange(String key, long start, long end) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<String> result = jedis.zrange(key, start, end);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long zrem(String key, String... member) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.zrem(key, member);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Double zincrby(String key, double score, String member) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Double result = jedis.zincrby(key, score, member);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Double zincrby(String key, double score, String member, ZIncrByParams params) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Double result = jedis.zincrby(key, score, member, params);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long zrank(String key, String member) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.zrank(key, member);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long zrevrank(String key, String member) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.zrevrank(key, member);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<String> zrevrange(String key, long start, long end) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<String> result = jedis.zrevrange(key, start, end);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<Tuple> result = jedis.zrangeWithScores(key, start, end);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<Tuple> result = jedis.zrevrangeWithScores(key, start, end);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long zcard(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.zcard(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Double zscore(String key, String member) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Double result = jedis.zscore(key, member);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public List<String> sort(String key) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            List<String> result = jedis.sort(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public List<String> sort(String key, SortingParams sortingParameters) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            List<String> result = jedis.sort(key, sortingParameters);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long zcount(String key, double min, double max) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.zcount(key, min, max);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long zcount(String key, String min, String max) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.zcount(key, min, max);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<String> result = jedis.zrangeByScore(key, min, max);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<String> zrangeByScore(String key, String min, String max) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<String> result = jedis.zrangeByScore(key, min, max);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<String> result = jedis.zrangeByScore(key, max, min);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<String> result = jedis.zrangeByScore(key, min, max, offset, count);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<String> zrevrangeByScore(String key, String max, String min) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<String> result = jedis.zrevrangeByScore(key, max, min);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<String> result = jedis.zrangeByScore(key, min, max, offset, count);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<String> result = jedis.zrevrangeByScore(key, max, min, offset, count);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<Tuple> result = jedis.zrangeByScoreWithScores(key, min, max);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<Tuple> result = jedis.zrevrangeByScoreWithScores(key, max, min);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<Tuple> result = jedis.zrangeByScoreWithScores(key, min, max, offset, count);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<String> result = jedis.zrevrangeByScore(key, max, min, offset, count);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<Tuple> result = jedis.zrangeByScoreWithScores(key, min, max);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<Tuple> result = jedis.zrevrangeByScoreWithScores(key, max, min);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<Tuple> result = jedis.zrevrangeByScoreWithScores(key, min, max, offset, count);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<Tuple> result = jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {


        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<Tuple> result = jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long zremrangeByRank(String key, long start, long end) {

        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.zremrangeByRank(key, start, end);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long zremrangeByScore(String key, double start, double end) {

        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.zremrangeByScore(key, start, end);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long zremrangeByScore(String key, String start, String end) {

        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.zremrangeByScore(key, start, end);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long zlexcount(String key, String min, String max) {

        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.zlexcount(key, min, max);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max) {

        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<String> result = jedis.zrangeByLex(key, min, max);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {

        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<String> result = jedis.zrangeByLex(key, min, max, offset, count);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min) {

        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<String> result = jedis.zrevrangeByLex(key, max, min);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {

        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Set<String> result = jedis.zrevrangeByLex(key, max, min, offset, count);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long zremrangeByLex(String key, String min, String max) {

        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.zremrangeByLex(key, min, max);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {

        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.linsert(key, where, pivot, value);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long lpushx(String key, String... string) {

        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.lpush(key, string);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long rpushx(String key, String... string) {

        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.rpush(key, string);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public List<String> blpop(String arg) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(arg);
            List<String> result = jedis.blpop(arg);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    @Override
    public List<String> blpop(int timeout, String key) {

        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            List<String> result = jedis.blpop(timeout, key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public List<String> brpop(String arg) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(arg);
            List<String> result = jedis.brpop(arg);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

    }


    @Override
    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            List<String> result = jedis.brpop(timeout, key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long del(String key) {

        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.del(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public String echo(String string) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(string);
            String result = jedis.echo(string);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long move(String key, int dbIndex) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.move(key, dbIndex);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long bitcount(String key) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.bitcount(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long bitcount(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.bitcount(key, start, end);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long bitpos(String key, boolean value) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.bitpos(key, value);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long bitpos(String key, boolean value, BitPosParams params) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.bitpos(key, value, params);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String key, int cursor) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            ScanResult<Map.Entry<String, String>> result = jedis.hscan(key, cursor);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public ScanResult<String> sscan(String key, int cursor) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            ScanResult<String> result = jedis.sscan(key, cursor);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public ScanResult<Tuple> zscan(String key, int cursor) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            ScanResult<Tuple> result = jedis.zscan(key, cursor);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            ScanResult<Map.Entry<String, String>> result = jedis.hscan(key, cursor);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, ScanParams params) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            ScanResult<Map.Entry<String, String>> result = jedis.hscan(key, cursor, params);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public ScanResult<String> sscan(String key, String cursor) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            ScanResult<String> result = jedis.sscan(key, cursor);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public ScanResult<String> sscan(String key, String cursor, ScanParams params) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            ScanResult<String> result = jedis.sscan(key, cursor, params);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            ScanResult<Tuple> result = jedis.zscan(key, cursor);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            ScanResult<Tuple> result = jedis.zscan(key, cursor, params);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long pfadd(String key, String... elements) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.pfadd(key, elements);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public long pfcount(String key) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.pfcount(key);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long geoadd(String key, double longitude, double latitude, String member) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.geoadd(key, longitude, latitude, member);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Long result = jedis.geoadd(key, memberCoordinateMap);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Double geodist(String key, String member1, String member2) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Double result = jedis.geodist(key, member1, member2);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Double geodist(String key, String member1, String member2, GeoUnit unit) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            Double result = jedis.geodist(key, member1, member2, unit);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public List<String> geohash(String key, String... members) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            List<String> result = jedis.geohash(key, members);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public List<GeoCoordinate> geopos(String key, String... members) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            List<GeoCoordinate> result = jedis.geopos(key, members);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            List<GeoRadiusResponse> result = jedis.georadius(key, longitude, latitude, radius, unit);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            List<GeoRadiusResponse> result = jedis.georadius(key, longitude, latitude, radius, unit, param);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            List<GeoRadiusResponse> result = jedis.georadiusByMember(key, member, radius, unit);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit, GeoRadiusParam param) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            List<GeoRadiusResponse> result = jedis.georadiusByMember(key, member, radius, unit, param);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public List<Long> bitfield(String key, String... arguments) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            List<Long> result = jedis.bitfield(key, arguments);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public void close() {

    }

    private Jedis shardJedis(String key) {
        JedisSentinelPool sentinelPool = getShard(key);
        Jedis jedis = sentinelPool.getResource();
        return jedis;
    }


    @Override
    public Object eval(String script, int keyCount, String... params) {
        if (keyCount != 1) {
            throw new UnsupportedOperationException("keyCount must be 1");
        }

        Jedis jedis = null;
        try {
            jedis = shardJedis(params[0]);
            Object result = jedis.eval(script, keyCount, params);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Object eval(String script, List<String> keys, List<String> args) {
        if (keys.size() != 1) {
            throw new UnsupportedOperationException("keys.size must be 1");
        }

        Jedis jedis = null;
        try {
            jedis = shardJedis(keys.get(0));
            Object result = jedis.eval(script, keys, args);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Object eval(String script) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object evalsha(String script) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object evalsha(String sha1, List<String> keys, List<String> args) {
        if (keys.size() != 1) {
            throw new UnsupportedOperationException("keys.size must be 1");
        }

        Jedis jedis = null;
        try {
            jedis = shardJedis(keys.get(0));
            Object result = jedis.eval(sha1, keys, args);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Object evalsha(String sha1, int keyCount, String... params) {
        if (keyCount != 1) {
            throw new UnsupportedOperationException("keyCount must be 1");
        }

        Jedis jedis = null;
        try {
            jedis = shardJedis(params[0]);
            Object result = jedis.eval(sha1, keyCount, params);
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public Boolean scriptExists(String sha1) {
        for (JedisSentinelPool jedisSentinelPool : getAllShards()) {
            Jedis jedis = null;
            try {
                jedis = jedisSentinelPool.getResource();
                if (!jedis.scriptExists(sha1)) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }

        }
        return true;
    }

    @Override
    public List<Boolean> scriptExists(String... sha1) {
        throw new IllegalArgumentException();
    }

    @Override
    public String scriptLoad(String script) {
        Collection<JedisSentinelPool> jedisSentinelPoolList = getAllShards();
        List<String> results = new ArrayList<String>();
        for (JedisSentinelPool jedisSentinelPool : jedisSentinelPoolList) {
            Jedis jedis = null;
            try {
                jedis = jedisSentinelPool.getResource();
                results.add(jedis.scriptLoad(script));
            } finally {
                if (jedis != null) {
                    if (jedis != null) {
                        jedis.close();
                    }
                }
            }

        }

        if (results.size() > 0 && results.size() == jedisSentinelPoolList.size()) {
            return results.get(0);
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * 获取key所在的机器当前的时间
     * @param key
     * @return
     */
    public List<String> time(String key) {
        Jedis jedis = null;
        try {
            jedis = shardJedis(key);
            List<String> result = jedis.time();
            return result;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
