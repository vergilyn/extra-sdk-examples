package com.vergilyn.examples;

import com.alipay.api.AbstractAlipayClient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * @author VergiLyn
 * @date 2019-09-12
 */
@SpringBootTest(classes = AlipaySDKApplication.class)
@Slf4j
public abstract class AbstractBaseTest extends AbstractTestNGSpringContextTests {
    @Autowired
    protected AbstractAlipayClient alipayClient;
}
