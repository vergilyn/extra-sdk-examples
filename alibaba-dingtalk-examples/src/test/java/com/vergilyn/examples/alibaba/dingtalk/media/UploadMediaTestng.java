package com.vergilyn.examples.alibaba.dingtalk.media;

import java.io.File;

import com.dingtalk.api.request.OapiMediaUploadRequest;
import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.taobao.api.FileItem;
import com.vergilyn.examples.alibaba.dingtalk.AbstractDingTalkClientTestng;

import org.testng.annotations.Test;

/**
 * <a href="https://developers.dingtalk.com/document/app/upload-media-files">
 *     上传媒体文件</a>
 * @author vergilyn
 * @since 2021-05-07
 */
public class UploadMediaTestng extends AbstractDingTalkClientTestng {

	/**
	 * 1) media_id是可复用的，同一个media_id多次使用。 <br/>
	 * 2) media_id对应的资源文件，仅能在钉钉客户端内使用。 <br/>
	 * 3) 重复上传返回不同的media_id。 <br/>
	 * 4) <b>暂时没有 删除资源的接口</b> <br/>
	 */
	@Test
	public void upload(){
		String apiUrl = "/media/upload";
		OapiMediaUploadRequest request = new OapiMediaUploadRequest();
		/*
		 * 媒体文件类型：
		 * image：图片，图片最大1MB。支持上传jpg、gif、png、bmp格式文件。
		 * voice：语音，语音文件最大2MB。支持上传amr、mp3、wav格式文件。
		 * video：视频，视频最大10MB。支持上传mp4格式文件。
		 * file：普通文件，最大10MB。支持上传doc、docx、xls、xlsx、ppt、pptx、zip、pdf、rar格式文件。
		 */
		request.setType("image");
		// 要上传的媒体文件
		request.setMedia(buildImage());

		OapiMediaUploadResponse response = execute(apiUrl, request);

		printJSONString(response);
	}

	private FileItem buildImage(){
		/* "errcode":40005, "errmsg": "不合法的文件类型"

		ClassLoader classLoader = this.getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("hachixxxx.jpg");
		return new FileItem("avatar-hachi", inputStream, "image/jpeg");
		*/

		String dir = System.getProperty("user.dir");
		String filepath = dir
				+ File.separator + "src"
				+ File.separator + "test"
				+ File.separator + "resources"
				+ File.separator + "hachixxxx.jpg";

		return new FileItem(filepath);
	}
}
