package com.vergilyn.examples.aliyun.vod.video;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoInfoResponse;
import com.vergilyn.examples.aliyun.vod.AbstractAliyunVodClientTest;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat;

public class VideoInfoTests extends AbstractAliyunVodClientTest {

	/**
	 * <a href="https://help.aliyun.com/document_detail/52835.html">获取视频信息</a>
	 */
	@SneakyThrows
	@Test
	public void getVideoInfo(){
		GetVideoInfoRequest request = new GetVideoInfoRequest();
		request.setActionName("GetVideoInfo");  // 系统规定参数
		request.setVideoId("21747933ac8b4b1ea403773a917c37b9");

		GetVideoInfoResponse response = _vodClient.getAcsResponse(request);

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
