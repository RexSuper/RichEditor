package com.rex.richeditor.tools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.rex.richeditor.ShowHtmlActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Rex on 2019/8/20.
 */
public class DownloadTask extends AsyncTask<String, Void, Boolean> {
    private String url;
    private String destPath;
    private DownloadTaskCallBack mDownloadTaskCallBack;

    public interface DownloadTaskCallBack {
        void onPreExecute();

        void doInBackground(int progress);

        void onPostExecute(boolean status, String url, String destPath);

        void onError(String status);
    }

    public DownloadTask(ShowHtmlActivity showHtmlActivity, DownloadTaskCallBack downloadTaskCallBack) {
        this.mDownloadTaskCallBack = downloadTaskCallBack;
    }

    @Override
    protected void onPreExecute() {
        if (mDownloadTaskCallBack != null) {
            mDownloadTaskCallBack.onPreExecute();
        }
    }

    @Override
    protected Boolean doInBackground(String... params) {
        url = params[0];
        destPath = params[1];
        OutputStream out = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(15000);
            InputStream in = urlConnection.getInputStream();
            out = new FileOutputStream(params[1]);
            byte[] buffer = new byte[10 * 1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            in.close();
        } catch (IOException e) {
            if (mDownloadTaskCallBack != null) {
                mDownloadTaskCallBack.onError(e.toString());
            }
            return false;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e2) {
                    if (mDownloadTaskCallBack != null) {
                        mDownloadTaskCallBack.onError(e2.toString());
                    }
                }
            }
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean noError) {
        if (mDownloadTaskCallBack != null) {
            mDownloadTaskCallBack.onPostExecute(noError, url, destPath);
        }


    }

    public static String getMIMEType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }


}


