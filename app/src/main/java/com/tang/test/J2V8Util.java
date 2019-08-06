package com.tang.test;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Function;

import java.util.List;
import java.util.Map;

/**
 * 描述: j2v8工具类
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/8/5.
 */
public class J2V8Util {

    /**
     * 拼接上传参数-获取v8array
     * @param v8
     * @param list
     * @return
     */
    public static V8Array getV8Array(V8 v8, List<Object> list){
        V8Array array = null;
        if (null == list) return null;
        if (null != v8 && !v8.isReleased()){
            array = new V8Array(v8);
            for (Object item : list){
                array.push(item);
            }
        }
        return array;
    }

    /**
     * 获取js方法中返回string类型的数据
     * @param v8
     * @param name js函数名
     * @param array 上传参数
     * @return 返回字符串
     */
    public static String v8StringFunction(V8 v8, String name,V8Array array)
    {
        String result = "";
        try {
            if (v8 != null&&!v8.isReleased()){
                if (null != array){
                    result = v8.executeStringFunction(name, array);
                    array.close();
                }else {
                    result = v8.executeStringFunction(name, null);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取js方法中返回boolean类型的数据
     * @param v8
     * @param name js函数名
     * @param array 上传参数
     * @return 返回值
     */
    public static Boolean v8BooleanFunction(V8 v8, String name,V8Array array)
    {
        boolean result = false;
        try {
            if (v8 != null&&!v8.isReleased()){
                if (null != array){
                    result = v8.executeBooleanFunction(name, array);
                    array.close();
                }else {
                    result = v8.executeBooleanFunction(name, null);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取js方法中返回Int类型的数据
     * @param v8
     * @param name js函数名
     * @param array 上传参数
     * @return 返回值
     */
    public static Integer v8IntFunction(V8 v8, String name,V8Array array)
    {
        int result = 0;
        try {
            if (v8 != null&&!v8.isReleased()){
                if (null != array){
                    result = v8.executeIntegerFunction(name, array);
                    array.close();
                }else {
                    result = v8.executeIntegerFunction(name, null);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取js函数
     * @param v8
     * @param name 函数名
     * @param array 上传参数
     */
    public static void v8Void(V8 v8, String name, V8Array array, V8Function javaCallback) {
        try {
            if (v8 != null&&!v8.isReleased())
            {
                if (null != array){
                    array.push(javaCallback);
                    v8.executeVoidFunction(name, array);
                    array.release();
                }else {
                    v8.executeVoidFunction(name, null);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return;
    }
}
