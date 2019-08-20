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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.demo.pojo.UserPay;
import com.alipay.demo.service.UserPayService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author alipay.demo
 *
 */
@Component
public class AlipayTradeCreateControllerImpl implements AlipayTradeCreateController{
    private static final Logger logger = LoggerFactory.getLogger(AlipayTradeCreateControllerImpl.class);

    @Autowired
    private AlipayClient   alipayClient;
    @Autowired
    private UserPayService userPayService;

    @Override
    public Object alipayTradeCreate(@Context HttpServletRequest request){
        String out_trade_no = request.getParameter("out_trade_no");
        String subject = request.getParameter("subject");
        String total_amount = request.getParameter("total_amount");
        String buyer_id = request.getParameter("buyer_id");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("out_trade_no", out_trade_no);
        params.put("buyer_id", buyer_id);
        params.put("subject", subject);
        params.put("total_amount", total_amount);
        Object obj = JSONObject.toJSON(params);
        AlipayTradeCreateRequest alipayRequest = new AlipayTradeCreateRequest();
        alipayRequest.setBizContent(obj.toString());
        try {
            AlipayTradeCreateResponse alipayResponse = alipayClient.execute(alipayRequest);

            String trade_no = alipayResponse.getTradeNo();
            UserPay userPay = new UserPay();
            userPay.setTrade_no(trade_no);
            userPay.setUserId(buyer_id);
            userPayService.insert(userPay);

            return alipayResponse;
        }catch (AlipayApiException e) {
            if(e.getCause() instanceof java.security.spec.InvalidKeySpecException){
                logger.error("商户私钥格式不正确，请确认application.properties文件中是否配置正确");
            }
            return "调用SDK失败";
        }
    }
}