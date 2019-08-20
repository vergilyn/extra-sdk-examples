/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.demo.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.demo.pojo.UserPay;
import com.alipay.demo.pojo.UserPayListResponse;
import com.alipay.demo.service.UserPayService;

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
public class UserPayListControllerImpl implements UserPayListController{
    private static final Logger logger = LoggerFactory.getLogger(UserPayListControllerImpl.class);

    @Autowired
    private UserPayService userPayService;

    @Autowired
    private AlipayClient   alipayClient;

    @Override
    public UserPayListResponse userPay(String userId, HttpResponse response) throws Exception {
        UserPayListResponse res = new UserPayListResponse();
        res.setSuccess(false);
        if (userId == null || userId.equals("")) {
            res.setSuccess(false);
        }else {
            res.setSuccess(true);
            List<AlipayTradeQueryResponse> alipayTradeQueryResponseList = new ArrayList<>();
            Iterable<UserPay> userPays = userPayService.getByUserId(userId);
            Iterator it = userPays.iterator();
            while (it.hasNext()){
                UserPay userPay = (UserPay)it.next();
                String trade_no = userPay.getTrade_no();
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("trade_no", trade_no);
                Object obj = JSONObject.toJSON(params);
                AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
                alipayRequest.setBizContent(obj.toString());
                try {
                    AlipayTradeQueryResponse alipayResponse = alipayClient.execute(alipayRequest);
                    alipayTradeQueryResponseList.add(alipayResponse);
                }catch (AlipayApiException e) {
                    if(e.getCause() instanceof java.security.spec.InvalidKeySpecException){
                        logger.error("商户私钥格式不正确，请确application.properties文件中是否配置正确");
                    }
                }
            }
            res.setAlipayTradeQueryList(alipayTradeQueryResponseList);
        }
        return res;
    }
}