package com.tang.test.j2v8;

import android.text.TextUtils;

import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: j2v8的单例模式 目的是使用时js文件只读取一遍。
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/8/28.
 */
public class J2V8Single {

    //    public J2V8Single single = new J2V8Single();

    private static J2V8Single instance = null;

    private J2V8Interface context;

    private String jsonJs = "";

    /**
     * 初始化
     * @param context
     */
    public J2V8Single(J2V8Interface context) {
        if (null == context) return;
        this.context = context;
        context.Log("J2V8Single----->处理js脚本");
        this.jsonJs = J2V8Util.getOssJsUrl("https://resource.toolmall.com/resource/toolmallerp/template/j2v8test.js");
//        this.jsonJs = J2V8Util.getLoaclJsUrl(context);
        if (!TextUtils.isEmpty(jsonJs)){
            context.Log("J2V8Single----->解析js脚本成功");
        }else {
            context.Log("J2V8Single----->解析js脚本失败");
        }
    }

    public static J2V8Single getInstance(J2V8Interface context){
        if (null == instance){
            return new J2V8Single(context);
        }
        return instance;
    }

    /**
     * 获取js解析后的数据
     * @param convertName js的处理方法
     * @return js解析处理后的数据
     */
    public String engineJs(String convertName){
        return engineJs(convertName,"");
    }

    /**
     * 获取js解析后的数据
     * @param convertName js的处理方法
     * @param jsonString 单个上传字段
     * @return js解析处理后的数据
     */
    public String engineJs(String convertName,String jsonString){
        return engineJs(convertName,jsonString,null,"",null);
    }

    /**
     * 获取js解析后的数据
     * @param convertName js的处理方法
     * @param list 多个上传字段
     * @return js解析处理后的数据
     */
    public String engineJs(String convertName, List<Object> list){
        return engineJs(convertName,"",list,"",null);
    }

    /**
     * 获取js解析后的数据
     * @param convertName js的处理方法
     * @param jsonString 单个上传字段
     * @param list 多个上传字段
     * @param registName java回调注册的方法
     * @param voidCallback 注册java方法到js中的处理回调
     * @return js解析处理后的数据
     */
    public String engineJs(String convertName, String jsonString, List<Object> list, String registName, JavaVoidCallback voidCallback){
        if (null == context) return "";
        if (TextUtils.isEmpty(jsonJs)) return "js文件不存在";
        if (TextUtils.isEmpty(convertName)) return "解析的方法名不能为空";
        V8 v8 = V8.createV8Runtime();//启动v8、
        context.Log("J2V8Single----->v8已启动");
        String response = "";//js解析结果
        if (!TextUtils.isEmpty(jsonJs)) {
            //先判断v8是否存在，以及是否被释放
            if (null != v8 && !v8.isReleased()) {
                //v8获取js的方法类
                v8.executeVoidScript(jsonJs);
                context.Log("J2V8Single----->js已注入到v8中");
                if (null != voidCallback){
                    //如果回调不为空，则注册回调
                    v8.registerJavaMethod(voidCallback,registName);
                    context.Log("J2V8Single----->" + registName + "方法已注册到js");
                }
                List<Object> putList = new ArrayList<>();
                if (!TextUtils.isEmpty(jsonString)){
                    if (list == null){
                        list = new ArrayList<>();
                    }
                    list.add(jsonString);
                    context.Log("J2V8Single----->当前是单字段上传");
                }
                if (null != list){
                    putList.addAll(list);
                    context.Log("J2V8Single----->当前是多字段上传");
                }
                response = J2V8Util.v8StringFunction(v8, convertName, J2V8Util.getV8Array(v8, putList));
                context.Log("J2V8Single----->js处理完毕");
                context.Log("J2V8Single----->js解析结果："+response);
                v8.release();//使用完，释放v8
                context.Log("J2V8Single----->v8已释放");
            }
        }else {
            context.Log("J2V8Single----->获取js失败");
        }
        return response;
    }
}
