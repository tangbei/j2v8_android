package com.tang.test.j2v8;

import android.os.AsyncTask;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Function;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 描述: j2v8工具类
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/8/5.
 */
public class J2V8Util {

    /**
     * 获取oss上的js文件，并转换成String返回
     * 如果是获取网络地址，则使用 J2V8Single.class 处理
     * @param _url oss上的js地址：https://resource.toolmall.com/resource/toolmallerp/template/j2v8test.js
     * @return
     */
    public static String getOssJsUrl(String _url){
        String jsPath ="";
        try {
            // 构造URL
            URL url = new URL(_url);
            // 打开连接
            URLConnection con = url.openConnection();
            // 设置请求超时为5s
            con.setConnectTimeout(5 * 1000);
            // 输入流
            InputStream is = con.getInputStream();
            jsPath = supplyAsync(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsPath;
    }

    /**
     * 获取本地js文件，并转换成string
     * @param context
     * @return
     */
    public static String getLocalJsUrl(J2V8Interface context) {
        String localString = "";
        final InputStream INPUTSTREAM = context.getClass().getClassLoader().getResourceAsStream("j2v8/j2v8test.js");//获取js脚本的输入流
        try {
            localString = supplyAsync(INPUTSTREAM);//获取js处理后的字符串
        } catch (Exception e) {
            e.printStackTrace();
            context.Log("J2V8Single----->js处理失败");
        }
        return localString;
    }

    /**
     * 异步处理js文件
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static String supplyAsync(final InputStream inputStream) throws Exception {
        final String[] jsonJs = {""};
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return getFileContent(inputStream);
            }

            @Override
            protected void onPostExecute(String s) {
                jsonJs[0] = s;
//                super.onPostExecute(s);
            }
        };
        return jsonJs[0];
    }

    /**
     * 读取js并转换成string字符串
     *
     * @param inputStream
     * @return
     */
    public static String getFileContent(InputStream inputStream) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            //通过管理器打开文件并读取
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line).append("\r\n");//为了保证js的严格“行”属性，这里主动追加\r\n
            }
            bf.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 拼接上传参数-获取v8array
     *
     * @param v8
     * @param list
     * @return
     */
    public static V8Array getV8Array(V8 v8, List<Object> list) {
        V8Array array = null;
        if (null == list) return null;
        if (null != v8 && !v8.isReleased()) {
            array = new V8Array(v8);
            for (Object item : list) {
                if (item instanceof String){
                    array.push((String) item);
                } else if (item instanceof Integer) {
                    array.push((Integer) item);
                }else if (item instanceof Boolean){
                    array.push((Boolean) item);
                }
            }
        }
        return array;
    }

    /**
     * 获取js方法中返回string类型的数据
     *
     * @param v8
     * @param name  js函数名
     * @param array 上传参数
     * @return 返回字符串
     */
    public static String v8StringFunction(V8 v8, String name, V8Array array) {
        String result = "";
        try {
            if (v8 != null && !v8.isReleased()) {
                if (null != array) {
                    result = v8.executeStringFunction(name, array);
                    array.release();
                } else {
                    result = v8.executeStringFunction(name, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取js方法中返回boolean类型的数据
     *
     * @param v8
     * @param name  js函数名
     * @param array 上传参数
     * @return 返回值
     */
    public static Boolean v8BooleanFunction(V8 v8, String name, V8Array array) {
        boolean result = false;
        try {
            if (v8 != null && !v8.isReleased()) {
                if (null != array) {
                    result = v8.executeBooleanFunction(name, array);
                    array.release();
                } else {
                    result = v8.executeBooleanFunction(name, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取js方法中返回Int类型的数据
     *
     * @param v8
     * @param name  js函数名
     * @param array 上传参数
     * @return 返回值
     */
    public static Integer v8IntFunction(V8 v8, String name, V8Array array) {
        int result = 0;
        try {
            if (v8 != null && !v8.isReleased()) {
                if (null != array) {
                    result = v8.executeIntegerFunction(name, array);
                    array.release();
                } else {
                    result = v8.executeIntegerFunction(name, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取js函数
     *
     * @param v8
     * @param name  函数名
     * @param array 上传参数
     */
    public static void v8Void(V8 v8, String name, V8Array array, V8Function javaCallback) {
        try {
            if (v8 != null && !v8.isReleased()) {
                if (null != array) {
                    array.push(javaCallback);
                    v8.executeVoidFunction(name, array);
                    array.release();
                } else {
                    v8.executeVoidFunction(name, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }
}
