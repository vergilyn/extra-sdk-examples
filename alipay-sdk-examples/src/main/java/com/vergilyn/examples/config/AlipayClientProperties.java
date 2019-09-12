package com.vergilyn.examples.config;

import com.alipay.api.AlipayConstants;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <a href="https://docs.open.alipay.com/api_28/alipay.fund.trans.toaccount.transfer">部分公共参数参考</a>
 * @author VergiLyn
 * @date 2019-09-12
 * @see com.alipay.api.DefaultAlipayClient
 * @see AlipayConstants
 */
@ConfigurationProperties(prefix = "alipay.client")
@Data
public class AlipayClientProperties {
    private String serverUrl = "https://openapi.alipay.com/gateway.do";

    /**
     * 仅支持JSON
     */
    private String format = AlipayConstants.FORMAT_JSON;

    /**
     * 请求使用的编码格式，如utf-8,gbk,gb2312等
     */
    private String charset = "utf-8";

    /**
     * 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
     */
    private String signType = AlipayConstants.SIGN_TYPE_RSA2;

    private String appId;
    private String privateKey;
    private String alipayPublicKey;
}
