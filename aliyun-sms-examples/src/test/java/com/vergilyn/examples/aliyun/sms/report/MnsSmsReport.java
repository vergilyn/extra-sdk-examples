package com.vergilyn.examples.aliyun.sms.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alicom.mns.tools.DefaultAlicomMessagePuller;
import com.alicom.mns.tools.MessageListener;
import com.aliyun.mns.model.Message;
import com.aliyuncs.exceptions.ClientException;
import com.vergilyn.examples.aliyun.sms.AbstractAliyunSmsClientTest;
import com.vergilyn.examples.aliyun.sms.dto.SmsReportDTO;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static com.alibaba.fastjson.serializer.SerializerFeature.PrettyFormat;

/**
 * @see <a href="https://help.aliyun.com/document_detail/101641.html">通过MNS消息队列可以拉取短信状态报告（SmsReport）。</a>
 */
@Slf4j
public class MnsSmsReport extends AbstractAliyunSmsClientTest {

	@Test
	public void pullReport() {
		DefaultAlicomMessagePuller puller = new DefaultAlicomMessagePuller();

		// 单线程 拉取msg
		puller.setPullMsgThreadSize(1);

		// 每次拉取16条数据，多个线程消费
		//设置异步线程池大小及任务队列的大小，还有无数据线程休眠时间
		puller.setConsumeMinThreadSize(6);
		puller.setConsumeMaxThreadSize(16);
		puller.setThreadQueueSize(200);
		//和服务端联调问题时开启,平时无需开启，消耗性能
		puller.openDebugLog(true);

		//TODO 此处需要替换成开发者自己的AK信息
		String accessKeyId = properties().getAccessKeyId();
		String accessKeySecret = properties().getAccessKeySecret();

		/*
		 * TODO 将messageType和queueName替换成您需要的消息类型名称和对应的队列名称
		 *云通信产品下所有的回执消息类型:
		 *1:短信回执：SmsReport，
		 *2:短息上行：SmsUp
		 *3:语音呼叫：VoiceReport
		 *4:流量直冲：FlowReport
		 */
		String messageType = "SmsReport";//此处应该替换成相应产品的消息类型
		String queueName = properties().getQueueName();//在云通信页面开通相应业务消息后，就能在页面上获得对应的queueName,格式类似Alicom-Queue-xxxxxx-SmsReport
		try {
			puller.startReceiveMsg(accessKeyId, accessKeySecret, messageType, queueName, new AliyunSmsMNSListener());

		} catch (ClientException | ParseException e) {
			log.error("[vergilyn]监听pull aliyun-MNS-SmsReport 失败", e);
		}

		preventExit();
	}

	static class AliyunSmsMNSListener implements MessageListener {

		@Override
		public boolean dealMessage(Message message) {

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//消息的几个关键值
			System.out.println("message receiver time from mns:" + format.format(new Date()));
			System.out.println("message handle: " + message.getReceiptHandle());
			System.out.println("message body: " + message.getMessageBodyAsString());
			System.out.println("message id: " + message.getMessageId());
			System.out.println("message dequeue count:" + message.getDequeueCount());
			System.out.println("Thread:" + Thread.currentThread().getName());

			String body = message.getMessageBodyAsString();
			try {

				SmsReportDTO report = JSON.parseObject(body, SmsReportDTO.class);
				System.out.println("report >>>> " + JSON.toJSONString(report, PrettyFormat));

			} catch (Throwable e) {
				//您自己的代码部分导致的异常，应该return false,这样消息不会被delete掉，而会根据策略进行重推
				return false;
			}

			//消息处理成功，返回true, SDK将调用MNS的delete方法将消息从队列中删除掉
			return true;
		}

	}
}
