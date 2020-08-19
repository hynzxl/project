package com.pinyougou.shop.controller;

import com.pinyougou.utils.FastDFSClient;
import com.pinyougou.utils.Resoult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileController {

    @Value("${FILE_SERVER_URL}")
    private String serverPath;

    @RequestMapping("/uploadFile")
    public Resoult uploadFile(MultipartFile file){
        try {
            //1.新建fastDFSClient对象,用于上传到图片服务器
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fdfs_client.conf");
            //2.通过MultipartFile获取上传的参数
            String originalFilename = file.getOriginalFilename();  //获取文件路径
            int index = originalFilename.lastIndexOf(".");
            String substring = originalFilename.substring(index + 1); //得到文件后缀名
            String[] strings = fastDFSClient.uploadFile(file.getBytes(), substring, null);
            String path = serverPath;
            for (int i = 0;i < strings.length;i++){
                path = path + "/" + strings[i];
            }
            Resoult resoult = new Resoult(path);
            resoult.setCome(1);
            return resoult;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
