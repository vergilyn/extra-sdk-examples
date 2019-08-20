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
import com.alipay.api.request.AlipayPassTemplateAddRequest;
import com.alipay.api.response.AlipayPassTemplateAddResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author alipay.demo
 *
 */
@Component
public class AlipayPassTemplateAddControllerImpl implements AlipayPassTemplateAddController{
    private static final Logger logger = LoggerFactory.getLogger(AlipayPassTemplateAddControllerImpl.class);

    @Autowired
    private AlipayClient alipayClient;

    @Override
    public Object alipayPassTemplateAdd(@Context HttpServletRequest request){
        String tpl_content = "{\n"
                + "\t\"logo\": \"https://alipass.alipay.com//temps/free/logo.png\",\n"
                + "\t\"strip\": \"https://alipass.alipay.com//temps/free/strip.png\",\n"
                + "\t\"icon\": \"http://alipassprod.test.alipay.net/temps/free/icon.png\",\n"
                + "\t\"content\": {\n"
                + "\t\t\"evoucherInfo\": {\n"
                + "\t\t\t\"title\": \"优惠券\",\n"
                + "\t\t\t\"type\": \"boardingPass\",\n"
                + "\t\t\t\"product\": \"free\",\n"
                + "\t\t\t\"startDate\": \"2019-03-06 15:05:22\",\n"
                + "\t\t\t\"endDate\": \"2029-12-31 23:59:59\",\n"
                + "\t\t\t\"operation\": [{\n"
                + "\n"
                + "\t\t\t\t\"message\": \"$ackCode$\",\n"
                + "\n"
                + "\t\t\t\t\"messageEncoding\": \"UTF-8\",\n"
                + "\n"
                + "\t\t\t\t\"format\": \"barcode\",\n"
                + "\n"
                + "\t\t\t\t\"altText\": \"$ackCode$\"\n"
                + "\n"
                + "\t\t\t}],\n"
                + "\t\t\t\"einfo\": {\n"
                + "\t\t\t\t\"logoText\": \"优惠券\",\n"
                + "\t\t\t\t\"headFields\": [{\n"
                + "\t\t\t\t\t\"key\": \"status\",\n"
                + "\t\t\t\t\t\"label\": \"状态\",\n"
                + "\t\t\t\t\t\"value\": \"可使用\",\n"
                + "\t\t\t\t\t\"type\": \"text\"\n"
                + "\t\t\t\t}],\n"
                + "\t\t\t\t\"primaryFields\": [{\n"
                + "\t\t\t\t\t\"key\": \"strip\",\n"
                + "\t\t\t\t\t\"label\": \"\",\n"
                + "\t\t\t\t\t\"value\": \"凭此券即可抵扣1元\",\n"
                + "\t\t\t\t\t\"type\": \"text\"\n"
                + "\t\t\t\t}],\n"
                + "\t\t\t\t\"secondaryFields\": [{\n"
                + "\t\t\t\t\t\"key\": \"validDate\",\n"
                + "\t\t\t\t\t\"label\": \"有效期至：\",\n"
                + "\t\t\t\t\t\"value\": \"2029-12-31 23:59:59\",\n"
                + "\t\t\t\t\t\"type\": \"text\"\n"
                + "\t\t\t\t}],\n"
                + "\t\t\t\t\"auxiliaryFields\": [{\n"
                + "\t\t\t\t\t\"key\": \"key_1\",\n"
                + "\t\t\t\t\t\"label\": \"\",\n"
                + "\t\t\t\t\t\"value\": \"卡券Demo演示\",\n"
                + "\t\t\t\t\t\"type\": \"text\"\n"
                + "\t\t\t\t}],\n"
                + "\t\t\t\t\"backFields\": [{\n"
                + "\t\t\t\t\t\"key\": \"description\",\n"
                + "\t\t\t\t\t\"label\": \"详情描述\",\n"
                + "\t\t\t\t\t\"value\": \"1.该优惠有效期：截止至2029年12月31日；\\n2.凭此券可以享受以下优惠：\\n享门抵扣1元\\n不与其他优惠同享。详询商家。\",\n"
                + "\t\t\t\t\t\"type\": \"text\"\n"
                + "\t\t\t\t}]\n"
                + "\t\t\t},\n"
                + "\t\t\t\"locations\": [],\n"
                + "\t\t\t\"remindInfo\": {\n"
                + "\t\t\t\t\"offset\": \"2\"\n"
                + "\t\t\t}\n"
                + "\t\t},\n"
                + "\t\t\"merchant\": {\n"
                + "\t\t\t\"mname\": \"君泓测试\",\n"
                + "\t\t\t\"mtel\": \"\",\n"
                + "\t\t\t\"minfo\": \"\"\n"
                + "\t\t},\n"
                + "\t\t\"platform\": {\n"
                + "\t\t\t\"channelID\": \"2088201564809153\",\n"
                + "\t\t\t\"webServiceUrl\": \"https://alipass.alipay.com/builder/syncRecord.htm?tempId=2019030622051381011176571\"\n"
                + "\t\t},\n"
                + "\t\t\"style\": {\n"
                + "\t\t\t\"backgroundColor\": \"RGB(255,126,0)\"\n"
                + "\t\t},\n"
                + "\t\t\"fileInfo\": {\n"
                + "\t\t\t\"formatVersion\": \"2\",\n"
                + "\t\t\t\"canShare\": true,\n"
                + "\t\t\t\"canBuy\": false,\n"
                + "\t\t\t\"canPresent\": false,\n"
                + "\t\t\t\"serialNumber\": \"$serialNumber$\",\n"
                + "\t\t\t\"supportTaxi\": \"false\",\n"
                + "\t\t\t\"taxiSchemaUrl\": \"alipays://platformapi/startapp?appId=20000778&bizid=260&channel=71322\"\n"
                + "\t\t},\n"
                + "\t\t\"appInfo\": {\n"
                + "\t\t\t\"app\": {\n"
                + "\t\t\t\t\"android_appid\": \"com.taobao.ecoupon\",\n"
                + "\t\t\t\t\"ios_appid\": \"taobaolife://\",\n"
                + "\t\t\t\t\"android_launch\": \"com.taobao.ecoupon\",\n"
                + "\t\t\t\t\"ios_launch\": \"taobaolife://\",\n"
                + "\t\t\t\t\"android_download\": \"http://download.taobaocdn.com/freedom/17988/andriod/Ecoupon_2.0.1_taobao_wap.apk\",\n"
                + "\t\t\t\t\"ios_download\": \"https://itunes.apple.com/cn/app/id583295537\"\n"
                + "\t\t\t},\n"
                + "\t\t\t\"label\": \"券券APP\",\n"
                + "\t\t\t\"message\": \"点击调起APP\"\n"
                + "\t\t},\n"
                + "\t\t\"source\": \"alipassprod\",\n"
                + "\t\t\"alipayVerify\": [\"qrcode\", \"barcode\", \"text\", \"wave\"]\n"
                + "\t}\n"
                + "}";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("unique_id", "20190314122901");
        params.put("tpl_content", tpl_content);
        Object obj = JSONObject.toJSON(params);
        AlipayPassTemplateAddRequest alipayRequest = new AlipayPassTemplateAddRequest();
        alipayRequest.setBizContent(obj.toString());
        try {
            AlipayPassTemplateAddResponse alipayResponse = alipayClient.execute(alipayRequest);
            if(alipayResponse.isSuccess()){
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