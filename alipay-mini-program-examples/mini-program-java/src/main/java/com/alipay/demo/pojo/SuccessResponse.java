/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.demo.pojo;

/**
 * @author alipay.demo
 *
 */
public class SuccessResponse {

    private Boolean Success;

    public SuccessResponse(){}

    public SuccessResponse(Boolean success) {
        Success = success;
    }

    public Boolean getSuccess() {
        return Success;
    }

    public void setSuccess(Boolean success) {
        Success = success;
    }
}