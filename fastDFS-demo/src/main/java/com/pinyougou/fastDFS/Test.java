package com.pinyougou.fastDFS;

import com.pinyougou.utils.FastDFSClient;
import org.csource.fastdfs.StorageClient;

public class Test {
    public static void main(String[] args) throws Exception {
        FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fdfs_client.conf");
        StorageClient storageClient = fastDFSClient.getStorageClient();
        String[] strings = storageClient.upload_file("C:\\Users\\Administrator\\Desktop\\3fe92443b5662bb6c68bcb789af5e62c6c5a432b.jpg", "jpg", null);
        System.out.println(strings);
    }
}
