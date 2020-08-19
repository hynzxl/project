package com.pinyougou.fastDFS;

import org.csource.fastdfs.*;

import java.io.IOException;

public class FastDFSDemo {
    public static void main(String[] args) throws Exception {
        ClientGlobal.init("E:\\pinyougou\\pinyougouparent\\fastDFS-demo\\src\\main\\resources\\fdfs_client.conf");
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer,storageServer);
        String[] strings = storageClient.upload_appender_file("C:\\Users\\Administrator\\Desktop\\3fe92443b5662bb6c68bcb789af5e62c6c5a432b.jpg", "jpg", null);
        for (String string : strings) {
            System.out.println(string);
        }

    }
}
