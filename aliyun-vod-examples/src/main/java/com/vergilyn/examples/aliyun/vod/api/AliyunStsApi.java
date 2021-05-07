package com.vergilyn.examples.aliyun.vod.api;

import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
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

		return _vodClient.getAcsResponse(request);
	}


	/**
	 * <a href="https://help.aliyun.com/document_detail/52835.html">获取视频信息</a>
	 * @return
	 */
	@SneakyThrows
	public GetVideoInfoResponse getVideoInfo(String videoId){
		GetVideoInfoRequest request = new GetVideoInfoRequest();
		request.setActionName("GetVideoInfo");  // 系统规定参数
		request.setVideoId(videoId);

		// ...更多参数参考文档

		return _vodClient.getAcsResponse(request);
	}

	/**
	 * <a href="https://help.aliyun.com/document_detail/56124.html">获取视频播放地址</a>
	 */
	@SneakyThrows
	public GetPlayInfoResponse getPlayInfo(String videoId){
		GetPlayInfoRequest request = new GetPlayInfoRequest();
		request.setActionName("GetPlayInfo");  // 系统规定参数
		request.setVideoId(videoId);

		/* 类型：String
		 * 必填：否
		 * 示例：mp4,mp3
		 * 描述：视频流格式。多个用英文逗号（,）分隔。支持格式：mp4、m3u8、mp3、mpd
		 */
		// request.setFormats();

		/* 播放地址过期时间。单位：秒。
		 * 根据 OutputType 最大值不一样
		 */
		request.setAuthTimeout(2592000L);  // 30天

		// 输出地址类型。oss：回源地址。 cdn（默认）：加速地址。
		request.setOutputType("cdn");

		// ...更多参数参考文档

		return _vodClient.getAcsResponse(request);
	}

	/**
	 * <a href="https://help.aliyun.com/document_detail/52833.html">获取视频播放凭证</a>
	 */
	@SneakyThrows
	public GetVideoPlayAuthResponse getPlayAuth(String videoId) {
		GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
		request.setActionName("GetVideoPlayAuth");
		request.setVideoId(videoId);


		/* 类型：Long
		 * 必填：否
		 * 示例：3000
		 * 描述：播放凭证过期时间。
		 *   默认为100秒。取值范围：[100,3000]。
		 */
		request.setAuthInfoTimeout(100L);

		return _vodClient.getAcsResponse(request);
	}

	/**
	 * <a href="https://help.aliyun.com/document_detail/93109.htm">批量删除源文件</a>
	 * <a href="https://help.aliyun.com/document_detail/52837.html">删除完整视频</a>
	 */
	@SneakyThrows
	public DeleteVideoResponse deleteVideo(String videoId){
		DeleteVideoRequest request = new DeleteVideoRequest();
		request.setActionName("DeleteVideo");

		// 视频ID列表。多个ID使用英文逗号（,）分隔。最多支持20个。
		request.setVideoIds(videoId);

		return _vodClient.getAcsResponse(request);
	}
}
