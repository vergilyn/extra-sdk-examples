package com.vergilyn.examples.alibaba.dingtalk.config;

import com.vergilyn.examples.alibaba.dingtalk.utils.DingCallbackCrypto;

/**
 * @see <a href="https://ding-doc.dingtalk.com/doc#/serverapi2/tvu5f4">dingtalk配置获取</a>
 */
public interface AbstractDingtalkProperties {
	String DINGTALK_OAPI_DOMAIN = "https://oapi.dingtalk.com";

	String dingtalkOapiHost();

	/**
	 * 钉钉根部门ID
	 */
	Long topDeptId();

	String moblie();
	String moblieOther();

	String dingtalkUserId();
	String dingtalkUserIdOther();

	String mediaId();

	/**
	 * 接收事件回调的url，必须是公网可以访问的url地址。
	 */
	String callbackDomain();

	/**
	 * 企业dingtalk的corpid
	 *
	 * @see <a href="https://open-dev.dingtalk.com/">钉钉开放平台</a>
	 */
	String corpId();

	/**
	 * 数据加密密钥。用于回调数据的加密，长度固定为43个字符，从a-z, A-Z, 0-9共62个字符中选取。
	 * 您可以随机生成，ISV(服务提供商)推荐使用注册套件时填写的EncodingAESKey
	 *
	 * @see DingCallbackCrypto.Utils#getRandomStr(int)
	 */
	String aesKey();

	/**
	 * 加解密需要用到的token，ISV(服务提供商)推荐使用注册套件时填写的token，普通企业可以随机填写
	 */
	String token();

	//region https://ding-doc.dingtalk.com/doc#/serverapi2/eev437
	Long agentId();

	String accessKey();

	String accessSecret();
	//endregion

	//region 场景群 https://developers.dingtalk.com/document/chatgroup/create-a-scene-group-v2

	/**
	 * 场景群-模版ID
	 *
	 * @see <a href="https://developers.dingtalk.com/document/chatgroup/fill-in-a-group-template">申请群模板</a>
	 */
	String sceneTemplateId();

	/**
	 * 场景群ID
	 *
	 * <a href="https://developers.dingtalk.com/document/chatgroup/create-a-scene-group-v2">创建场景群</a>
	 */
	String openConversationId();

	/**
	 * 创建场景群 返回字段
	 */
	String sceneChatId();

	//endregion

	//region 企业内部机器人
	String robotEnterpriseAgentId();
	String robotEnterpriseAppKey();
	String robotEnterpriseAppSecret();
	//endregion

	//region 机器人 https://developers.dingtalk.com/document/app/custom-robot-access
	String robotWebhook();
	//endregion

}
