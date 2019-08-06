
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
    var array1 = [{first:'Ian'}, {first:'Jordi'}, {first:'Holger'}];
    for ( var i = 0; i < array1.length; i++ ) {
      putString.call(array1[i], " says Hi."); // putString 是 java 注册到 js 的一个函数
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


