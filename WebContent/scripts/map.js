/**
 * Created by ZCC on 2017/6/3.
 */


var option_array=[]
var option_real=[]
var chartname_arr=[]
function  toIntArray(tmp){
    ss=tmp.replace("[","").replace("]","")
    sarry=ss.split(",")
    intArray=[]
    $.each(sarry,function(index,value) {
        intArray.push(parseInt(value))
    });
return intArray
}

define([], function() {
    return {

        initMap:function(echarts){
            $.ajax({
                url: "http://localhost:8080/FlightSight//user/ChartMap",
                type: "GET",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                dataType: "text",
                success: function (msg) {
                    console.log(msg)
                    var result = $.parseJSON(msg);
                    $("#select").children().remove()
                    $.each(result,function(index,value){
                        var option_select = {
                            backgroundColor:'',
                            grid : {
                                x:'5%',
                                y:'5%',
                                x2:'5%',
                                y2:'5%',

                            },
                            xAxis: {
                                data: []
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
                                    data:[]
                                }
                            ]
                        };

                        option_select.xAxis.data=toIntArray(value.xdata)
                        option_select.series[0].data=toIntArray(value.ydata)
                        $("#select").append("<p><div id=select"+index+"></div><p style='text-align: center'><label>"+value.chartname+"</label></p></p>")
                        $("#select"+index).css("height",100)
                        var mychart = echarts.init(document.getElementById("select"+index));
                        mychart.setOption( option_select)
                        option_real[index]=option_select
                         chartname_arr[index]=value.chartname
                        $("#select"+index).click(function(){

                            if(option_real[index].backgroundColor=="") {
                                var mychart = echarts.init(document.getElementById("select"+index));
                                option_real[index].backgroundColor = "#886600"
                                mychart.setOption(option_real[index])
                            }
                            else{
                                return
                            }
                            option_array.push(value)

                            var option_right_arr=[]
                            for( i=0;i<option_array.length;i++)
                            {
                                var option_right = {
                                    title:{
                                      text:""
                                    },

                                    toolbox: {
                                        show : true,
                                        feature : {
                                            mark : {show: true},
                                            magicType : {show: true, type: ['line', 'bar']},
                                            restore : {show: true},
                                            saveAsImage : {show: true}
                                        }
                                    },
                                    tooltip : {
                                        trigger: 'item',
                                        formatter: "{a} <br/>{b} : {c}"
                                    },
                                    xAxis: {
                                        data: []
                                    },
                                    yAxis: {},
                                    series : [
                                        {
                                            name: '事件分布',
                                            type: 'bar',
                                            itemStyle: {
                                                normal: {
                                                    //好，这里就是重头戏了，定义一个list，然后根据所以取得不同的值，这样就实现了，
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
                                            data:[]
                                        }
                                    ]
                                };
                                option_right.xAxis.data = toIntArray(option_array[i].xdata)
                                option_right.series[0].data = toIntArray(option_array[i].ydata)
                                option_right.title.text=option_array[i].chartname
                                option_right_arr[i]=option_right
                            }

                                var mychart = echarts.init(document.getElementById('map'));
                                $("#map").children().remove()
                                height=$(".content .content").height()
                                row=Math.round(Math.sqrt(option_array.length))
                                if(option_array.length%row==0){
                                    row_count=option_array.length/row
                                }else {
                                    row_count = Math.round(option_array.length / row + 0.5)
                                }
                                for(var i=0;i<row;i++){

                                    for(var j=0;i*row_count+j<(i+1)*row_count;j++){
                                        count=i*row_count+j
                                        $("#map").append("<div id=pic"+count+"></div>")
                                        if(i*row_count+j+1==option_array.length&&option_array.length%row!=0){
                                            row_count=option_array.length%row
                                        }
                                        $("#pic"+count).attr("class", "col-md-"+Math.floor(10/row_count))
                                        $("#pic"+count).css("height",height/row)
                                        echarts.init(document.getElementById('pic'+count)).setOption( option_right_arr[count]);

                                        if(i*row_count+j+1>=option_array.length){
                                            break;
                                        }

                                    }
                                }

                        })
                    })


                }
            })
        },
        test: function (echarts) {
            $("#add").click(function(){
                var mychart = echarts.init(document.getElementById('map'));
                option = {
                    tooltip : {
                        trigger: 'axis'
                    },
                    legend: {
                        data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    calculable : true,
                    xAxis : [
                        {
                            type : 'category',
                            boundaryGap : false,
                            data : ['周一','周二','周三','周四','周五','周六','周日']
                        }
                    ],
                    yAxis : [
                        {
                            type : 'value'
                        }
                    ],
                    series : [
                        {
                            name:'邮件营销',
                            type:'line',
                            stack: '总量',
                            itemStyle: {normal: {areaStyle: {type: 'default'}}},
                            data:[120, 132, 101, 134, 90, 230, 210]
                        },
                        {
                            name:'联盟广告',
                            type:'line',
                            stack: '总量',
                            itemStyle: {normal: {areaStyle: {type: 'default'}}},
                            data:[220, 182, 191, 234, 290, 330, 310]
                        },
                        {
                            name:'视频广告',
                            type:'line',
                            stack: '总量',
                            itemStyle: {normal: {areaStyle: {type: 'default'}}},
                            data:[150, 232, 201, 154, 190, 330, 410]
                        },
                        {
                            name:'直接访问',
                            type:'line',
                            stack: '总量',
                            itemStyle: {normal: {areaStyle: {type: 'default'}}},
                            data:[320, 332, 301, 334, 390, 330, 320]
                        },
                        {
                            name:'搜索引擎',
                            type:'line',
                            stack: '总量',
                            itemStyle: {normal: {areaStyle: {type: 'default'}}},
                            data:[820, 932, 901, 934, 1290, 1330, 1320]
                        }
                    ]
                };



                $("#map").children().remove()
                pic.push("0")
                length=$("#map").width()
                height=$("#map").height()
                row=Math.round(Math.sqrt(pic.length))
                row_count=Math.round(pic.length/row+0.5)
               for(var i=0;i<row;i++){
                   $("#map").append("<div class=row id=row"+i+"></div>")
                   for(var j=0;i*row_count+j<(i+1)*row_count;j++){
                       count=i*row_count+j
                       $("#row"+i).append("<div id=pic"+count+"></div>")
                       $("#pic"+count).attr("class", "col-md-"+Math.floor(12/row_count))
                       $("#pic"+count).css("height",height/row)
                       var mychart = echarts.init(document.getElementById('pic'+count));
                       mychart.setOption(option)
                       if(i*row_count+j+1>=pic.length){
                           break;
                       }

                   }
                }




            })



        }
    }
})