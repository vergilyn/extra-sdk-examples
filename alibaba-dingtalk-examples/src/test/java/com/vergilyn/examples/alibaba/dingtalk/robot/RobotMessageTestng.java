package com.vergilyn.examples.alibaba.dingtalk.robot;

import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.vergilyn.examples.alibaba.dingtalk.AbstractDingTalkClientTestng;
import org.assertj.core.util.Lists;
import org.testng.annotations.Test;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author vergilyn
 * @since 2021-07-15
 *
 * @see <a href="https://developers.dingtalk.com/document/app/custom-robot-access/title-zob-eyu-qse">使用自定义消息机器人</a>
 */
public class RobotMessageTestng extends AbstractDingTalkClientTestng {

	/**
	 * 每个机器人每分钟最多发送20条。
	 * 消息发送太频繁会严重影响群成员的使用体验，大量发消息的场景 (譬如系统监控报警) 可以将这些信息进行整合，通过markdown消息以摘要的形式发送到群里。
	 *
	 * @see <a href="https://developers.dingtalk.com/document/app/custom-robot-access/title-72m-8ag-pqw">机器人消息类型及数据格式</a>
	 * @see <a href="https://open.dingtalk.com/document/robots/customize-robot-security-settings">自定义机器人安全设置</a>
	 */
	@Test
	public void msgText(){
		LocalDateTime now = LocalDateTime.now();
		String nowStr = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		final String robotWebhook = dingtalkProperties().robotWebhook();

		OapiRobotSendRequest request = new OapiRobotSendRequest();
		request.setMsgtype("text");

		OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
		// robot使用的是“自定义关键词” 所以content必须包含 `vergilyn`
		// text.setContent("[vergilyn] 测试文本消息的内容，以及@2个相关人员");

		// 内容中也可以写入 @xx，格式是 `@dingtalkUserId`。
		// 貌似，会导致 `at`参数无效
		text.setContent(MessageFormat.format("[vergilyn]测试文本消息的内容 ",
				dingtalkProperties().dingtalkUserId(), dingtalkProperties().dingtalkUserIdOther(), nowStr));

		request.setText(text);

		OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
		at.setAtMobiles(Lists.newArrayList(dingtalkProperties().moblieOther()));
		at.setAtUserIds(Lists.newArrayList(dingtalkProperties().dingtalkUserId()));
		at.setIsAtAll(false);
		request.setAt(at);

		final OapiRobotSendResponse response = executeExplicitUrl(robotWebhook, request);
		printJSONString(response);
	}
}
