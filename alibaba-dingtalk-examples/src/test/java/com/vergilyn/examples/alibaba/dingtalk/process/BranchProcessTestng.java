package com.vergilyn.examples.alibaba.dingtalk.process;

import java.time.LocalTime;
import java.util.List;

import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest.FormComponentValueVo;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.google.common.collect.Lists;
import com.vergilyn.examples.alibaba.dingtalk.AbstractDingTalkClientTestng;

import org.testng.annotations.Test;

/**
 * 审批流程中包含：分条件流程。例如，分支条件[A, B, C]，如果包含选项`B`，则需要审核。
 * @author vergilyn
 * @date 2021-01-26
 */
public class BranchProcessTestng extends AbstractDingTalkClientTestng {
	private static final String PROCESS_CODE = "PROC-4BDA257D-BBAB-4D59-BA77-B1E92E31B88C";

	@Test
	public void create(){
		String serverUrl = "/topapi/processinstance/create";
		OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
		request.setAgentId(getAgentId());
		request.setProcessCode(PROCESS_CODE);

		request.setOriginatorUserId(dingtalkProperties().dingtalkUserId());
		// 发起人所在的部门。如果发起人属于根部门，传-1
		request.setDeptId(dingtalkProperties().topDeptId());

		request.setFormComponentValues(buildForm());

		// 不传入approvers或approvers_v2参数，会自动复用在审批管理后台预设的审批人
		// request.setApproversV2();

		OapiProcessinstanceCreateResponse response = execute(serverUrl, request);

		printJSONString(response);
	}

	private List<FormComponentValueVo> buildForm() {
		FormComponentValueVo name = new FormComponentValueVo();
		name.setName("名称");
		name.setValue("名称" + LocalTime.now().toString());

		FormComponentValueVo multiSelect = new FormComponentValueVo();
		multiSelect.setName("分支条件");
		// multiSelect.setValue("[\"A\", \"B\"]");
		multiSelect.setValue("[\"A\"]");

		return Lists.newArrayList(name, multiSelect);
	}
}
