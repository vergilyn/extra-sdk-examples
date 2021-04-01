package com.vergilyn.examples.aliyun.vod.api;

import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.vergilyn.examples.aliyun.vod.config.AliyunVodClient;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class AliyunStsApi {
	private final AliyunVodClient _vodClient = AliyunVodClient.getInstance();

	/**
	 * @see <a href="https://help.aliyun.com/document_detail/57114.html">创建角色并进行STS临时授权</a>
	 */
	@SneakyThrows
	public AssumeRoleResponse getUploadSts(){
		//构造请求，设置参数。关于参数含义和设置方法，请参见API参考。
		AssumeRoleRequest request = new AssumeRoleRequest();
		request.setSysRegionId(_vodClient.getProperties().regionId());
		request.setRoleArn(_vodClient.getProperties().stsRoleArn());

		// RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
		// 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
		// 具体规则请参考API文档中的格式要求
		String roleSessionName = "vergilyn-session-name";// 自定义即可
		request.setRoleSessionName(roleSessionName);

		AssumeRoleResponse response = _vodClient.getAcsResponse(request);
		return response;
	}
}
