package com.tang.test.j2v8;

import android.app.Activity;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Function;
import com.tang.test.AsyncTaskBackListener;
import com.tang.test.J2V8ReadJavaScript;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/18.
 */
public class J2v8Example {

    public static void getJs(Activity activity,final JavaCallbackImpl javaCallback, final JavaVoidCallbackImpl callbackImpl, final J2V8ValueCallBack valueCallBack){
        final V8 v8 = V8.createV8Runtime();
        J2V8ReadJavaScript readJavaScript = new J2V8ReadJavaScript(activity, new AsyncTaskBackListener<String>()
        {
            @Override
            public void onAsyncTaskCallBack(String s)
            {

                if (v8 != null && !v8.isReleased())
                {
                    StringBuffer stringBuffer = new StringBuffer();
                    v8.executeVoidScript(s);//拿到当前js的方法

                    //在js脚本中注册java方法
                    v8.registerJavaMethod(callbackImpl,"javaVoid");

                    v8.registerJavaMethod(javaCallback,"javaBack");

                    List<Object> list1 = new ArrayList<>();
                    list1.add("我是js脚本中：j2v8String方法的第一个参数哦");
                    list1.add("我是js脚本中：j2v8String方法的第二个参数哦");
                    stringBuffer.append(J2V8Util.v8StringFunction(v8,"j2v8String",J2V8Util.getV8Array(v8,list1)) + "/n");

                    List<Object> list2 = new ArrayList<>();
                    list2.add(10000);
                    list2.add(20000);
                    stringBuffer.append(",------->我是调用的Int返回方法：" + J2V8Util.v8IntFunction(v8,"j2v8Int",J2V8Util.getV8Array(v8,list2)));

                    List<Object> list3 = new ArrayList<>();
                    list3.add("aaa");
                    list3.add("aaa");
                    stringBuffer.append(",------->我是调用的boolean返回方法："+ J2V8Util.v8BooleanFunction(v8,"j2v8Boolean",J2V8Util.getV8Array(v8,list3)));

                    //判断是否是函数
                    if (v8.getType("putVoid") == V8.V8_FUNCTION) {
                        List<Object> list4 = new ArrayList<>();
                        list4.add(234);
                        list4.add(33);
                        V8Function call = (V8Function) v8.getObject("putVoid");
                        int ss = (int) call.call(null, J2V8Util.getV8Array(v8, list4));
                        call.close();
                        stringBuffer.append(",------->调用js的方法并返回结果：" + ss);
                    }

                    List<Object> list5 = new ArrayList<>();
                    list5.add("我是java注册的回调函数:参数1");
                    list5.add("我是java注册的回到函数:参数2");
                    stringBuffer.append(J2V8Util.v8StringFunction(v8,"javaCallBack",J2V8Util.getV8Array(v8,list5)));

                    valueCallBack.getReadValue(stringBuffer.toString());
                    v8.release(false);
                }
            }
        });

        readJavaScript.execute();
    }
}
