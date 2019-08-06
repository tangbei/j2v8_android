package com.tang.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Function;
import com.eclipsesource.v8.V8Object;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text2();
    }

    public void text(){
        V8 v8 = V8.createV8Runtime();
        int result = v8.executeIntegerScript("1+1");
        Log.d("test---------v8->",String.valueOf(result));
        v8.release();
    }

    private void text2(){
        final V8 v8 = V8.createV8Runtime();
        J2V8ReadJavaScript readJavaScript = new J2V8ReadJavaScript(this, new AsyncTaskBackListener<String>()
        {
            @Override
            public void onAsyncTaskCallBack(String s)
            {

                if (v8 != null && !v8.isReleased())
                {
                    v8.executeVoidScript(s);//拿到当前js的方法
                    v8.registerJavaMethod(voidCallback,"putString");
                    List<Object> list1 = new ArrayList<>();
                    list1.add("吴彦祖");
                    list1.add("是帅哥哦");
                    Log.d("test------v8String-->",J2V8Util.v8StringFunction(v8,"j2v8String",J2V8Util.getV8Array(v8,list1)));

                    List<Object> list2 = new ArrayList<>();
                    list2.add(22);
                    list2.add(33);
                    Log.d("tang------v8Integer-->",""+J2V8Util.v8IntFunction(v8,"j2v8Int",J2V8Util.getV8Array(v8,list2)));

                    List<Object> list3 = new ArrayList<>();
                    list3.add("aaa");
                    list3.add("aaa");
                    Log.d("test------v8boolean-->",""+J2V8Util.v8BooleanFunction(v8,"j2v8Boolean",J2V8Util.getV8Array(v8,list3)));

                    //判断是否是函数
                    if (v8.getType("putVoid") == V8.V8_FUNCTION){
                        List<Object> list4 = new ArrayList<>();
                        list4.add(234);
                        list4.add(33);
                        V8Function call = (V8Function) v8.getObject("putVoid");
                        int ss = (int) call.call(null,J2V8Util.getV8Array(v8,list4));
                        Log.d("test------v8void-->",""+ss);
                        call.close();
                    }

                    List<Object> list5 = new ArrayList<>();
                    list5.add(111);
                    list5.add(11);
                    V8Function function = new V8Function(v8,javaCallback);
                    J2V8Util.v8Void(v8,"handle",J2V8Util.getV8Array(v8,list5),function);
                }
            }
        }, "j2v8/j2v8test.js");

        readJavaScript.execute();
    }

    JavaVoidCallback voidCallback = new JavaVoidCallback() {
        @Override
        public void invoke(V8Object v8Object, V8Array v8Array) {
            Log.d("test--v8Voidallback--->",""+v8Object.getString("first") + v8Array.get(0));
        }
    };

    JavaCallback javaCallback = new JavaCallback() {
        @Override
        public Object invoke(V8Object v8Object, V8Array v8Array) {
            Log.d("test-----v8Callback--->",""+ v8Array.get(0));
            return null;
        }
    };


}
