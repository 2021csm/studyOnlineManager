package com.example.study.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.example.study.util.OSSConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Service
public class OSSService {
    @Autowired
   private OSSConfig ossConfig;

    public String uploadImageOnAliBaBa(String uploadName,InputStream inputStream) throws FileNotFoundException {
        // 创建OSSClient实例。
       // OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //https://study2020.oss-cn-beijing.aliyuncs.com/
        // 上传文件流。
        OSS ossClient = new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        ossClient.putObject(ossConfig.getBucketName(), ossConfig.getDir()+"/"+uploadName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
        StringBuilder builder=new StringBuilder();
        builder.append("https://")
                .append(ossConfig.getBucketName())
                .append(".")
                .append(ossConfig.getEndpoint())
                .append("/")
                .append(ossConfig.getDir())
                .append("/")
                .append(uploadName);
        return builder.toString();
    }
}
