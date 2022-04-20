package com.vergilyn.examples.alipay.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.vergilyn.examples.alipay.AbstractAlipayClientTests;
import org.testng.annotations.Test;

/**
 *
 * <a href="https://docs.open.alipay.com/203/105285">手机网站支付快速接入 </>
 * @author VergiLyn
 * @date 2019-09-12
 */
public class WapPayTest extends AbstractAlipayClientTests {

    /**
     * 手机网站支付接口alipay.trade.wap.pay
     * https://docs.open.alipay.com/203/107090
     */
    @Test
    public void wappay() {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        request.setBizModel(model);

        model.setOutTradeNo("20180320150900001");
        model.setTotalAmount("1024");
        model.setSubject("Iphone6 16G");
        model.setProductCode("QUICK_WAP_WAY");

        // FIXME 回调地址
        request.setReturnUrl("http://127.0.0.1:59090/w/pay/alipay_return.htm");
        request.setNotifyUrl("http://127.0.0.1:59090/s/pay/alipay_notify.html");//在公共参数中设置回跳和通知地址
        String form = null; //调用SDK生成表单
        try {
            form = _alipayClient.pageExecute(request).getBody();
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(form);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //        httpResponse.setContentType("text/html;CHARSET=" + AlipayServiceEnvConstants.CHARSET);
        //        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        //        httpResponse.getWriter().flush();

    }

    /**
     * alipay.trade.app.pay App支付接口
     * 通过此接口传入订单参数，同时唤起支付宝客户端。
     */
    @Test
    public void pay() {
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        AlipayTradePayModel model = new AlipayTradePayModel();

        model.setOutTradeNo("20150320010101001");
        model.setScene("bar_code");
        model.setAuthCode("28763443825664394");
        model.setProductCode("FACE_TO_FACE_PAYMENT");
        model.setSubject("Iphone6 16G");
        model.setBuyerId("2088202954065786");
        model.setSellerId("2088102146225135");
        model.setTotalAmount("88.88");
        model.setDiscountableAmount("8.88");
        model.setBody("Iphone6 16G");
        model.setGoodsDetail(null);
        model.setOperatorId("yx_001");
        model.setStoreId("NJ_001");
        model.setTerminalId("NJ_T_001");
        model.setExtendParams(null);
        model.setTimeoutExpress("90m");

        request.setBizModel(model);
        //        request.setBizContent("{" +
        //                "\"out_trade_no\":\"20150320010101001\"," +
        //                "\"scene\":\"bar_code\"," +
        //                "\"auth_code\":\"28763443825664394\"," +
        //                "\"product_code\":\"FACE_TO_FACE_PAYMENT\"," +
        //                "\"subject\":\"Iphone6 16G\"," +
        //                "\"buyer_id\":\"2088202954065786\"," +
        //                "\"seller_id\":\"2088102146225135\"," +
        //                "\"total_amount\":88.88," +
        //                "\"discountable_amount\":8.88," +
        //                "\"body\":\"Iphone6 16G\"," +
        //                "\"goods_detail\":[{" +
        //                "        \"goods_id\":\"apple-01\"," +
        //                "       \"goods_name\":\"ipad\"," +
        //                "\"quantity\":1," +
        //                "\"price\":2000," +
        //                "\"goods_category\":\"34543238\"," +
        //                "\"body\":\"特价手机\"," +
        //                "\"show_url\":\"http://www.alipay.com/xxx.jpg\"" +
        //                "        }]," +
        //                "\"operator_id\":\"yx_001\"," +
        //                "\"store_id\":\"NJ_001\"," +
        //                "\"terminal_id\":\"NJ_T_001\"," +
        //                "\"extend_params\":{" +
        //                "\"sys_service_provider_id\":\"2088511833207846\"," +
        //                "\"industry_reflux_info\":\"\\\"{\\\\\\\"scene_code\\\\\\\":\\\\\\\"metro_tradeorder\\\\\\\",\\\\\\\"channel\\\\\\\":\\\\\\\"xxxx\\\\\\\",\\\\\\\"scene_data\\\\\\\":{\\\\\\\"asset_name\\\\\\\":\\\\\\\"ALIPAY\\\\\\\"}}\\\"\\\"\"," +
        //                "\"card_type\":\"S0JP0000\"" +
        //                "    }," +
        //                "\"timeout_express\":\"90m\"," +
        //                "\"auth_confirm_mode\":\"COMPLETE：转交易支付完成结束预授权;NOT_COMPLETE：转交易支付完成不结束预授权\"" +
        //                "  }");
        AlipayTradePayResponse response = null;
        try {
            response = _alipayClient.execute(request);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }

    /**
     * 商户通过该接口进行交易的创建下单
     * https://docs.open.alipay.com/api_1/alipay.trade.create/
     */
    @Test
    public void create() {
        AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
        AlipayTradeCreateModel model = new AlipayTradeCreateModel();
        request.setBizContent("{" + "\"out_trade_no\":\"20150320010101001\"," + "\"seller_id\":\"2088102146225135\","
                + "\"total_amount\":88.88," + "\"discountable_amount\":8.88," + "\"subject\":\"Iphone616G\","
                + "\"body\":\"Iphone616G\"," + "\"buyer_id\":\"2088102146225135\"," + "\"goods_detail\":[{"
                + "\"goods_id\":\"apple-01\"," + "\"goods_name\":\"ipad\"," + "\"quantity\":1," + "\"price\":2000,"
                + "\"goods_category\":\"34543238\"," + "\"body\":\"特价手机\","
                + "\"show_url\":\"http://www.alipay.com/xxx.jpg\"" + "}]," + "\"operator_id\":\"Yx_001\","
                + "\"store_id\":\"NJ_001\"," + "\"terminal_id\":\"NJ_T_001\"," + "\"extend_params\":{"
                + "\"sys_service_provider_id\":\"2088511833207846\"" + "}," + "\"timeout_express\":\"90m\","
                + "\"business_params\":\"{\\\"data\\\":\\\"123\\\"}\"" + "}");
        AlipayTradeCreateResponse response = null;
        try {
            response = _alipayClient.execute(request);
            System.out.println(response.getBody());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }

    /**
     * alipay.trade.precreate(统一收单线下交易预创建)
     * 收银员通过收银台或商户后台调用支付宝接口，生成二维码后，展示给用户，由用户扫描二维码完成订单支付。
     */
    @Test
    public void precreate() {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        request.setBizModel(model);

        model.setOutTradeNo(System.currentTimeMillis() + "");
        model.setTotalAmount("88.88");
        model.setSubject("Iphone6 16G");
        AlipayTradePrecreateResponse response = null;
        try {
            response = _alipayClient.execute(request);
            System.out.println(response.getBody());
            System.out.println(response.getQrCode());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }
}
