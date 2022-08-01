package com.vergilyn.examples.aliyun.rocketmq.feature;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.common.message.MessageClientIDSetter;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.vergilyn.examples.aliyun.rocketmq.AbstractAliyunBusinessRocketMQTests;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class ProviderTests extends AbstractAliyunBusinessRocketMQTests {
	private final String tag = "helloworld";

	/**
	 * <a href="https://help.aliyun.com/document_detail/29547.html">发送普通消息（三种方式）</a>
	 */
	@SneakyThrows
	@Test
	public void producer(){
		Producer producer = startProducer();

		String msgContent = "Hello world: " + LocalDateTime.now();

		Message msg = new Message(_properties.topic(),
		                          tag,
		                          MessageClientIDSetter.createUniqID(),
		                          msgContent.getBytes(RemotingHelper.DEFAULT_CHARSET));

		producer.sendAsync(msg, new SendCallback() {
			@Override
			public void onSuccess(final SendResult sendResult) {
				// 消息发送成功。
				System.out.printf("#aliyun#mq#sendAsync send message success. topic=%s, msgId=%s \n", sendResult.getTopic(), sendResult.getMessageId());
			}

			@Override
			public void onException(OnExceptionContext context) {
				// 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理。
				System.out.printf("#aliyun#mq#sendAsync send message fail. message:%s \n", msg);
			}
		});

		TimeUnit.SECONDS.sleep(5);
	}

	/**
	 * {@link Message} 实际数据示例：
	 * <pre>{@code
	 * {
	 * 	"body":"SGVsbG8gd29ybGQ6IDIwMjItMDgtMDFUMTA6MjM6MDguNzQw",
	 * 	"bornHost":"222.182.1xx.2xx:54401",
	 * 	"bornTimestamp":1659320589562,
	 * 	"key":"AC15000185A818B4AAC2023A831D0000",
	 * 	"msgID":"AC15000185A818B4AAC2023A83D40001",
	 * 	"offset":0,
	 * 	"reconsumeTimes":0,
	 * 	"shardingKey":"",
	 * 	"startDeliverTime":0,
	 * 	"tag":"helloworld",
	 * 	"topic":"vergilyn_busi_topic",
	 * 	"topicPartition":{
	 * 		"partition":"rmq-gateway-share-01-cn-chengdu-0#1",
	 * 		"topic":"vergilyn_busi_topic"
	 *      },
	 * 	"userProperties":{
	 * 		"MSG_REGION":"cn-chengdu",
	 * 		"UNIQ_KEY":"AC15000185A818B4AAC2023A83D40001",
	 * 		"TRACE_ON":"true",
	 * 		"1ST_POP_TIME":"1659321532190",
	 * 		"CONSUME_START_TIME":"1659321532890",
	 * 		"INSTANCE_ID":"MQ_INST_17940780816?????_BYJlELzn",
	 * 		"__MESSAGE_DECODED_TIME":"1659321532189",
	 * 		"__FQN_TOPIC":"MQ_INST_17940780816?????_BYJlELzn%vergilyn_busi_topic",
	 * 		"POP_CK":"0 1659321532190 60000 7 0 cn-chengdu-share-01-1 5 0"
	 *    }
	 * }
	 *
	 *
	 * }</pre>
	 */
	@SneakyThrows
	@Test
	public void consumer(){
		Consumer consumer = createConsumer();

		consumer.subscribe(_topic, tag, new MessageListener() { //订阅多个Tag。
			public Action consume(Message message, ConsumeContext context) {
				System.out.println("Receive: " + message);
				return Action.CommitMessage;
			}
		});

		consumer.start();
		System.out.println("Consumer Started");

		TimeUnit.HOURS.sleep(1);
	}
}
