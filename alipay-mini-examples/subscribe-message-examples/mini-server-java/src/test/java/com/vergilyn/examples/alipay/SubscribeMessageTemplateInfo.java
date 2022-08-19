package com.vergilyn.examples.alipay;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubscribeMessageTemplateInfo {
	/**
	 * <pre>
	 * fe7bfb57cb114867bf6acd4247d0a03c	付费会员自动续费提醒
	 * 续费时间: {{ keyword1 }}
	 * 续费状态: {{ keyword2 }}
	 * 续费金额: {{ keyword3 }}
	 * 续费项目: {{ keyword4 }}
	 * </pre>
	 */
	public static final SubscribeMessageTemplateInfo VIP_AUTO_RENEW;

	/**
	 * <pre>
	 * cbbd808de97a495a9967f1256d05ab6e	业务完成通知
	 * 业务名称: {{ keyword1 }}
	 * 业务类型: {{ keyword2 }}
	 * 完成时间: {{ keyword3 }}
	 * </pre>
	 */
	public static final SubscribeMessageTemplateInfo BUSINESS_FINISH;

	/**
	 * <pre>
	 * 9f04e7c1721b4c1799329717dea6b41f	任务完成提醒
	 * 任务奖励: {{ keyword1 }}
	 * 任务类型: {{ keyword2 }}
	 * </pre>
	 */
	public static final SubscribeMessageTemplateInfo TASK_FINISH;

	static {
		VIP_AUTO_RENEW = new SubscribeMessageTemplateInfo("付费会员自动续费提醒", "fe7bfb57cb114867bf6acd4247d0a03c", SubscibeType.longterm);
		VIP_AUTO_RENEW.content = "续费时间: {{ keyword1 }}\n"
	                            + "续费状态: {{ keyword2 }}\n"
	                            + "续费金额: {{ keyword3 }}\n"
	                            + "续费项目: {{ keyword4 }}";

		BUSINESS_FINISH = new SubscribeMessageTemplateInfo("业务完成通知", "cbbd808de97a495a9967f1256d05ab6e", SubscibeType.onetime);
		BUSINESS_FINISH.content = "业务名称: {{ keyword1 }}\n"
								+ "业务类型: {{ keyword2 }}\n"
								+ "完成时间: {{ keyword3 }}";

		TASK_FINISH = new SubscribeMessageTemplateInfo("任务完成提醒", "9f04e7c1721b4c1799329717dea6b41f", SubscibeType.onetime);
		TASK_FINISH.content = "任务奖励: {{ keyword1 }}\n"
							+ "任务类型: {{ keyword2 }}";
	}

	private String name;
	private String templateId;
	private SubscibeType type;
	private String content;

	public SubscribeMessageTemplateInfo(String name, String templateId, SubscibeType type) {
		this.name = name;
		this.templateId = templateId;
		this.type = type;
	}

	public static enum SubscibeType{
		/**
		 * 长期性订阅
		 */
		longterm,

		/**
		 * 一次性订阅
		 */
		onetime;
	}
}
