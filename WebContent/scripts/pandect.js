/**
 * Created by ZCC on 2017/5/26.
 */
var indexinfo={}
var entity="<div class=col-md-12><div class=col-md-4> <div class=card> <div class=header> <h4 class=title>LogName</h4> <p class=category>Username</p><p class=category> </p> <table class='table table-hover table-striped'><tbody id=tableinfo style='display: none;'> </tbody></table><p><label>ID：uuid</label></p><p><label>创建时间：time</label></p><p><label>记录条数：count</label><a class='btn btn-default pull-right' id='delete'>删除 </a></p><a href='#' id=downid> <span class='glyphicon glyphicon-eye-open'></span> </a> </div></div> </div> </div>"

function fillHtml(info,count){
    time_tmp=new Date(Number(indexinfo.date)).toLocaleString().replace(/:\d{1,2}$/,' ')
    $(".row").append(entity.replace("time",time_tmp).replace("count",indexinfo.count).replace("uuid",indexinfo.uuid).replace("LogName",info.Logname).replace("Username",info.name).replace("tableinfo","tableinfo"+count).replace("downid","downid"+count).replace("delete","delete"+count))
    getMapping(info.Logname,count)
    $("#downid"+count).click(function(){

        if($("#tableinfo"+count).is(":hidden")) {
            $("#downid"+count).parent().parent().parent().attr("class", "col-md-12")
        }
        else{
            $("#downid"+count).parent().parent().parent().attr("class", "col-md-4")
        }
        $("#tableinfo"+count).slideToggle(0);
    })
    $("#delete"+count).click(function(){
        $.ajax({
            url: "http://localhost:8080/FlightSight/user/deleteLog",
            type: "GET",
            data: {index: info.Logname},
            contentType: "text/html;charset=utf-8",
            dataType: "text",
            success: function (msg) {
                console.log(msg)

            }
        })
    })
}
function getMapping(key,count) {
    $.ajax({
        url: "http://localhost:8080/FlightSight/main/getMpping",
        type: "GET",
        data: {index: key},
        contentType: "text/html;charset=utf-8",
        dataType: "text",
        success: function (msg) {
            console.log(msg)
            var result = $.parseJSON(msg);
            i=1
            tmp=""
            $.each(result,function(index,value){
                    tmp= tmp.concat("<td>" + index + "</td><td>" + value.type + "</td>")
                if(i%3==0){
                    $("#tableinfo"+count).append("<tr>"+tmp+"<tr>")
                    tmp=""
                }
                i++;
            }
            )
            $("#tableinfo"+count).append("<tr>"+tmp+"<tr>")
        }
    })
}
function IndexInfo(info,count){
     indexinfo={}
    $.ajax({
        url: "http://localhost:8080/FlightSight/user/IndexInfo",
        type: "GET",
        data: {name:info.Logname},
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (msg) {
            indexinfo['date']=msg[info.Logname]['creation_date']
            indexinfo['uuid']=msg[info.Logname]['uuid']
                console.log(msg)

        }
    }),
        $.ajax({
            url: "http://localhost:8080/FlightSight/main/initpic",
            type : "GET",
            data: {type:"chartpreferences"},
            contentType:"text/html;charset=utf-8",
            dataType:"text",
            success: function (msg) {
                console.log(msg)
                var result=$.parseJSON( msg );
                $.each(result,function(key,value) {
                    if(value.key==info.Logname){
                        indexinfo['count']=value.doc_count
                        fillHtml(info,count)
                    }
                });
            },
            error:function(ajaxobj)
            {
                if(ajaxobj.responseText!='')
                    alert(ajaxobj.responseText);
            }
        });

}
define([], function() {

        return {
            initLogUser:function ()
        {
            var count=0
            $.ajax({
                url: "http://localhost:8080/FlightSight/user/logUser",
                type: "GET",
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                success: function (msg) {
                    if (msg.status == "OK") {
                        result=msg.content
                        $.each(result,function(index,value){


                            count++
                            IndexInfo(value,count);
                            console.log(indexinfo)

                        })
                        console.log(msg)
                    }

                }
            });
        },
            IndexInfo:function(){
                $.ajax({
                    url: "http://localhost:8080/FlightSight/user/IndexInfo",
                    type: "GET",
                    data: {name:"chartpreferences"},
                    contentType: "application/json;charset=utf-8",
                    dataType: "json",
                    success: function (msg) {
                        if (msg.status == "OK") {

                            console.log(msg)
                        }

                    }
                }),
                $.ajax({
                    url: "http://localhost:8080/FlightSight/main/initpic",
                    type : "GET",
                    data: {type:"chartpreferences"},
                    contentType:"text/html;charset=utf-8",
                    dataType:"text",
                    success: function (msg) {
                        console.log(msg)
                    },
                    error:function(ajaxobj)
                    {
                        if(ajaxobj.responseText!='')
                            alert(ajaxobj.responseText);
                    }
                });

            }
    }
    }
)