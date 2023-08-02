package com.example.servicepractice;

public interface DownloadListener {
    void onProgress(int progress);//下载进度
    void onSuccess(); //通知下载成功
    void onFailed(); //通知下载失败
    void onPaused();  //通知下载暂停
    void onCancled();  //通知下载取消
}
