/**
 * Created by ZCC on 2017/4/26.
 */
require.config({
    baseUrl: 'scripts/',
    paths : {
        "jQuery" : "jquery",
        "log" : "log",
        "fileinput" : "fileinput",
        "editable" : "bootstrap-editable",
        "echarts" : "echarts",
        "index":"index"
    }
})


requirejs(['jQuery','fileinput',"bootstrap-editable","echarts","index"], function(jq,file,edit,echarts,index) {
    index.chartpreferences(echarts);
    index.chartHours(echarts);
    index.sharedsAndReplicas(echarts);
    index.initLogin();
    index.initLoginout();
    index.initregister();
    /*    var dir=$("#submit-1")
     dir.click(function(){
     log.logsubmit(dir.val())
     })*/
});