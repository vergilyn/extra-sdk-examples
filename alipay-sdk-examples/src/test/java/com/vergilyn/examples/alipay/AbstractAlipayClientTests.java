package com.vergilyn.examples.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author vergilyn
 * @see <a href="https://opendocs.alipay.com/open/54/103419">服务端 SDK（通用版）</a>
 * @since 2021-09-01
 */
public abstract class AbstractAlipayClientTests {

	protected final AbstractAlipayClientConfig _alipayConfig = new MiniAlipayClientConfig();

	protected final AlipayClient _alipayClient = new DefaultAlipayClient(_alipayConfig.serverUrl(),
			_alipayConfig.appId(), _alipayConfig.privateKey(), _alipayConfig.format(),
			_alipayConfig.charset(), _alipayConfig.alipayPublicKey(), _alipayConfig.signType());

	protected final AbstractAlipayUserInfo _alipayUserInfo = new SandboxAlipayUserInfo();

	protected Map<String, Object> putGet(Map<String, Object> map, String key){
		Map<String, Object> value = Maps.newHashMap();
		map.put(key, value);
		return value;
	}
}

