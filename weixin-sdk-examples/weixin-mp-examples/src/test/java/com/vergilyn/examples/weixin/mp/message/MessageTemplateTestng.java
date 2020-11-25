package com.vergilyn.examples.weixin.mp.message;

import java.util.List;

import com.vergilyn.examples.weixin.mp.AbstractWeixinMpTestng;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;
import org.testng.annotations.Test;

/**
 * <a href="https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Template_Message_Interface.html">模版消息接口</a>
 */
public class MessageTemplateTestng extends AbstractWeixinMpTestng {

	/**
	 * 获取模板列表（需要微信认证）
	 *
	 * <p>
	 *   个人认证错误信息："错误代码：48001, 错误信息：api 功能未授权"
	 * </p>
	 *
	 * @see <a href="https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Template_Message_Interface.html#3">获取模板列表</a>
	 */
	@Test
	public void getAllPrivateTemplate(){
		try {
			List<WxMpTemplate> tpls = templateMsgService.getAllPrivateTemplate();

			printJsonString(tpls);

		} catch (WxErrorException e) {
			e.printStackTrace();
		}
	}

}
