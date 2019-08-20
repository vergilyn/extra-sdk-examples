/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.demo.service;

import com.alipay.demo.pojo.UserCard;

/**
 * @author alipay.demo
 *
 */
public interface UserCardService {
    void insert(UserCard userCard);
    void update(UserCard userCard);
    Iterable<UserCard> getByUserId(String userId);
    Iterable<UserCard> getByCardId(String card_id);
}