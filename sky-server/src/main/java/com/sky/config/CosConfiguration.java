package com.sky.config;

import com.sky.properties.TencentCosProperties;
import com.sky.utils.TencentCosUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，用于创建TencentCosUtil对象
 */
@Configuration
@Slf4j
public class CosConfiguration {
    @Bean
    public TencentCosUtil tencentCosUtil(TencentCosProperties tencentCosProperties) {
        log.info("开始创建腾讯云文件上传工具类对象:{}",tencentCosProperties);
        return new TencentCosUtil(tencentCosProperties.getEndpoint(),
                tencentCosProperties.getTmpSecretId(),
                tencentCosProperties.getTmpSecretKey(),
                tencentCosProperties.getBucketName());
    }
}
