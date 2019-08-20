/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.demo.pojo;

import java.util.List;

import com.alipay.api.response.AlipayTradeQueryResponse;

/**
 * @author alipay.demo
 *
 */
public class UserPayListResponse extends SuccessResponse {

    private List<AlipayTradeQueryResponse> alipayTradeQueryList;

    public List<AlipayTradeQueryResponse> getAlipayTradeQueryList() {
        return alipayTradeQueryList;
    }

    public void setAlipayTradeQueryList(List<AlipayTradeQueryResponse> alipayTradeQueryList) {
        this.alipayTradeQueryList = alipayTradeQueryList;
    }

}