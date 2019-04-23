package com.btdc.demo.util;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author bockey
 */
@Service
public class LocalQueue {
    private static final ConcurrentHashMap<String, BlockingDeque<String>> que = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, HashSet<String>> set = new ConcurrentHashMap<>();

    public LocalQueue() {
        BlockingDeque<String> b = new LinkedBlockingDeque<>();
        que.put(QueueKeyUtil.getEventQueueKey(), b);
    }

    public static LocalQueue getQueue() {
        return new LocalQueue();
    }


    public Long sadd(String key, String value) {
        HashSet set1 = set.get(key);
        if (set1 != null) {
            set1.add(value);
            return 1l;
        } else {
            return null;
        }
    }

    public Long srem(String key, String value) {
        HashSet set1 = set.get(key);
        if (set1 != null) {
            set1.remove(value);
            return 1l;
        } else {
            return null;
        }
    }

    public boolean sismember(String key, String value) {
        HashSet set1 = set.get(key);
        if (set1 != null) {
            return set1.contains(value);
        } else {
            return false;
        }
    }

    public Long scard(String key) {
        HashSet set1 = set.get(key);
        if (set1 != null) {
            int size = set1.size();
            return Long.valueOf(size);
        } else {
            return null;
        }
    }


    public Long lpush(String key, String value) {
        System.out.println(" l push");
        BlockingDeque<String> q = que.get(key);
        if (q != null) {
            q.addLast(value);
            return 1l;
        } else {
            BlockingDeque<String> q2 = new LinkedBlockingDeque<>();
            q2.addLast(value);
            que.put(key, q2);
            System.out.println("put que event");

            return 1l;
        }
    }

    public List<String> brpop(int timeout, String key) {
        ArrayList<String> arr = new ArrayList<>();
//        arr.add(key);
        BlockingDeque<String> q = que.get(key);
        System.out.println(que);
        System.out.println(key);
        if (q != null) {
            String s = null;
            try {
                s = q.pollFirst(timeout, TimeUnit.SECONDS);
                arr.add(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return arr;
        } else {
            return arr;
        }
    }


}
