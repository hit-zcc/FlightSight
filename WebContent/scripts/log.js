/**
 * Created by ZCC on 2017/4/20.
 */

function logsubmit() {
    var obj = {}
    alert($('#input-1').val());
    var dir=$('#input-1').val();
    obj['name'] = dir;
    alert(JSON.stringify(obj));
    $.ajax({
        url: "http://localhost:8080/FlightSight/log/test",
        type : "POST",
        data : JSON.stringify(obj),
        dataType: "json",
        contentType:"application/json;charset=utf-8",
        success: function (msg) {
                alert(msg)
        },
        error:function(ajaxobj)
        {
            if(ajaxobj.responseText!='')
                alert(ajaxobj.responseText);
        }
    });
}
define([], function() {
    function dd() {
        alert("hello, sdsd~");
    }
    function logsubmit(dir) {
        alert(dir);
    }
    return {
        hello: function() {
            alert("hello, app~");
        },
        initFileInput:function (ctrlName,uploadUrl) {
            var control = $('#' + ctrlName);
            alert("hello, app~");
            control.fileinput({
                language: 'zh', //设置语言
                uploadUrl:uploadUrl,
                allowedFileExtensions : ['txt'],//接收的文件后缀
                showUpload: false, //是否显示上传按钮
                showCaption: false,//是否显示标题
                showPreview : true,//是否预览
            });
    },
        logsubmit :function(dir) {
        alert(dir);
    }

    }
});