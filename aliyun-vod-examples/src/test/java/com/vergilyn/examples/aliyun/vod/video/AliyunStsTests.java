package com.vergilyn.examples.aliyun.vod.video;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
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

	/**
	 * <a href="https://help.aliyun.com/document_detail/56124.html">获取视频播放地址</a>
	 */
	@SneakyThrows
	@Test
	public void getPlayInfo(){
		GetPlayInfoRequest request = new GetPlayInfoRequest();
		request.setActionName("GetPlayInfo");  // 系统规定参数
		request.setVideoId(_properties.videoId());

		/* 类型：String
		 * 必填：否
		 * 示例：mp4,mp3
		 * 描述：视频流格式。多个用英文逗号（,）分隔。支持格式：mp4、m3u8、mp3、mpd
		 */
		// request.setFormats();

		/* 播放地址过期时间。单位：秒。 默认 3600s(1h)
		 * 根据 OutputType 最大值不一样
		 */
		request.setAuthTimeout(2592000L);  // 30天

		// 输出地址类型。oss：回源地址。 cdn（默认）：加速地址。
		request.setOutputType("oss");

		// ...更多参数参考文档

		GetPlayInfoResponse response = getAcsResponse(request);
		System.out.println(JSON.toJSONString(response, PrettyFormat));
	}

	/**
	 * <a href="https://help.aliyun.com/document_detail/56124.html">获取视频播放地址</a>
	 */
	@SneakyThrows
	@Test
	public void playInfoWithoutExpired(){
		GetPlayInfoRequest request = new GetPlayInfoRequest();
		request.setActionName("GetPlayInfo");  // 系统规定参数
		request.setVideoId(_properties.videoId());

		// 输出地址类型。oss：回源地址。 cdn（默认）：加速地址。
		request.setOutputType("cdn");
		request.setAuthTimeout(Long.MAX_VALUE);  // 无效，最大还是 30days，


		// ...更多参数参考文档

		GetPlayInfoResponse response = getAcsResponse(request);
		System.out.println(JSON.toJSONString(response, PrettyFormat));
	}
}
