/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.demo.dal;

import com.alipay.demo.pojo.UserCard;

import org.springframework.data.repository.CrudRepository;

/**
 * @author alipay.demo
 *
 */
public interface UserCardRepository extends CrudRepository<UserCard, Long> {
    Iterable<UserCard> findByUserId(String userId);

    Iterable<UserCard> findByCardId(String cardId);
}