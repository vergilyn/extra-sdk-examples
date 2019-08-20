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
package com.alipay.demo.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


/**
 * @author alipay.demo
 *
 */
@Component
public class DefaultAlipayClientFactory implements FactoryBean<AlipayClient>, InitializingBean{
	private static final Logger   logger = LoggerFactory.getLogger(DefaultAlipayClientFactory.class);

	private AlipayClient alipayClient = null;

	@Autowired
	protected Environment config;

	@Override
	public AlipayClient getObject() throws Exception {
		return alipayClient;
	}

	@Override
	public Class<?> getObjectType() {
		return AlipayClient.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// 请求方式 json
		String FORMAT = "json";
		// 编码格式，目前只支持UTF-8
		String CHARSET = "UTF-8";
		// 签名方式
		String SIGN_TYPE = "RSA2";
		// 网关
		String URL = config.getProperty("alipay.appconfig.gateway");
		// 商户APP_ID
		String APP_ID = config.getProperty("alipay.appconfig.appid");
		// 商户RSA 私钥
		String APP_PRIVATE_KEY = config.getProperty("alipay.appconfig.privatekey");
		// 支付宝公钥
		String ALIPAY_PUBLIC_KEY = config.getProperty("alipay.appconfig.publickey");

		alipayClient = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);

		logger.info("创建支付宝网关访问客户端完成, 网关地址：{}，appId:{}", URL, APP_ID);
	}
}