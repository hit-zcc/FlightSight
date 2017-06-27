/**
 * Created by ZCC on 2017/4/29.
 */
require.config({
    baseUrl: 'scripts/',
    paths : {
        //"jQuery" : "jquery",
        "echarts" : "echarts"
       ,"search" : "search"
        ,"bootstrap-select" : "bootstrap-select"
        , "paginator":"bootstrap-paginator"
    }
})


requirejs(["bootstrap-select","echarts","search","paginator"], function(select,echarts,search,paginator) {
    search.getIndex(echarts)
    mychart=search.initEchartBody(echarts)
    search.bodysearch(echarts,mychart)
    search.inputsearch(echarts,mychart)
    //addTermSearch(select)
    search.saveChart();
    $("#addTermSearch").click(function(){
        $("#termTbody").append("<tr><td><select class=selectpicker> "+optionString +"</select></td><td><select class=selectpicker> "+TermType +"</select></td><td><input type=text class=form-control></td><td><select class=selectpicker> "+Relation +"</select></td><td><select class=selectpicker> "+Group +"</select></td><td><a class='btn btn-danger'>delete</a></td></tr>")
        $('.selectpicker').selectpicker();
        $(".btn-danger").click(function(){
            $(this).parent().parent().remove()
        })
    })
    search.addAggsSearch(select)
    search.init()
    /*    var dir=$("#submit-1")
     dir.click(function(){
     log.logsubmit(dir.val())
     })*/
});
function addTermSearch(select){
    $("#addTermSearch").click(function(){
        $("#termTbody").append("<tr><td><select class=selectpicker> "+optionString +"</select></td><td><select class=selectpicker> "+TermType +"</select></td><td><input type=text class=form-control></td><td><select class=selectpicker> "+Relation +"</select></td><td><select class=selectpicker> "+Group +"</select></td><td><a class='btn btn-danger'>delete</a></td></tr>")
        $('.selectpicker').selectpicker();
        $(".btn-danger").click(function(){
            $(this).parent().parent().remove()
        })

    })
}
