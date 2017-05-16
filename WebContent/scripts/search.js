/**
 * Created by ZCC on 2017/4/29.
 */

var _mapping={}
var optionString
var TermType="<option>match</option><option>regexp</option>"
var AggsType="<option>sum</option><option>count</option>"
var Relation="<option>must</option><option>should</option>"
var Group="<option>0</option><option>1</option><option>2</option><option>3</option><option>4</option><option>5</option>"
var search={

}
var pagesize

function AddTermValue(name,type,_value,relation,group){
    var en={}
    en["name"]=name;
    en["type"]=type;
    en["_value"]=_value;
    en["relation"]=relation;
    en["group"]=group;
    search["term"].push(en)
    console.log(search)
}


function AddaggsValue(name,type){
    var en={}
    en["name"]=name;
    en["type"]=type;
    search["bucket"].push(en)
    console.log(search)
}

function getMapping(key){
    var j=require("bootstrap-select")
    optionString=""
    $.ajax({
        url: "http://localhost:8080/FlightSight/main/getMpping",
        type: "GET",
        data: {index: key},
        contentType: "text/html;charset=utf-8",
        dataType: "text",
        success: function (msg) {
             console.log(msg)
            var result=$.parseJSON( msg );

            $.each(result,function(key,value) {
                    optionString+="<option>"+ key+"</option>"

            }
            )
        }
    })

}

function pageinit(msg){
    size=msg.length/10
    page=$("#page")
    var options={
        bootstrapMajorVersion:1,    //版本
        currentPage:1,    //当前页数
        numberOfPages:5,    //最多显示Page页
        totalPages:size,    //所有数据可以显示的页数
        onPageClicked:function(e,originalEvent,type,page){
            $("#searchBodyTable").children().remove()
            $.each(msg,function(index,value){
                    if((page-1)*10<=index&&index<page*10) {
                        $("#searchBodyTable").append("<tr><td>" + value._id + "</td><td>" + value._index + "</td><td>" + value._type + "</td><td>" + value._source.message + "</td><tr>")
                    }
                }
            )

        }
    }
    page.bootstrapPaginator(options);

}


function termSubmit(){



        var trList = $('#termTbody').children("tr")
        trList.each(function(index,value) {
            name=trList.eq(index).find("td")[0].innerText.replace("\u00A0\n","")
            type=trList.eq(index).find("td")[1].innerText.replace("\u00A0\n","")
            _value=trList.eq(index).find("input").val().replace("\u00A0\n","")
            ralation=trList.eq(index).find("td")[3].innerText.replace("\u00A0\n","")
            group=trList.eq(index).find("td")[4].innerText.replace("\u00A0\n","")
            AddTermValue(name,type,_value,ralation,group)
        })

}

function aggsSubmit(){


        var trList = $('#aggsTbody').children("tr")
        trList.each(function(index,value) {
            name=trList.eq(index).find("td")[0].innerText.replace("\u00A0\n","")
            type=trList.eq(index).find("td")[1].innerText.replace("\u00A0\n","")
            AddaggsValue(name,type)
        })

}

function parseInputSearch(input){
    try {
        term=[]
        tmp=input.split(",")

        var en={}

        $.each(tmp,function(index,value) {
            en["name"] = value.split(":")[0];
            en["type"] = "match";
            en["_value"] = value.split(":")[1];
            en["relation"] = "must";
            en["group"] = 0;
            term.push(en)
        })
    }
    catch(err) {
        alert("error in input")
    }
    console.log(term)
    return term

}

function searchFunction(search,echarts){
    var xaxis=[]
    var obj = []
    var mychart = echarts.init(document.getElementById('echartBody'));
    $.ajax({
        url: "http://localhost:8080/FlightSight/main/Bodysearch",
        type : "POST",
        data : JSON.stringify(search),
        dataType: "json",
        contentType:"application/json;charset=utf-8",
        success: function (response) {
            //写数据
            console.log(response)
            msg=response.termresult
            pagesize=msg.length

            $.each(msg,function(index,value){
                    if(0<=index&&index<10) {
                        $("#searchBodyTable").append("<tr><td>" + value._id + "</td><td>" + value._index + "</td><td>" + value._type + "</td><td>" + value._source.message + "</td><tr>")
                    }
                }
            )

            //翻页
            size=msg.length
            pageinit(msg)




            //写图标
            result=response.aggsresult
            $.each(result,function(index,value) {
                obj.push(value.doc_count);
                xaxis.push(value.key)
            });

            var option = {
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                grid : {
                    x:'5%',
                    y:'5%',
                    x2:'5%',
                    y2:'5%',

                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c}"
                },
                xAxis: {
                    data: xaxis
                },
                yAxis: {},
                series : [
                    {
                        name: '事件分布',
                        type: 'bar',
                        itemStyle: {
                            normal: {
                                //定义一个list，然后根据所以取得不同的值，这样就实现了，
                                color: function(params) {
                                    // build a color map as your need.
                                    var colorList = [
                                        '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
                                        '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
                                        '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
                                    ];
                                    return colorList[params.dataIndex]
                                }
                            }
                        },
                        data:obj
                    }
                ]
            };

            // 使用刚指定的配置项和数据显示图表。
            mychart.setOption(option);

        },
        error:function(ajaxobj)
        {
            if(ajaxobj.responseText!='')
                alert(ajaxobj.responseText);
        }
    })
}
/*<td>
<select class="selectpicker">
    <option>Mustard</option>
    <option>Ketchup</option>
    <option>Relish</option>
    </select>
    </td>
    <td></td>
    <td>hfghfgh</td><td>ghghghgh</td><td>ghghghgh</td>*/


define(["bootstrap-select"], function(select) {

    return {
        inputsearch:function(echarts,mychart){
            $("#inputclick").click(function(){
                var xaxis=[]
                var obj = []
                var term=[]
                var bucket=[]
                search["term"]=term
                search["bucket"]=bucket
                search["index"] =$('#dropdownbutton').text()
                var input= $("#searchinput")[0].value
                search["term"]=parseInputSearch(input)
                searchFunction(search,echarts)
                console.log(input)
            })
        },
        bodysearch:function(echarts,mychart){


            $("#bodysearch").click(function(){
                var term=[]
                var bucket=[]
                search["term"]=term
                search["bucket"]=bucket
                search["index"] =$('#dropdownbutton').text()
                termSubmit();
                aggsSubmit()

                searchFunction(search,echarts)

        })},


        init:function(echarts){
            var term=[]
            var bucket=[]
            search["term"]=term
            search["bucket"]=bucket
            search["index"] =$('#dropdownbutton').val()
        },
        addTermSearch:function(select){
            $("#addTermSearch").click(function(){
                $("#termTbody").append("<tr><td><select class=selectpicker> "+optionString +"</select></td><td><select class=selectpicker> "+TermType +"</select></td><td><input type=text class=form-control></td><td><select class=selectpicker> "+Relation +"</select></td><td><select class=selectpicker> "+Group +"</select></td><td><a class='btn btn-danger'>delete</a></td></tr>")
                $('.selectpicker').selectpicker();
                $(".btn-danger").click(function(){
                    $(this).parent().parent().remove()
                })

            })
        },
        addAggsSearch:function(select){
            $("#addaggsSearch").click(function(){
                $("#aggsTbody").append("<tr><td><select class=selectpicker> "+optionString +"</select></td><td><select class=selectpicker> "+AggsType +"</select></td><td><a class='btn btn-danger'>delete</a></td></tr>")
                $(".btn-danger").click(function(){
                    $(this).parent().parent().remove()
                })
                $('.selectpicker').selectpicker();
            })
        },
        getIndex: function (echarts) {
            $('#index-ul').children().remove()
            $.ajax({
                url: "http://localhost:8080/FlightSight/main/initpic",
                type: "GET",
                data: {type: "chartpreferences"},
                contentType: "text/html;charset=utf-8",
                dataType: "text",
                success: function (msg) {
                    console.log(msg)
                    var result = $.parseJSON(msg);

                    $.each(result, function (index, value) {
                        $('#index-ul').append("<li id=index-ul_"+index+"><a href='#'>"+value.key+"</a></li>")
                        $('#index-ul_'+index).click(function(){
                            $('#dropdownbutton').text(value.key)
                            getMapping(value.key)

                        })
                    });

                },
                error: function (ajaxobj) {
                    if (ajaxobj.responseText != '')
                        alert(ajaxobj.responseText);
                }
            });
        },
        initEchartBody:function(echarts){
            var mychart = echarts.init(document.getElementById('echartBody'));
            var option = {
                tooltip: {},
               splitLine:{
                   show:true
               },
                grid : {
                    x:'5%',
                    y:'5%',
                    x2:'5%',
                    y2:'5%',

                },
                legend: {
                },
                xAxis: {
                    data: [""]
                },
                yAxis: {},
                series: [{
                    type: 'bar',
                    data:[0]
                }]
            };
            mychart.setOption(option);
            return mychart
        }
    }
}
)