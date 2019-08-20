/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.demo.controller.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayPassInstanceUpdateRequest;
import com.alipay.api.response.AlipayPassInstanceUpdateResponse;
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
public class AlipayPassInstanceUpdateControllerImpl implements AlipayPassInstanceUpdateController{
    private static final Logger logger = LoggerFactory.getLogger(AlipayPassInstanceUpdateControllerImpl.class);

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private UserCardService userCardService;

    @Override
    public Object alipayPassInstanceUpdate(@Context HttpServletRequest request){

        String verify_code = request.getParameter("serial_number");
        String card_id = request.getParameter("card_id");
        Map<String, Object> tpl_params = new HashMap<String, Object>();
        tpl_params.put("ackCode", verify_code);
        tpl_params.put("serialNumber", verify_code);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("serial_number", verify_code);
        params.put("channel_id", "2088201564809153");
        params.put("tpl_params", tpl_params);
        params.put("status", "USED");
        params.put("ackCode", verify_code);
        params.put("verify_code", verify_code);
        params.put("verify_type", "barcode");
        Object obj = JSONObject.toJSON(params);
        AlipayPassInstanceUpdateRequest alipayRequest = new AlipayPassInstanceUpdateRequest();
        alipayRequest.setBizContent(obj.toString());
        try {
            AlipayPassInstanceUpdateResponse alipayResponse = alipayClient.execute(alipayRequest);
            //更新用户领取卡券信息，用于用户卡券列表展示
            Iterable<UserCard>  userCards = userCardService.getByCardId(card_id);
            UserCard userCard = userCards.iterator().next();
            userCard.setCardStatus("USED");
            userCardService.update(userCard);
            if(alipayResponse.isSuccess()){
                alipayResponse.setSuccess("true");
                logger.info("调用成功");
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