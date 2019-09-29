package com.vergilyn.examples;

import com.alibaba.fastjson.JSON;
import com.binarywang.spring.starter.wxjava.pay.properties.WxPayProperties;
import com.github.binarywang.wxpay.bean.entpay.EntPayRequest;
import com.github.binarywang.wxpay.bean.entpay.EntPayResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.EntPayService;
import com.github.binarywang.wxpay.service.WxPayService;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * @author VergiLyn
 * @date 2019-09-17
 */
public class WxPayServiceTest extends AbstractBaseTest{
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private WxPayProperties properties;

    /**
     * <a href="https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2">企业付款</a>
     */
    @Test
    public void entPay(){
        EntPayService entPayService = wxPayService.getEntPayService();

        EntPayRequest request = new EntPayRequest();

        // 商户订单号，需保持唯一性
        //(只能是字母或者数字，不能包含有其他字符)
        request.setPartnerTradeNo("409839163a");

        // 商户appid下，某用户的openid
        // 唐x o6IMPxFDUeJyensa-aUFNp8gP3Yk
        // 向x o6IMPxNoeE0uEIOD8KOr65GCrbhQ
        request.setOpenid("o6IMPxFDUeJyensa-aUFNp8gP3Yk");

        // (是)校验用户姓名选项
        // NO_CHECK：不校验真实姓名
        // FORCE_CHECK：强校验真实姓名
        request.setCheckName("FORCE_CHECK");

        // (否)收款用户真实姓名。
        // 如果check_name设置为FORCE_CHECK，则必填用户真实姓名
        request.setReUserName("真实姓名");

        // (是)企业付款金额，单位为分
        request.setAmount(100);

        // (是)企业付款备注
        request.setDescription("测试微信提现（姓名强校验）");

        // (是)该IP同在商户平台设置的IP白名单中的IP没有关联，该IP可传用户端或者服务端的IP。
        // request.setSpbillCreateIp("127.0.0.1");

        try {
            EntPayResult result = entPayService.entPay(request);
            System.out.println(JSON.toJSONString(result));
        } catch (WxPayException e) {
            logger.error("微信企业付款异常", e);
        }
    }
}
