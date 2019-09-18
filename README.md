# j2v8的android测试demo。

本工程只是 j2v8 测试demo, 包含了j2v8的所有使用方法。可以下载 工程体验。

#### j2v8使用方式：
1. 在build.gradle中添加依赖：
```java
dependencies {
    compile 'com.eclipsesource.j2v8:j2v8:5.0.103@aar'
}
```
2. 也可以在app的libs中引入j2v8.aar文件。[点击获取maven地址](https://mvnrepository.com/artifact/com.eclipsesource.j2v8/j2v8)
   同时在build.gradle中引入：
   ```
   repositories {
       flatDir {
           dirs 'libs'
       }
   }
   ```

#### 后台java使用方式:

maven依赖方式：
```maven
<dependency>
    <groupId>com.eclipsesource.j2v8</groupId>
    <artifactId>j2v8</artifactId>
    <version>5.0.103</version>
</dependency>
```
java后台使用方式，和android类似，只是其中的j2v8方法还有使用上的区别。

#### 工程demo使用介绍:

本工程已经包含了 所有j2v8使用到的方法，和使用到的工具类。以及包含了三种读取 js 文件的方式，方便在任何场景下 java 和 js的交互。

#### 一、本地 js文件/js字符串 读取方式

在主工程app的assets文件夹中添加js脚本文件，如果没有assets文件夹，则新建一个。
![image](https://github.com/tangbei/j2v8_android/blob/master/20190918135108.jpg)

获取js脚本文件的方式：
``` 
final InputStream INPUTSTREAM = this.activity.getClass().getResourceAsStream("/assets/j2v8/j2v8test.js");//获取js脚本的输入流
```

#### 二、后台数据库js的string字符串 读取方式

在需要调取js的类中 implements J2V8Interface这个接口，同时实现 getJs()方法。
```java
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
```

此读取方式，更加灵活，可以直接把js存到后台数据库，每次调用的时候 拿到后台的js字符串即可。
同时调用`J2V8Helper.class`暴露的帮助实现方法 即可，demo写的很详细，一些方法还是比较全面的，同时你可以自己更改代码，获取自己想要的实现方式，并放在自己的工程中。 具体可下载工程查看使用方式。 /n

注意事项：**存的是js脚本字符串、或者js脚本字符串方法**。


#### 三、服务器js文件 读取方式

js文件既可以本地读取，那么 服务器链接 读取肯定也是可以的。

```java
/**
    * 获取oss上的js文件，并转换成String返回
    * 如果是获取网络地址，则使用 J2V8Single.class 处理
    * @param _url oss上的js地址：https://github.com/tangbei/j2v8_android/blob/master/j2v8test.js
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
```

但是调取方式和第一种不同，是在 `J2V8Single.class`单例类中使用，目的是只网络读取一遍js脚本文件。


#### 读取方式优劣分析
1. 三种方式在读取上第二种是最快的，直接拿到的是js脚本字符串。
2. 本地读取和本地读取字符串也是最快、但是不可动态配置js脚本，不灵活。
3. 服务器读取虽然动态可配置，但是读取速度上不占优，使用单例模式加载网络文件，目的是使用时只读取一遍，提升代码可用度。

综上，还是推荐数据库存储js字符串 读取、即可动态配置、也不失灵活、读取便捷、扩展性强、适用场景更多。



#### 注意事项
1. 本demo只是粗略概括了`j2v8`的各种使用方式，为初次使用 jav8 的人搭个桥。
2. 使用j2v8时一定要及时释放，否则会很耗性能和内存。
3. 如果java后台使用j2v8 也可以使用此demo,只是依赖替换一下，具体使用大同小异。
4. 本demo，可以随意更改，并用到自己的工程中，希望能帮到你们。
