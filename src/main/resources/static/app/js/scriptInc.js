var _beforeUnload_time = 0, _gap_time = 0;
window.onunload = function (){
    _gap_time = new Date().getTime() - _beforeUnload_time;
    console.log(_gap_time)
    if(_gap_time <= 5){//浏览器关闭
        window.location.href="/forward/logout";
    }else{//浏览器刷新
        console.log(document.domain);
       // return confirm("您确定要离开系统么？chrome刷新");
    }
}
window.onbeforeunload = function (){
    _beforeUnload_time = new Date().getTime();
};
