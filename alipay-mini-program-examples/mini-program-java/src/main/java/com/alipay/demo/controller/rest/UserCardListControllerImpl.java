/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.demo.controller.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.alipay.api.AlipayClient;
import com.alipay.demo.pojo.UserCard;
import com.alipay.demo.pojo.UserCardListResponse;
import com.alipay.demo.service.UserCardService;

import org.jboss.resteasy.spi.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author alipay.demo
 *
 */
@Component
public class UserCardListControllerImpl implements UserCardListController{
    private static final Logger logger = LoggerFactory.getLogger(UserPayListControllerImpl.class);

    @Autowired
    private UserCardService userCardService;

    @Autowired
    private AlipayClient alipayClient;

    @Override
    public UserCardListResponse userCard(String user_id, HttpResponse response) throws Exception {
        UserCardListResponse res = new UserCardListResponse();
        res.setSuccess(false);
        if (user_id == null || user_id.equals("")) {
            res.setSuccess(false);
        }else {
            res.setSuccess(true);
            List<UserCard> userCardList = new ArrayList<>();
            Iterable<UserCard> userCards = userCardService.getByUserId(user_id);
            Iterator it = userCards.iterator();
            while (it.hasNext()){
                UserCard userCard = (UserCard)it.next();
                userCardList.add(userCard);
            }
            res.setUserCardList(userCardList);
        }
        return res;
    }

}