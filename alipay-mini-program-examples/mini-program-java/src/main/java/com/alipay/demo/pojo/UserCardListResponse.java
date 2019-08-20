/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.demo.pojo;

import java.util.List;

/**
 * @author alipay.demo
 *
 */
public class UserCardListResponse  extends SuccessResponse {

    private List<UserCard> userCardList;

    public List<UserCard> getUserCardList() {
        return userCardList;
    }

    public void setUserCardList(List<UserCard> userCardList) {
        this.userCardList = userCardList;
    }

}