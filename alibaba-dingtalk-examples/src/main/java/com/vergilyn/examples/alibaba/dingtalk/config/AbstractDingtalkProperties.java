package com.vergilyn.examples.alibaba.dingtalk.config;

import com.vergilyn.examples.alibaba.dingtalk.utils.DingCallbackCrypto;

public interface AbstractDingtalkProperties {
	/**
	 * 钉钉根部门ID
	 */
	Long getTopDeptId();

	String getDingtalkUserId();

	/**
	 * 接收事件回调的url，必须是公网可以访问的url地址。
	 */
	String getCallbackDomain();

	/**
	 * 企业dingtalk的corpid
	 *
	 * @see <a href="https://open-dev.dingtalk.com/">钉钉开放平台</a>
	 */
	String getCorpId();

	/**
	 * 数据加密密钥。用于回调数据的加密，长度固定为43个字符，从a-z, A-Z, 0-9共62个字符中选取。
	 * 您可以随机生成，ISV(服务提供商)推荐使用注册套件时填写的EncodingAESKey
	 *
	 * @see DingCallbackCrypto.Utils#getRandomStr(int)
	 */
	String getAesKey();
	/**
	 * 加解密需要用到的token，ISV(服务提供商)推荐使用注册套件时填写的token，普通企业可以随机填写
	 */
	String getToken();

	//region https://ding-doc.dingtalk.com/doc#/serverapi2/eev437
	Long getAgentId();
	String getAccessKey();
	String getAccessSecret();
	//endregion
}
