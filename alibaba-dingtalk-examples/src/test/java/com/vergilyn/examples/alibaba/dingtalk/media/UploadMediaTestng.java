package com.vergilyn.examples.alibaba.dingtalk.media;

import java.io.InputStream;
import java.net.URL;
import java.util.Locale;

import com.dingtalk.api.request.OapiMediaUploadRequest;
import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.taobao.api.FileItem;
import com.vergilyn.examples.alibaba.dingtalk.AbstractDingTalkClientTestng;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

/**
 * <a href="https://developers.dingtalk.com/document/app/upload-media-files">
 *     上传媒体文件</a>
 *
 * <p>
 *   1) media_id是可复用的，同一个media_id多次使用。 <br/>
 * 	 2) media_id对应的资源文件，仅能在钉钉客户端内使用。 <br/>
 * 	 3) 重复上传返回不同的media_id。 <br/>
 * 	 4) <b>暂时没有 删除资源的接口</b> <br/>
 * </p>
 * @author vergilyn
 * @since 2021-05-07
 */
public class UploadMediaTestng extends AbstractDingTalkClientTestng {

	@Test
	public void uploadByFilepath(){
		//"errcode":40005, "errmsg": "不合法的文件类型"

		FileItem fileItem;
		ClassLoader classLoader = this.getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("hachixxxx.jpg");
		fileItem = new FileItem("hachixxxx.jpg", inputStream, "image/jpg");

		/*
		String dir = System.getProperty("user.dir");
		String filepath = dir
				+ File.separator + "src"
				+ File.separator + "test"
				+ File.separator + "resources"
				+ File.separator + "hachixxxx.jpg";
		fileItem = new FileItem(filepath);
		*/

		OapiMediaUploadResponse response = upload(MediaType.image, fileItem);

		printJSONString(response);
	}

	@SneakyThrows
	@Test
	public void uploadByUrl(){
		String imageUrl = "https://pic.cnblogs.com/avatar/1025273/20171112211439.png";
		byte[] bytes = IOUtils.toByteArray(new URL(imageUrl));

		// filename: 非空，且需要带后缀
		FileItem fileItem = new FileItem("hachixxxx.png", bytes);

		OapiMediaUploadResponse response = upload(MediaType.image, fileItem);

		printJSONString(response);
	}

	/**
	 * 媒体文件类型：<br/>
	 *   image：图片，图片最大1MB。支持上传jpg、gif、png、bmp格式文件。<br/>
	 *   voice：语音，语音文件最大2MB。支持上传amr、mp3、wav格式文件。<br/>
	 *   video：视频，视频最大10MB。支持上传mp4格式文件。<br/>
	 *   file：普通文件，最大10MB。支持上传doc、docx、xls、xlsx、ppt、pptx、zip、pdf、rar格式文件。<br/>
	 */
	private OapiMediaUploadResponse upload(MediaType type, FileItem fileItem){
		String apiUrl = "/media/upload";
		OapiMediaUploadRequest request = new OapiMediaUploadRequest();
		request.setType(type.name().toLowerCase(Locale.ROOT));
		request.setMedia(fileItem);

		return execute(apiUrl, request);
	}

	private static enum MediaType {
		image, voice, video, file;
	}
}
