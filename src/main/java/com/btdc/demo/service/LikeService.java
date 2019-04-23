package com.btdc.demo.service;

public interface LikeService {

    Integer getLikeStatus(Integer userId, Integer entityType, Integer entityId);

    long like(Integer userId, Integer entityType, Integer entityId);

    long disLike(Integer userId, Integer entityType, Integer entityId);
}
