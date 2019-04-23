package com.btdc.demo.service.impl;

import com.btdc.demo.service.LikeService;
import com.btdc.demo.util.LocalQueue;
import com.btdc.demo.util.QueueKeyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class LikeServiceImpl implements LikeService {
    //    @Autowired
//    private JedisAdapter jedisAdapter;
    @Resource
    private LocalQueue localQueue;

    @Override
    public Integer getLikeStatus(Integer userId, Integer entityType, Integer entityId) {
        String likeKey = QueueKeyUtil.getLikeKey(entityId, entityType);
        if (localQueue.sismember(likeKey, String.valueOf(userId))) {
            return 1;
        }
        String disLikeKey = QueueKeyUtil.getDisLikeKey(entityId, entityType);
        return localQueue.sismember(disLikeKey, String.valueOf(userId)) ? -1 : 0;
    }

    @Override
    public long like(Integer userId, Integer entityType, Integer entityId) {
        //在喜欢集合里增加
        String likeKey = QueueKeyUtil.getLikeKey(entityId, entityType);
        localQueue.sadd(likeKey, String.valueOf(userId));
        System.out.println("service like " + likeKey + " " + userId);
        //从反对里删除
        String disLikeKey = QueueKeyUtil.getDisLikeKey(entityId, entityType);
        localQueue.srem(disLikeKey, String.valueOf(userId));

        System.out.println("service like " + likeKey + " " + localQueue.scard(likeKey));
        return localQueue.scard(likeKey);
    }

    @Override
    public long disLike(Integer userId, Integer entityType, Integer entityId) {
        //在反对集合里增加
        String disLikeKey = QueueKeyUtil.getDisLikeKey(entityId, entityType);
        localQueue.sadd(disLikeKey, String.valueOf(userId));

        //从喜欢里删除
        String likeKey = QueueKeyUtil.getLikeKey(entityId, entityType);
        localQueue.srem(likeKey, String.valueOf(userId));
        return localQueue.scard(likeKey);
    }
}
