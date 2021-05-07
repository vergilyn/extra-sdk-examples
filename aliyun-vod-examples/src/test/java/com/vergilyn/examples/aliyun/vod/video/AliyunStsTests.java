package com.vergilyn.examples.aliyun.vod.video;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.vergilyn.examples.aliyun.vod.AbstractAliyunVodClientTest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat;

public class AliyunStsTests extends AbstractAliyunVodClientTest {

	/**
	 * @see <a href="https://help.aliyun.com/document_detail/57114.html">创建角色并进行STS临时授权</a>
	 */
	@SneakyThrows
	@Test
	public void stsUpload(){
		//构造请求，设置参数。关于参数含义和设置方法，请参见API参考。
		AssumeRoleRequest request = new AssumeRoleRequest();
		request.setSysRegionId(_properties.regionId());
		request.setRoleArn(_properties.stsRoleArn());

		// RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
		// 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
		// 具体规则请参考API文档中的格式要求
		String roleSessionName = "vergilyn-session-name";// 自定义即可
		request.setRoleSessionName(roleSessionName);

		AssumeRoleResponse response = getAcsResponse(request);
		System.out.println(JSON.toJSONString(response.getCredentials(), PrettyFormat));
	}

	/**
	 * <a href="https://help.aliyun.com/document_detail/52833.html">获取视频播放凭证</a>
	 */
	@SneakyThrows
	@Test
	public void getVideoPlayAuth(){
		GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
		request.setActionName("GetVideoPlayAuth");  // 系统规定参数
		request.setVideoId(_properties.videoId());

		/* 类型：Long
		 * 必填：否
		 * 示例：3000
		 * 描述：播放凭证过期时间。
		 *      默认为100秒。取值范围：[100,3000]。
		 */
		request.setAuthInfoTimeout(3000L);

		GetVideoPlayAuthResponse response = getAcsResponse(request);
		System.out.println(JSON.toJSONString(response, PrettyFormat));
	}
}
