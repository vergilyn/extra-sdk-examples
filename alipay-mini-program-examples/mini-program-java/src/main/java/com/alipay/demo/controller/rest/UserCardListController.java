/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.demo.controller.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.alipay.demo.config.RestConstants;
import com.alipay.demo.pojo.UserCardListResponse;

import org.jboss.resteasy.spi.HttpResponse;

/**
 * @author alipay.demo
 *
 */
@Path("/voucher")
@Consumes(RestConstants.DEFAULT_CONTENT_TYPE)
@Produces(RestConstants.DEFAULT_CONTENT_TYPE)
public interface UserCardListController {
    /**
     * 通过URL访问： http://localhost:8080/alipay/voucher/userCard?userId=XXX
     *
     * @param userid
     * @param response
     * @return
     * @throws Exception
     */
    @GET
    @Path("/userCard")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    UserCardListResponse userCard(@QueryParam("user_id") String userid, @Context HttpResponse response) throws Exception;
}