/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.demo.controller.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.alipay.demo.config.RestConstants;

/**
 * @author alipay.demo
 *
 */
@Path("/voucher")
@Consumes(RestConstants.DEFAULT_CONTENT_TYPE)
@Produces(RestConstants.DEFAULT_CONTENT_TYPE)
public interface AlipayPassTemplateAddController {

    /**
     * 通过URL访问： http://localhost:8080/alipay/voucher/alipayPassTemplateAdd
     *
     * @param request
     * @return
     * @throws Exception
     */
    @GET
    @Path("/alipayPassTemplateAdd")
    Object alipayPassTemplateAdd(@Context HttpServletRequest request) throws Exception;
}