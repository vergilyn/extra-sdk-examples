package com.vergilyn.examples.baidu.mini.client;

/**
 *
 * @author vergilyn
 * @since 2021-07-16
 */
public class BaiduMiniClient {

	private static BaiduMiniClient _INSTANCE;

	private BaiduMiniClient(){

	}

	public static BaiduMiniClient getInstance(){
		if (_INSTANCE != null){
			return _INSTANCE;
		}

		synchronized (BaiduMiniClient.class){
			if (_INSTANCE != null){
				return _INSTANCE;
			}

			_INSTANCE = new BaiduMiniClient();
		}

		return _INSTANCE;

	}
}
