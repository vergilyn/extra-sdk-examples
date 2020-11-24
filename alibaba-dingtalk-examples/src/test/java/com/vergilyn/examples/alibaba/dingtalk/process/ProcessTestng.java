package com.vergilyn.examples.alibaba.dingtalk.process;

import java.text.ParsePosition;
import java.util.List;

import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.request.OapiProcessinstanceGetRequest;
import com.dingtalk.api.request.OapiProcessinstanceListidsRequest;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.dingtalk.api.response.OapiProcessinstanceListidsResponse;
import com.google.common.collect.Lists;
import com.vergilyn.examples.alibaba.dingtalk.AbstractDingTalkClientTestng;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.testng.annotations.Test;

public class ProcessTestng extends AbstractDingTalkClientTestng {
	/**
	 * 请假模版，表单字段：请假类型、开始时间、结束时间、时长、请假事由、图片
	 * FIXME 2020-11-24,vergilyn 替换成自己的process_code
	 */
	private static final String PROCESS_CODE = "PROC-B289EB4A-BFB1-4EA8-95CC-1F7D037C7C0E";

	private static final FastDateFormat FORMAT_YYYY_MM_DD_HH_SS = FastDateFormat.getInstance("yyyy-MM-dd HH:mm");
	private static final String START_DATE_STRING = "2020-11-23 09:00";
	private static final String END_DATE_STRING = "2020-11-23 19:00";
	private static final Long START_DATE_UNIX = FORMAT_YYYY_MM_DD_HH_SS
													.parse(START_DATE_STRING, new ParsePosition(0))
													.getTime();
	private static final Long END_DATE_UNIX = FORMAT_YYYY_MM_DD_HH_SS
													.parse(END_DATE_STRING, new ParsePosition(0))
													.getTime();

	/**
	 *
	 * <pre>
	 *   // 审批流程中包含"分支条件"导致以下错误
	 *	 "errorCode":"820001",
	 *	 "msg":"发起审批实例失败，错误原因为【系统错误:审批流程预测异常，请联系管理员处理】",
	 *	 "params":{
	 *	    "form_component_values":"[{\"name\":\"请假类型\",\"value\":\"事假\"},{\"name\":\"[\\\"开始时间\\\",\\\"结束时间\\\"]\",\"value\":\"[\\\"2020-11-23 09:00\\\",\\\"2020-11-23 19:00\\\"]\"},{\"name\":\"请假事由\",\"value\":\"测试OAPI创建审批流程\"}]",
	 *	    "agent_id":"1011446848",
	 *	    "dept_id":"-1",
	 *	    "process_code":"PROC-B289EB4A-BFB1-4EA8-95CC-1F7D037C7C0E",
	 *	    "originator_user_id":"072760165220257910"
	 *	 }
	 * </pre>
	 * @see <a href="https://ding-doc.dingtalk.com/document#/org-dev-guide/initiate-approval">发起审批实例</a>
	 */
	@Test
	public void create() {
		String serverUrl = "/topapi/processinstance/create";
		OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
		request.setAgentId(getAgentId());
		request.setProcessCode(PROCESS_CODE);

		request.setOriginatorUserId(getDingtalkProperties().getDingtalkUserId());
		// 发起人所在的部门。如果发起人属于根部门，传-1
		request.setDeptId(getDingtalkProperties().getTopDeptId());

		request.setFormComponentValues(buildForm());

		// 不传入approvers或approvers_v2参数，会自动复用在审批管理后台预设的审批人
		// request.setApproversV2();

		OapiProcessinstanceCreateResponse response = execute(serverUrl, request);

		printJSONString(response);
	}

	/**
	 * @see <a href="https://ding-doc.dingtalk.com/document#/org-dev-guide/obtain-an-approval-list-of-instance-ids">批量获取审批实例ID</a>
	 * @see <a href="https://ding-doc.dingtalk.com/document#/org-dev-guide/obtains-details-about-a-specified-approval-instance">根据实例ID获取审批实例详情</a>
	 */
	@Test
	public void getProcessInstance() {
		String serverUrl = "/topapi/processinstance/listids";

		OapiProcessinstanceListidsRequest request = new OapiProcessinstanceListidsRequest();
		request.setProcessCode(PROCESS_CODE);
		request.setStartTime(START_DATE_UNIX);
		request.setEndTime(END_DATE_UNIX);
		request.setSize(10L);
		request.setCursor(0L);
		// 发起人用户id列表，用逗号分隔，最大列表长度：10
		request.setUseridList(getDingtalkProperties().getDingtalkUserId());

		OapiProcessinstanceListidsResponse response = execute(serverUrl, request);
		printJSONString(response);

		// 根据实例ID获取审批实例详情
		if (response == null || !response.isSuccess()){
			return;
		}

		OapiProcessinstanceListidsResponse.PageResult result = response.getResult();
		String instanceId = result.getList().get(0);
		if (StringUtils.isBlank(instanceId)){
			return;
		}

		serverUrl = "/topapi/processinstance/get";

		OapiProcessinstanceGetRequest getRequest = new OapiProcessinstanceGetRequest();
		getRequest.setProcessInstanceId(instanceId);

		OapiProcessinstanceGetResponse getResponse = execute(serverUrl, getRequest);
		printJSONString(getResponse);
	}

	private List<OapiProcessinstanceCreateRequest.FormComponentValueVo> buildForm() {
		// 请假类型、开始时间、结束时间、时长、请假事由、图片
		OapiProcessinstanceCreateRequest.FormComponentValueVo f1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
		f1.setName("请假类型");
		f1.setValue("事假");

		OapiProcessinstanceCreateRequest.FormComponentValueVo f2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
		f2.setName("[\"开始时间\",\"结束时间\"]");
		// [\"start-date\",\"end-date\"]
		f2.setValue("[\"" + START_DATE_STRING + "\",\"" + END_DATE_STRING + "\"]");

		OapiProcessinstanceCreateRequest.FormComponentValueVo f3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
		f3.setName("时长");
		f3.setValue("1");  // FIXME 2020-11-23 单位？

		OapiProcessinstanceCreateRequest.FormComponentValueVo f4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
		f4.setName("请假事由");
		f4.setValue("测试OAPI创建审批流程");

		return Lists.newArrayList(f1, f2, f3, f4);
	}
}
