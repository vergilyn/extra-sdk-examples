package com.vergilyn.examples.alibaba.dingtalk;

import com.vergilyn.examples.alibaba.dingtalk.config.AbstractDingtalkProperties;
import com.vergilyn.examples.alibaba.dingtalk.config.VergilynDingtalkProperties;
import com.vergilyn.examples.alibaba.dingtalk.utils.DingCallbackCrypto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DingtalkApplication {
	/**
	 * FIXME 2020-11-24 实现相应的{@linkplain AbstractDingtalkProperties}
	 */
	public static final AbstractDingtalkProperties DINGTALK_PROPERTIES = new VergilynDingtalkProperties();

	@Bean
	public DingCallbackCrypto dingCallbackCrypto() throws DingCallbackCrypto.DingTalkEncryptException {
		return new DingCallbackCrypto(DINGTALK_PROPERTIES.getToken(), DINGTALK_PROPERTIES.getAesKey(), DINGTALK_PROPERTIES.getCorpId());
	}

	public static void main(String[] args) {
		SpringApplication.run(DingtalkApplication.class);
	}
}
