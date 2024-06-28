package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.TencentCosUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {

    @Autowired
    private TencentCosUtil tencentCosUtil;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload_test")
    @ApiOperation(value = "文件上传")
    public Result<String> test(MultipartFile file){
        if(file == null)
        {
            log.info("file is null");
        }else{
            try {
                //原始文件名
                String originFileName = file.getOriginalFilename();
                //截取原始文件名的后缀 例如ddfdf.png
                String extension = originFileName.substring(originFileName.lastIndexOf("."));
                //构造新文件名称
                String objectName = UUID.randomUUID().toString() + extension;

                // 创建临时文件或保存到指定路径
                File tempFile = null;

                // File tempFile = new File("path/to/save/" + file.getOriginalFilename()); // 或指定固定路径
                tempFile = File.createTempFile("upload-", file.getOriginalFilename());

                // 将 MultipartFile 转换成 java.io.File
                file.transferTo(tempFile);

                String filePath = tencentCosUtil.upload(objectName, tempFile);

                return Result.success(filePath);
            } catch (IOException e) {
                log.error("文件上传失败:{}",e);
            }
        }
        log.info("test message received!");
        return null;
    }

    @PostMapping("/upload")
    @ApiOperation(value = "文件上传")
    //前端表单中的名称必须和型参中的名称一致才能接收，假如不一致，则要使用@RequestParam("{表单中的参数名}"MultipartFile file)
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        if(file == null)
        {
            log.info("file is null");
            return null;
        }
        log.info("文件上传:{}", file.getOriginalFilename());

        try {
            //原始文件名
            String originFileName = file.getOriginalFilename();
            //截取原始文件名的后缀 例如ddfdf.png
            String extension = originFileName.substring(originFileName.lastIndexOf("."));
            //构造新文件名称
            String objectName = UUID.randomUUID().toString() + extension;

            // 创建临时文件或保存到指定路径
            File tempFile = null;

            // File tempFile = new File("path/to/save/" + file.getOriginalFilename()); // 或指定固定路径
            tempFile = File.createTempFile("upload-", file.getOriginalFilename());

            // 将 MultipartFile 转换成 java.io.File
            file.transferTo(tempFile);

            String filePath = tencentCosUtil.upload(objectName, tempFile);

            return Result.success(filePath);
        } catch (Exception e) {
            log.error("文件上传失败:{}",e);
        }
        return null;
    }
}
