package com.sky.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;

@Data
@AllArgsConstructor
@Slf4j
public class TencentCosUtil {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;


    public String upload( String objectName, File file) {

        // 1. 初始化用户身份信息（secretId, secretKey）
        COSCredentials cred = new BasicCOSCredentials(accessKeyId, accessKeySecret);
        // 2. 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        Region region = new com.qcloud.cos.region.Region(com.tencentcloudapi.common.profile.Region.Ashburn.getValue());
        // 3. 生成 cos 客户端配置信
        ClientConfig clientConfig = new ClientConfig(region);
        // 4. 生成 cos 客户端
        COSClient cosClient = new COSClient(cred, clientConfig);

        try {
            // 创建PutObject请求。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName,file );
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

            return putObjectResult.getETag();
        } catch (Exception e) {
            System.out.println("Caught an CosException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + e);
        }

        // 关闭客户端(关闭后台线程)
        cosClient.shutdown();

        //文件访问路径规则 https://BucketName.Endpoint/ObjectName
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(bucketName)
                .append(".")
                .append(endpoint)
                .append("/")
                .append(objectName);

        log.info("文件上传到:{}", stringBuilder.toString());

        return stringBuilder.toString();

    }
}
