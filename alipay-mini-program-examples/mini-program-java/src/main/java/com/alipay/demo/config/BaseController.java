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
package com.alipay.demo.config;

import com.alipay.api.AlipayClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/** 控制层基础类，承载公共配置信息
 * @author alipay.demo
 *
 */
public class BaseController {
    /**
	 * 配置文件加载
	 */
    @Autowired  
    protected Environment config;

    @Autowired  
    protected AlipayClient alipayClient;
    

	public void setAlipayClient(AlipayClient alipayClient) {
		this.alipayClient = alipayClient;
	}

	public void setConfig(Environment config) {
		this.config = config;
	} 
    
}
