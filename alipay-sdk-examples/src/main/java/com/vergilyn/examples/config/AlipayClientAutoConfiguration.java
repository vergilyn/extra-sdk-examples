package com.vergilyn.examples.config;

import com.alipay.api.AbstractAlipayClient;
import com.alipay.api.DefaultAlipayClient;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author VergiLyn
 * @date 2019-09-12
 */
@Configuration
@EnableConfigurationProperties(AlipayClientProperties.class)
public class AlipayClientAutoConfiguration {

    @Bean
    public AbstractAlipayClient alipayClient(AlipayClientProperties properties){
        DefaultAlipayClient alipayClient = new DefaultAlipayClient(properties.getServerUrl(),
                properties.getAppId(),
                properties.getPrivateKey(),
                properties.getFormat(),
                properties.getCharset(),
                properties.getAlipayPublicKey(),
                properties.getSignType());

        return alipayClient;
    }
}
