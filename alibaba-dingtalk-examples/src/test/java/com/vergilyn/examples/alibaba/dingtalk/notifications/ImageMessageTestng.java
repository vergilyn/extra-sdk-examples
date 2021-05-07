package com.vergilyn.examples.alibaba.dingtalk.notifications;

import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request.Image;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request.Msg;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.vergilyn.examples.alibaba.dingtalk.media.UploadMediaTestng;

import org.testng.annotations.Test;

import static com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request.Markdown;

/**
 * <a href="https://developers.dingtalk.com/document/app/message-types-and-data-format/title-isi-kyv-dfx">
 *     工作通知 - 图片消息</a>
 * <p>
 * 同样受到发送限制：<br/>
 *   1) 同一个应用相同消息的内容同一个用户一天只能接收一次。<br/>
 *   2) 同一个应用给同一个用户发送消息，企业内部应用一天不得超过500次。<br/>
 *   3) 通过设置to_all_user参数全员推送消息，一天最多3次。<br/>
 *   4) 超出以上限制次数后，接口返回成功，但用户无法接收到<br/>
 * </p>
 *
 * @author vergilyn
 * @since 2021-05-07
 */
public class ImageMessageTestng extends AbstractAsyncSendEnterpriseMessages {

	/**
	 * 最好用`image`上传获取到的media_id，如果用`file`上传后的media_id，消息内容看着不如图片本身好看。
	 *
	 * @see <a href="https://developers.dingtalk.com/document/app/message-types-and-data-format/title-isi-kyv-dfx">
	 *     工作通知 - 图片消息</a>
	 * @see <a href="https://developers.dingtalk.com/document/app/upload-media-files">
	 *     上传媒体文件</a>
	 * @see UploadMediaTestng
	 */
	@Test
	public void image(){
		Image image = new Image();
		image.setMediaId(getDingtalkProperties().getMediaId());

		Msg msg = new Msg();
		msg.setMsgtype("image");
		msg.setImage(image);

		OapiMessageCorpconversationAsyncsendV2Response response = asyncSend(msg);

		printJSONString(response);
	}

	/**
	 * 由于“图片消息”需要单独上传图片资源，所以考虑用“markdown消息”来实现
	 * @see <a href="https://developers.dingtalk.com/document/app/message-types-and-data-format/title-afc-2nh-5kk">markdown消息</a>
	 */
	@Test
	public void markdownImage(){
		Msg msg = new Msg();
		msg.setMsgtype("markdown");

		Markdown markdown = new Markdown();
		// markdown.setTitle("markdown.title 参数不能为空");
		markdown.setTitle("标题不能为空123");  // 标题改变 算不同的消息内容
		markdown.setText("![hachixxxx](https://pic.cnblogs.com/avatar/1025273/20171112211439.png)");
		msg.setMarkdown(markdown);

		OapiMessageCorpconversationAsyncsendV2Response response = asyncSend(msg);
		printJSONString(response);
	}
}
