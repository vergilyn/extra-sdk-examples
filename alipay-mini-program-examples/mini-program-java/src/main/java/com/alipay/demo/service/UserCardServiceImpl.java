/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.demo.service;

import com.alipay.demo.dal.UserCardRepository;
import com.alipay.demo.pojo.UserCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author alipay.demo
 *
 */
@Service
public class UserCardServiceImpl implements UserCardService{

    @Autowired
    private UserCardRepository userCardRepository;

    @Override
    public void insert(UserCard userCard) {
        userCardRepository.save(userCard);
    }

    @Override
    public void update(UserCard userCard) {
        userCardRepository.save(userCard);
    }

    @Override
    public Iterable<UserCard> getByUserId(String userId){
        return userCardRepository.findByUserId(userId);
    }

    @Override
    public Iterable<UserCard> getByCardId(String userId){
        return userCardRepository.findByCardId(userId);
    }
}