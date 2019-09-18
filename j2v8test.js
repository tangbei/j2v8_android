
function j2v8String(x,y){


    var name3 = x.concat(y);
    return name3;
}

function j2v8Int(x,y){

    var name3 = x + y;
    return name3;
}

function j2v8Boolean(x,y){

    return x == y;
}

function putVoid(x,y){
    var array = [{name:'zhangSan'}, {name:'liSi'}, {name:'wangMaZi'}];
    for ( var i = 0; i < array.length; i++ ) {
      javaVoid.call(array[i], " 你好啊"); // javaVoid 是 java 注册到 js 的一个函数
    }
    return j2v8Int(x,y);
}

var j2v8New = {
    a: "111",
    b: "222"
};

function handle(x,y,callBack){
    callBack(x+y);
}

function javaCallBack(x,y){

    var t = javaBack(x+y);//javaBack是 java注册到js中的一个回调函数
    return t;
}


