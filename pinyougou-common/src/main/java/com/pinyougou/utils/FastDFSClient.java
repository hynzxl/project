package com.pinyougou.utils;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;


public class FastDFSClient {

    private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;
    private StorageServer storageServer = null;
    private StorageClient storageClient = null;

    public StorageClient getStorageClient() {
        return storageClient;
    }

    public FastDFSClient(String config) {
        try {
            if (null != config && !config.equals("")){
                if (config.contains("classpath:")){
                    config = config.replace("classpath:",this.getClass().getResource("/").getPath());//代替为绝对路径
                }
                ClientGlobal.init(config);
                trackerClient = new TrackerClient();
                trackerServer = trackerClient.getConnection();
                storageClient = new StorageClient(trackerServer,storageServer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //上传文件方法
    public String[] uploadFile(String filePath,String suffix,NameValuePair[] meta_list){
        try {
            String[] strings = storageClient.upload_file(filePath,suffix,meta_list);
            return strings;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] uploadFile(byte[] file_buff, String file_ext_name, NameValuePair[] meta_list){
        try {
            String[] strings = storageClient.upload_file(file_buff, file_ext_name, meta_list);
            return strings;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
