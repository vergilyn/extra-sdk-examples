package com.vergilyn.examples;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * @author VergiLyn
 * @date 2019-09-12
 */
@SpringBootTest(classes = WeiXinApplication.class)
@Slf4j
public abstract class AbstractBaseTest extends AbstractTestNGSpringContextTests {

}
