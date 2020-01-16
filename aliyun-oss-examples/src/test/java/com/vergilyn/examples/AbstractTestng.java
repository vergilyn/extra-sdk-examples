package com.vergilyn.examples;

import java.util.Optional;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

/**
 * @date 2019/2/14
 */
public abstract class AbstractTestng {
    private static final String ENDPOINT = "oss-cn-hangzhou.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "<yourAccessKeyId>"; // yourAccessKeyId
    private static final String ACCESS_KEY_SECRET = "<yourAccessKeySecret>"; // yourAccessKeySecret

    protected static final String BUCKET_NAME = "vergilyn"; // yourBucketName
    protected static final String EXIST_OBJECT_NAME = "5191d2b0166d5_600x.jpg";
    protected OSS ossClient;

    @Test
    public void before() {
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。
        // 强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    @AfterTest
    public void after() {
        Optional.ofNullable(ossClient).ifPresent(OSS::shutdown);
    }

    protected void print(ObjectMetadata metadata){
        System.out.println("content-type: " + metadata.getContentType());
        System.out.println("last-modified: " + metadata.getLastModified());
    }

}
