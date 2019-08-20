/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
import com.alipay.demo.pojo.UserPayListResponse;

import org.jboss.resteasy.spi.HttpResponse;

/**
 * @author alipay.demo
 *
 */
@Path("/voucher")
@Consumes(RestConstants.DEFAULT_CONTENT_TYPE)
@Produces(RestConstants.DEFAULT_CONTENT_TYPE)
public interface UserPayListController {
    /**
     * 通过URL访问： http://localhost:8080/alipay/voucher/userPay?userId=XXX
     *
     * @param userid
     * @param response
     * @return
     * @throws Exception
     */
    @GET
    @Path("/userPay")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    UserPayListResponse userPay(@QueryParam("userId") String userid, @Context HttpResponse response) throws Exception;

}