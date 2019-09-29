package com.vergilyn.examples;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayFundAccountQueryRequest;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundAccountQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

/**
 * <a href="https://docs.open.alipay.com/api_28/alipay.fund.trans.toaccount.transfer">单笔转账到支付宝账户接口</a>
 *
 * @author VergiLyn
 * @date 2019-09-12
 */
@Slf4j
public class AccountTransferTest extends AbstractBaseTest {

    @Test
    public void alipayFundTransToaccountTransferRequest() {
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        request.setBizContent("{" + "\"out_biz_no\":\"3142321423432\"," + "\"payee_type\":\"ALIPAY_LOGONID\","
                + "\"payee_account\":\"koqhoo6035@sandbox.com\"," + "\"amount\":\"12.23\"," + "\"payer_show_name\":\"上海交通卡退款\","
                + "\"payee_real_name\":\"沙箱环境\"," + "\"remark\":\"转账备注\"" + "  }");

        AlipayFundTransToaccountTransferResponse response = null;
        try {
            response = alipayClient.execute(request);
            log.info("Alipay response >>>> {}, {}", response.isSuccess(), JSON.toJSON(response));

        } catch (AlipayApiException e) {
            log.error("单笔转账到支付宝账户接口错误", e);
        }
    }

    /**
     * <pre>
     *   查询支付宝资金账户资产
     *   接口地址：<a href="https://docs.open.alipay.com/api_1/alipay.fund.account.query">alipay.fund.account.query(支付宝资金账户资产查询接口)</a>
     * </pre>
     */
    @Test
    public void alipayFundAccountQueryRequest(){
        AlipayFundAccountQueryRequest request = new AlipayFundAccountQueryRequest();
        request.setBizContent("{" +
                "\"alipay_user_id\":\"2088202215895635\"," +
                "\"merchant_user_id\":\"243893499\"," +
                "\"account_product_code\":\"DING_ACCOUNT\"," +
                "\"account_type\":\"ACCTRANS_ACCOUNT\"," +
                "\"account_scene_code\":\"SCENE_000_000_000\"," +
                "\"ext_info\":\"{\\\"agreement_no\\\":\\\"2019-09-27\\\"}\"" +
                "  }");
        AlipayFundAccountQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
            System.out.println("支付宝资金账户资产 >>>> " + response.isSuccess() + ", " + JSON.toJSONString(response));
        } catch (AlipayApiException e) {
            logger.error(e);
        }

    }
}
