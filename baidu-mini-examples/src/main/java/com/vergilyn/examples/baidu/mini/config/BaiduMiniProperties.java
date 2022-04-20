package com.vergilyn.examples.baidu.mini.config;

/**
 *
 * @author vergilyn
 * @since 2022-04-20
 */
public interface BaiduMiniProperties {
	String API_HOSTNAME = "https://openapi.baidu.com";

	/**
	 * <pre>
	 *   解释：获取小程序全局唯一后台接口调用凭据（access_token）。调用绝大多数后台接口时都需使用 access_token，开发者需要进行妥善保存。
	 *
	 *   access_token 的存储与更新：
	 *     1) access_token 的存储至少要保留 1024 个字符空间。
	 *     2) access_token 的有效期目前为 1 个月，中控服务器需要根据这个有效时间提前去刷新。
	 *     3) 建议开发者使用中控服务器统一获取和刷新 access_token，由中控服务器统一管理分发 access_token 到其他业务产品线。
	 * </pre>
	 *
	 * @see <a href="https://smartprogram.baidu.com/docs/develop/serverapi/power_exp/">getAccessToken</a>
	 */
	String accessToken();

	default String apiHostname(){
		return API_HOSTNAME;
	}
}
