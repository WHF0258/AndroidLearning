package com.example.servicepractice;

import android.os.AsyncTask;
import android.os.Binder;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask extends AsyncTask<String,Integer,Integer> {
    public static  final int TYPE_SUCCESS = 0;
    public static  final int TYPE_FAILED = 1;
    public static  final int TYPE_PUASED = 2;
    public static  final int TYPE_CANCLED = 3;
    private DownloadListener downloadListener;
    private boolean isCancled = false;
    private boolean isPaused = false;
    private int lastProgress;

    public DownloadTask(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }


    @Override
    protected Integer doInBackground(String... strings) {
        InputStream inputStream = null;
        RandomAccessFile savedFile = null;
        File file = null;
        try{
            long downloadedLength = 0;
            String downLoadUrl =  strings[0];
            String fileName = downLoadUrl.substring(downLoadUrl.lastIndexOf("/")); //解析文件名
            String diretory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(diretory + fileName);
            if (file.exists()){
                downloadedLength = file.length();
            }
            long contentLength = getContentLength(downLoadUrl);
            if (contentLength == 0){
                return TYPE_FAILED;
            }else if (contentLength == downloadedLength){
                //下载 == 内容  下载完成
                return TYPE_SUCCESS;
            }
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    //断点下载 指定字节
                    .addHeader("RANGE","bytes="+downloadedLength+"-")
                    .url(downLoadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null){
                inputStream = response.body().byteStream();
                savedFile = new RandomAccessFile(file,"rw");
                savedFile.seek(downloadedLength);//跳过已下载字节
                byte[] bytes = new byte[1024];
                int total = 0;
                int len;
                while ((len = inputStream.read(bytes)) != -1){
                    if (isCancled){
                        return TYPE_FAILED;
                    }else if (isPaused){
                        return TYPE_PUASED;
                    }else {
                        total += len;
                        savedFile.write(bytes,0,len);
                        //下载进度
                        int progress = (int) ((total +downloadedLength)*100/contentLength);
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if (inputStream != null) inputStream.close();
                if (savedFile != null) savedFile.close();
                if (isCancled && file != null) file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return TYPE_FAILED;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int progress = (int) values[0];
        if (progress > lastProgress){
            downloadListener.onProgress(progress);
            lastProgress = progress;
        }
    }


    @Override
    protected void onPostExecute(Integer status) {
        super.onPostExecute(status);
        switch (status){
            case TYPE_SUCCESS:
                downloadListener.onSuccess();
                break;
            case TYPE_FAILED:
                downloadListener.onFailed();
                break;
            case TYPE_PUASED:
                downloadListener.onPaused();
                break;
            case TYPE_CANCLED:
                downloadListener.onCancled();
                break;
            default:
                break;
        }
    }

    public void pauseDownload(){
        isPaused = true;
    }
    public void cancleDownload(){
        isCancled = true;
    }

    private long getContentLength(String downLoadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(downLoadUrl).build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()){
            long contentLength = response.body().contentLength();
            response.body().close();
            return contentLength;
        }
        return 0;
    }

}
