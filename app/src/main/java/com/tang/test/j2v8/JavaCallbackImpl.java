package com.tang.test.j2v8;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

/**
 * 描述: js调用java方法 并返回结果
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/18.
 */
public class JavaCallbackImpl implements JavaCallback {
    @Override
    public Object invoke(V8Object v8Object, V8Array v8Array) {

        String callback = "";
        if (null != v8Array.get(0)){
            callback = (String) v8Array.get(0);

            callback = "我是java回调结果，并再次处理返回：" + callback;
        }
        return callback;
    }
}
