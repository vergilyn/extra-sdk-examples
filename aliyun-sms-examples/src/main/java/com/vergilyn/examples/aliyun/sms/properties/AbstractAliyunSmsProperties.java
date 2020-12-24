package com.vergilyn.examples.aliyun.sms.properties;

/**
 * @see <a href="https://help.aliyun.com/document_detail/53045.html">创建AccessKey</a>
 */
public interface AbstractAliyunSmsProperties {

	String getAccessKeyId();

	String getAccessKeySecret();

	/**
	 * MNS消息队列消费模式queue-name。
	 *
	 * @return
	 */
	String getQueueName();
}
