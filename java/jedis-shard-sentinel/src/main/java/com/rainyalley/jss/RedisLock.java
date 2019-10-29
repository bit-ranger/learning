package com.rainyalley.jss;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RedisLock implements Lock {

    private String prefix = "lock";

    private String delimiter = ":";

    private ShardedSentinelJedisPool pool;

    private long lockExpireTime;

    private Random random = new Random();

    public RedisLock(String prefix, String delimiter, ShardedSentinelJedisPool pool) {
        this.prefix = prefix;
        this.delimiter = delimiter;
        this.pool = pool;
    }

    @Override
    public boolean hasLock(String entityId, double time) {
        ShardedSentinelJedis client = null;
        try {
            client = pool.getResource();
            String value = client.get(getKey(entityId));
            return String.valueOf(time).equals(value);
        } catch (Exception e) {
            return false;
        } finally {
            if(client != null){
                client.close();
            }
        }

    }

    @Override
    public boolean tryLock(String entityId, double time) {
        ShardedSentinelJedis client = null;
        try {
            client = pool.getResource();
            String key = getKey(entityId);
            String result = client.set(key, String.valueOf(time), "nx", null, lockExpireTime);

            if("OK".equals(result)){
                return true;
            }

            String lockTime = client.get(key);

            //空，代表锁已经被删，应当重新setNX,然而setNX后get返回nil的情况依旧存在，循环处理的逻辑放到lock()方法中处理
            //此处直接认为获取锁失败
            if(lockTime == null){
                return false;
            }

            //未超时
            double currTime = time(entityId);
            if(currTime - Double.parseDouble(lockTime) < lockExpireTime){
                return false;
            }

            String lastLockTime = client.getSet(key, String.valueOf(currTime));
            if(lastLockTime == null){
                return true;
            } else if(lastLockTime.equals(lockTime)){
                return true;
            } else {
                return false;
            }


        } catch (Exception e) {
            return false;
        } finally {
            if(client != null){
                client.close();
            }
        }
    }

    @Override
    public boolean lock(String entityId, double time, long timeout) {
        long beginTime = System.currentTimeMillis();

        for (;;) {
            boolean trySuccess = tryLock(entityId, time);
            if(trySuccess){
                return true;
            }

            if(System.currentTimeMillis() - beginTime >= timeout){
                return false;
            }

            //20毫秒~50毫秒之间随机睡眠，错开并发
            try {
                Thread.sleep(random.nextInt(30) + 20);
            } catch (InterruptedException e) {
                //
            }
        }
    }

    @Override
    public boolean unLock(String entityId, double time) {
        ShardedSentinelJedis client = null;
        try {
            client = pool.getResource();
            String key = getKey(entityId);
            Object result = client.eval(
                    "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                    "    return redis.call(\"del\",KEYS[1])\n" +
                    "else\n" +
                    "    return 0\n" +
                    "end"
            , Arrays.asList(key), Arrays.asList(String.valueOf(time)));

            return Long.valueOf(result.toString()).compareTo(0L) > 0;
//            String lockTime = client.get(key);
//
//            //空，代表锁不存在
//            if(lockTime == null){
//                return true;
//            }
//
//            //锁已经不属于该线程
//            if(!String.valueOf(time).equals(lockTime)){
//                return true;
//            }
//
//            //锁已超时，此时的锁会被其他线程竞争，不可删除
//            //20是用提前量，避免出现临界情况
//            double currTime = time(key);
//            if(currTime - Double.valueOf(lockTime) + 20> lockExpireTime){
//                return true;
//            }
//
//            //此处存在临界条件，如果上一行中的currTime即将到达超时时间，然后线程在此处到达了超时时间，
//            //如果此时线程A竞争得到锁，删除了Key之后，会使线程B得到锁
//            client.del(key);
//            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if(client != null){
                client.close();
            }
        }
    }

    public double time(String entityId){
        ShardedSentinelJedis client = pool.getResource();
        List<String> rl =  client.time(getKey(entityId));
        double t = Double.parseDouble(rl.get(0) + "." + rl.get(1));
        client.close();
        return t;
    }

    private String getKey(String entityId){
        return prefix + delimiter + entityId;
    }


}
