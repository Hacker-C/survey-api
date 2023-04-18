package com.cg.controller;

import com.cg.result.Result;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static com.cg.util.AssistUtil.*;
import static com.cg.util.SystemConst.*;

@RestController
@RequestMapping("upload")
public class UploadController {
    @Value("${oss.bucketName}")
    private String bucketName;
    @Value("${oss.accessKey}")
    private String accessKey;
    @Value("${oss.secretKey}")
    private String secretKey;
    @Value("${oss.dnsPath}")
    private String dnsPath;

    /**
     * 七牛云上传头像
     * @param file
     * @return
     */
    @PostMapping
    public Result<String> uploadFile(MultipartFile file) {
        assertionWithSystemException(Objects.isNull(file), FILE_NOT_EMPTY);
        String filename = file.getOriginalFilename();
        boolean flag = filename.endsWith(".png") || filename.endsWith(".jpg") || filename.endsWith(".jpeg");
        assertionWithSystemException(!flag, FILE_TYPE_ERROR);
//上传到七牛云
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String path = generatorFilePath(filename);
        try {
            InputStream inputStream = file.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucketName);
            try {
                Response response = uploadManager.put(inputStream,path,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            } catch (QiniuException ex) {
                Response r = ex.response;
                assertionWithSystemException(true, QI_NIU_ERROR);
            }
        }catch (IOException e) {
            assertionWithSystemException(true, QI_NIU_ERROR);
        }
        return Result.ok(dnsPath + path);
    }
}
