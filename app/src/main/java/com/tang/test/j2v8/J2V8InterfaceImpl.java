package com.tang.test.j2v8;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/8/28.
 */
public class J2V8InterfaceImpl implements J2V8Interface {

    public String jsString;//数据库中存入的js脚本函数

    public J2V8InterfaceImpl(){

    }

    public J2V8InterfaceImpl(String jsString) {
        this.jsString = jsString;
    }

    @Override
    public void Log(String log) {
        System.out.println(log);
    }

    @Override
    public String getJs() {
        return jsString;
    }

}
