package com.tang.test.j2v8;

import android.util.Log;

import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

/**
 * 描述: js调用java方法返回
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/18.
 */
public class JavaVoidCallbackImpl implements JavaVoidCallback {

    @Override
    public void invoke(V8Object v8Object, V8Array v8Array) {

        if (null != v8Array.get(0)){
            String tt = v8Object.getString("name");
            String str = (String) v8Array.get(0);

            Log.d("java方法返回结果----------->",tt   +  str);
        }
    }
}
