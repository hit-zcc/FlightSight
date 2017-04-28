/**
 * Created by ZCC on 2017/4/20.
 */

function logsubmit() {
    var obj = {}
    var dir=$('#input-1').val();
    obj['name'] = dir;
    $.ajax({
        url: "http://localhost:8080/FlightSight/log/test",
        type : "POST",
        data : JSON.stringify(obj),
        dataType: "json",
        contentType:"application/json;charset=utf-8",
        success: function (msg) {

               $('#logContent')[0].innerHTML=msg;
               $('#inputlog').show();
        },
        error:function(ajaxobj)
        {
            if(ajaxobj.responseText!='')
                alert(ajaxobj.responseText);
        }
    });
}
function  logContent(){
    $('#splitheader').show();
    body=$('#splitbody');
    body.show();
    tbody=$('#tbody');
    tbody.children().remove()
     splite_input=$('#split-input').val()
     content= $('#logContent')[0].innerHTML;
     var arr =content.split(splite_input);
     $.each(arr,function(index,value){
        console.log(value);
         aid='tr'+index
         tid='type'+index
         tbody.append('<tr><td>'+index+'</td><td>'+value+'</td><td><a href="#" id='+aid+'>不分析</a></td><td><a href="#" id='+tid+'>String</a></td></tr>')

         requirejs(['log','jQuery','fileinput',"bootstrap-editable"], function(log,jq,file,edit) {
             $(function () {
                 $('#tr'+index).editable(
                 {
                     type: "text",                //编辑框的类型。支持text|textarea|select|date|checklist等
                     disabled: false,             //是否禁用编辑
                     emptytext: "空文本",          //空值的默认文本
                     mode: "inline",              //编辑框的模式：支持popup和inline两种模式，默认是popup
                     validate: function (value) { //字段验证
                         if (!$.trim(value)) {
                             return '不能为空';
                         }
                     }
                 }
             )
             })

         });



         requirejs(['log','jQuery','fileinput',"bootstrap-editable"], function(log,jq,file,edit) {
             $(function () {
                 $('#type'+index).editable(
                     {
                         type: "select",                //编辑框的类型。支持text|textarea|select|date|checklist等
                         source: [{ value: 1, text: "String" }, { value: 2, text: "Int" }, {value:3,text:"Float"}],
                         disabled: false,             //是否禁用编辑
                         emptytext: "空文本",          //空值的默认文本
                         mode: "inline",              //编辑框的模式：支持popup和inline两种模式，默认是popup
                         validate: function (value) { //字段验证
                             if (!$.trim(value)) {
                                 return '不能为空';
                             }
                         }
                     }
                 )
             })

         });

    })

}
function  FormSubmit(){
    var submitBody = {}
    var table={}
    var trList = $('#tbody').children("tr")
    trList.each(function(index,value) {
        id=trList.eq(index).find("td")[0].innerText
        val=trList.eq(index).find("td")[1].innerText
        name=trList.eq(index).find("td")[2].innerText
        type=trList.eq(index).find("td")[3].innerText
        table[id]=id+','+val+','+name+','+type
    })
    submitBody['Dir']=$('#input-1').val();
    submitBody['table']=JSON.stringify(table)
    submitBody['Index']=$('#Index').val()
    submitBody['Tags']=$('#Tags').val()
    submitBody['Add']=$('#Add').val()
    submitBody['Start']=$('#Start').val()
    submitBody['Split']=$('#split-input').val()
    $.ajax({
        url: "http://localhost:8080/FlightSight/log/FormSubmit",
        type : "POST",
        data : JSON.stringify(submitBody),
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
    function logsubmit(dir) {
        alert(dir);
    }
    return {
        hello: function() {
            alert("hello, app~");
        },
        initFileInput:function (ctrlName,uploadUrl) {
            var control = $('#' + ctrlName);
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