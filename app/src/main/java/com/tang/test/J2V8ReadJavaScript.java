package com.tang.test;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.tang.test.j2v8.J2V8Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 描述: 使用asyncTask异步读取本地js脚本
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/8/5.
 */
public class J2V8ReadJavaScript extends AsyncTask<String, String, String>
{
    private AsyncTaskBackListener<String> listener;

    private Activity activity;
    public J2V8ReadJavaScript(Activity activity, AsyncTaskBackListener<String> listener)
    {
        this.listener = listener;
        this.activity = activity;

    }
    public J2V8ReadJavaScript()
    {
        super();
    }

    @Override
    protected String doInBackground(String... strparams)
    {
        final InputStream INPUTSTREAM = this.activity.getClass().getResourceAsStream("/assets/j2v8/j2v8test.js");//获取js脚本的输入流
        return J2V8Util.getFileContent(INPUTSTREAM);
    }
    @Override
    protected void onPostExecute(String result)
    {
        if (listener != null)
        {
            listener.onAsyncTaskCallBack(result);
        }
    }
}