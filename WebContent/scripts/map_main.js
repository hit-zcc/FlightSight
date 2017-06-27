/**
 * Created by ZCC on 2017/6/3.
 */
require.config({
    baseUrl: 'scripts/',
    paths : {
        "jQuery" : "jquery",
        "echarts" : "echarts",
        "map":"map"
    }
})


requirejs(['jQuery',"echarts","map"], function(jq,echarts,map) {
    map.initMap(echarts);
 //   map.test(echarts);
    /*    var dir=$("#submit-1")
     dir.click(function(){
     log.logsubmit(dir.val())
     })*/
});