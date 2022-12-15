package com.vergilyn.examples.aliyun.feature;

import com.aliyun.openservices.aliyun.log.producer.Result;
import com.aliyun.openservices.log.common.LogItem;
import com.google.common.util.concurrent.ListenableFuture;
import com.vergilyn.examples.aliyun.AbstractAliyunSLSClientTests;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class SendSLSTests extends AbstractAliyunSLSClientTests {

	private final String slsProject = "vergilyn-test-sls";
	private final String slsLogStore = "stats_logs";

	private final String EVENT_SUCCESS = "SUCCESS";
	private final String EVENT_FAILURE = "FAILURE";

	private final String CATEGORY_100 = "100";
	private final String CATEGORY_110 = "110";

	@Test
	public void send(){
		AtomicInteger logItemIndex = new AtomicInteger(0);
		List<LogItem> logItems = Stream.generate(() -> {
			LogItem logItem = new LogItem();

			logItem.PushBack("scene_id", "10080");
			logItem.PushBack("task_id", null);
			logItem.PushBack("category", RandomUtils.nextBoolean() ? CATEGORY_100 : CATEGORY_110);
			logItem.PushBack("event", RandomUtils.nextBoolean() ? EVENT_SUCCESS : EVENT_FAILURE);
			logItem.PushBack("user_id", "409839163_"
					+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                    + "_" + logItemIndex.incrementAndGet()
			);

			logItem.PushBack("data_target", RandomUtils.nextInt(1, 4) + "");

			// 可以写入 SLS，但是不能用于 分析语句。
			logItem.PushBack("not_exists_field", "not_exists_field");


			// new_field_a,  新增字段，开启统计。
			//   1) 可能 不写此值  （部分系统不调整）
			//   2) 写字段，  但值可能是 null 或者 具体值
			if (RandomUtils.nextBoolean()){
				logItem.PushBack("new_field_a", RandomUtils.nextBoolean() ? "true" : null);
			}

			return logItem;
		}).limit(10).collect(Collectors.toList());

		try {

			// topic 暂时实例 spring-application-name，例如`zmn-mcs-dubbo`
			log.info("[vergilyn][sls-send] 准备发送。slsProject: {}, slsLogStore: {}, topic: {}, item.size: {}", slsProject, slsLogStore, _applicationName, logItems.size());

			ListenableFuture<Result> future = createSLSProducer(slsProject).send(slsProject, slsLogStore, _applicationName, null, logItems);
			Result result = future.get();
			log.info("[vergilyn][sls-send] 发送结果。isSuccessful: {}, ErrorCode: {}, ErrorMessage: {}",
			         result.isSuccessful(),
			         result.getErrorCode(),
			         result.getErrorMessage());
		} catch (Exception e) {
			log.error("[vergilyn][sls-send] 异常: {}", e.getMessage(), e);
		}
	}
}
