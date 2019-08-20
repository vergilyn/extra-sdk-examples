/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.demo.controller.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayPassInstanceAddRequest;
import com.alipay.api.response.AlipayPassInstanceAddResponse;
import com.alipay.demo.pojo.UserCard;
import com.alipay.demo.service.UserCardService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author alipay.demo
 *
 */
@Component
public class AlipayPassInstanceAddControllerImpl implements AlipayPassInstanceAddController{
    private static final Logger logger = LoggerFactory.getLogger(AlipayPassInstanceAddControllerImpl.class);

    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private UserCardService userCardService;

    @Override
    public Object alipayPassInstanceAdd(@Context HttpServletRequest request){
        String user_id = request.getParameter("user_id");
        String out_trade_no = request.getParameter("out_trade_no");
        long time = System.currentTimeMillis();
        String ackCode = String.valueOf(time);
        Map<String, Object> tpl_params = new HashMap<String, Object>();
        tpl_params.put("ackCode", ackCode);
        tpl_params.put("serialNumber", ackCode);
        Map<String, Object> recognition_info = new HashMap<String, Object>();
        recognition_info.put("partner_id", "2088431641083682");
        recognition_info.put("out_trade_no", out_trade_no);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tpl_id", "2019031416312511170360733");
        params.put("tpl_params", tpl_params);
        params.put("recognition_type", "1");
        params.put("recognition_info", recognition_info);
        Object obj = JSONObject.toJSON(params);
        AlipayPassInstanceAddRequest alipayRequest = new AlipayPassInstanceAddRequest();
        alipayRequest.setBizContent(obj.toString());
        try {
            AlipayPassInstanceAddResponse alipayResponse = alipayClient.execute(alipayRequest);
            if(alipayResponse.isSuccess()){
                alipayResponse.setSuccess("true");
                String result = alipayResponse.getResult();
                JSONObject jsonObject = JSON.parseObject(result);
                String card_id = jsonObject.get("passId").toString();
                logger.info("调用成功");
                //将用户领取卡券信息入库，用于用户卡券列表展示
                UserCard userCard = new UserCard();
                userCard.setCardId(card_id);
                userCard.setUserId(user_id);
                userCard.setCardStatus("OK");
                userCard.setSerialNumber(ackCode);
                userCardService.insert(userCard);
            } else {
                logger.info("调用失败");
            }
            return alipayResponse;
        }catch (AlipayApiException e) {
            if(e.getCause() instanceof java.security.spec.InvalidKeySpecException){
                logger.error("商户私钥格式不正确，请确认application.properties文件中是否配置正确");
            }
            return "调用SDK失败";
        }
    }
}