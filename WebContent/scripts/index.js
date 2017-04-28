/**
 * Created by ZCC on 2017/4/25.
 */

define([], function() {
        return {
            chartpreferences: function (echarts) {
                var obj = []
                var mychart = echarts.init(document.getElementById('chartpreferences'));
                $.ajax({
                    url: "http://localhost:8080/FlightSight/main/initpic",
                    type : "GET",
                    data: {type:"chartpreferences"},
                    contentType:"text/html;charset=utf-8",
                    dataType:"text",
                    success: function (msg) {
                        console.log(msg)
                        var result=$.parseJSON( msg );

                        $.each(result,function(index,value) {
                            var tmp={};
                            tmp.name =value.key;
                            tmp.value =value.doc_count;
                            obj.push(tmp);
                        });

                        var option = {
                            tooltip : {
                                trigger: 'item',
                                formatter: "{a} <br/>{b} : {c} ({d}%)"
                            },
                            series : [
                                {
                                    name: '访问来源',
                                    type: 'pie',
                                    radius: '55%',
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
                });
            },
            chartHours: function (echarts) {
                var xaxis=[]
                var obj = []
                var mychart = echarts.init(document.getElementById('chartHours'));
                $.ajax({
                    url: "http://localhost:8080/FlightSight/main/initpic",
                    type : "GET",
                    data: {type:"chartHours"},
                    contentType:"text/html;charset=utf-8",
                    dataType:"text",
                    success: function (msg) {
                        console.log(msg)
                        var result=$.parseJSON( msg );

                        $.each(result,function(index,value) {
                            obj.push(value.doc_count);
                            xaxis.push(value.key_as_string)
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
                });

            },
            sharedsAndReplicas: function (echarts) {
                var xaxis=[]
                var shareds= []
                var Replicas= []

                var mychart = echarts.init(document.getElementById('sharedsAndReplicas'));
                $.ajax({
                    url: "http://localhost:8080/FlightSight/main/initpic",
                    type : "GET",
                    data: {type:"sharedsAndReplicas"},
                    contentType:"text/html;charset=utf-8",
                    dataType:"text",
                    success: function (msg) {
                        console.log(msg)
                        var result=$.parseJSON( msg );

                        $.each(result,function(index,value) {
                            if (index == 0) {

                                for (var key in value) {

                                    xaxis.push(key)
                                    shareds.push(value[key])
                                }
                            }
                            else
                            {
                                for (var key in value) {

                                    Replicas.push(value[key])
                                }
                            }
                        });

                        console.log(Replicas)
                        console.log(shareds)
                        console.log(xaxis)
                        // 使用刚指定的配置项和数据显示图表。
                        option = {
                            tooltip : {
                                trigger: 'axis'
                            },
                            legend: {
                                data:['Shareds','Replicas']
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
                            calculable : true,
                            xAxis : [
                                {
                                    type : 'category',
                                    boundaryGap : false,
                                    data : xaxis
                                }
                            ],
                            yAxis : [
                                {
                                    type : 'value',
                                    axisLabel : {
                                        formatter: '{value} '
                                    }
                                }
                            ],
                            series : [
                                {
                                    name:'Shareds',
                                    type:'line',
                                    data:shareds,
                                },
                                {
                                    name:'Replicas',
                                    type:'line',
                                    data:Replicas,
                                }
                            ]
                        };
                        mychart.setOption(option);
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