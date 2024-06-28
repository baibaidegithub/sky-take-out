package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
//添加在yml中的配置类，这样在yml中就可以出现自动提示填充，然后在yml中配置这个类中的值，具体示例千万yml
@ConfigurationProperties(prefix = "sky.tencentcos")
@Data
public class TencentCosProperties {
    private String endpoint;
    private String tmpSecretId;
    private String tmpSecretKey;
    private String bucketName;
}
