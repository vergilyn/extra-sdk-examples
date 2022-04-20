package com.vergilyn.examples.baidu.mini;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author vergilyn
 * @since 2021-07-16
 */
public abstract class AbstractBaiduMiniTestng {

	protected CloseableHttpClient httpClient = null;

	@BeforeTest
	public void before(){
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(10_000)
				.setConnectTimeout(10_000)
				.setSocketTimeout(10_000)
				.setMaxRedirects(0)
				.build();

		httpClient = HttpClients.custom()
				.setDefaultRequestConfig(requestConfig)
				.setMaxConnPerRoute(8)
				.setMaxConnTotal(64)
				.setConnectionTimeToLive(10, TimeUnit.SECONDS)

				//  可以看到在HttpClientBuilder进行build的时候，如果指定了开启清理功能，会创建一个连接池清理线程并运行它。
				// .evictIdleConnections(maxIdleTime, unit)     // 指定是否清理空闲连接
				// .evictExpiredConnections()   // 指定是否要清理过期连接，默认不启动清理线程(false)
				.build();

	}


}
