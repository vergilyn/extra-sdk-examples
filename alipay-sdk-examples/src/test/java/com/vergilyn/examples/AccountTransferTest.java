package com.vergilyn.examples;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
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
}
