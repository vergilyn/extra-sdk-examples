package com.vergilyn.examples.alibaba.dingtalk.users;

import com.dingtalk.api.request.OapiUserListsimpleRequest;
import com.dingtalk.api.request.OapiV2UserGetbymobileRequest;
import com.dingtalk.api.response.OapiUserListsimpleResponse;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import com.vergilyn.examples.alibaba.dingtalk.AbstractDingTalkClientTestng;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

/**
 *
 * @author vergilyn
 * @since 2021-01-27
 */
public class UserInfoTestng extends AbstractDingTalkClientTestng {

	/**
	 * 根据手机号获取用户的userid。
	 * @see <a href="https://developers.dingtalk.com/document/app/query-users-by-phone-number">根据手机号获取userid</a>
	 */
	@Test
	public void getbymobile(){
		String apiUrl = "/topapi/v2/user/getbymobile";

		OapiV2UserGetbymobileRequest req = new OapiV2UserGetbymobileRequest();
		req.setMobile(getDingtalkProperties().getDinggtalkMobile());

		OapiV2UserGetbymobileResponse response = execute(apiUrl, req);

		printJSONString(response);

		boolean eq = StringUtils.endsWithIgnoreCase(getDingtalkProperties().getDingtalkUserId(), response.getResult().getUserid());
		Assertions.assertTrue(eq);
	}

	/**
	 * 获取指定部门的用户userid和name。
	 * @see <a href="https://developers.dingtalk.com/document/app/queries-the-simple-information-of-a-department-user">获取部门用户基础信息</a>
	 */
	@Test
	public void listsimple(){
		String apiUrl = "/topapi/user/listsimple";
		OapiUserListsimpleRequest req = new OapiUserListsimpleRequest();

		// 部门ID，根部门ID为1。
		// 	"errmsg":"client-error:Invalid arguments:the value of deptId can not be less than 1.",
		req.setDeptId(1L);

		// 分页查询的游标，最开始传0，后续传返回参数中的next_cursor值。
		req.setCursor(0L);

		req.setSize(10L);

		/* 部门成员的排序规则：
		 *   entry_asc：代表按照进入部门的时间升序。
		 *   entry_desc：代表按照进入部门的时间降序。
		 *   modify_asc：代表按照部门信息修改时间升序。
		 *   modify_desc：代表按照部门信息修改时间降序。
		 *   custom(default)：代表用户定义(未定义时按照拼音)排序。
		 */
		req.setOrderField("modify_desc");

		// 是否返回访问受限的员工。
		req.setContainAccessLimit(false);

		/* 通讯录语言，取值。
		 * zh_CN：中文（默认值）；en_US：英文。
		 */
		req.setLanguage("zh_CN");

		OapiUserListsimpleResponse response = execute(apiUrl, req);

		printJSONString(response);
	}
}
