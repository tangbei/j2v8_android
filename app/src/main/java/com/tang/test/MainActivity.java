package com.tang.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Function;
import com.eclipsesource.v8.V8Object;
import com.tang.test.j2v8.J2V8Helper;
import com.tang.test.j2v8.J2V8Interface;
import com.tang.test.j2v8.J2V8InterfaceImpl;
import com.tang.test.j2v8.J2V8Util;
import com.tang.test.j2v8.J2V8ValueCallBack;
import com.tang.test.j2v8.J2v8Example;
import com.tang.test.j2v8.JavaCallbackImpl;
import com.tang.test.j2v8.JavaVoidCallbackImpl;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements J2V8Interface {

    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvContent = findViewById(R.id.tv_content);
    }


    public void jav8OnClick(View view){
//        localRead();

        readJs();
    }

    private void localRead(){

        J2v8Example.getJs(this,new JavaCallbackImpl(),new JavaVoidCallbackImpl(),new J2V8ValueCallBack() {
            @Override
            public void getReadValue(String value) {
                tvContent.setText(value);
            }
        });
    }

    /**
     * js脚本 字符串
     * @return
     */
    @Override
    public String getJs() {
       /* String s = "function j2v8String(x,y){\n" +
                "    var name3 = x.concat(y);\n" +
                "    return name3;\n" +
                "}";*/
        return "";
    }

    private void readJs(){
        List<Object> list = new ArrayList<>();
        list.add("我是j2v8Helper调取的 参数1,");
        list.add("我是j2v8Helper调取的 参数2");
        String jsString = J2V8Helper.engineJs(this,"j2v8String",list);
        tvContent.setText(jsString);
    }

}
