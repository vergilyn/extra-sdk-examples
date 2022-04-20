package com.vergilyn.examples.baidu.mini.api;

/**
 *
 * @author vergilyn
 * @since 2021-07-16
 */
public interface BaiduMiniRequest {
	String DEFAULT_API_HOSTNAME = "https://openapi.baidu.com";


	public String getApiURL();

	default String getApiHostname(){
		return DEFAULT_API_HOSTNAME;
	}
}


