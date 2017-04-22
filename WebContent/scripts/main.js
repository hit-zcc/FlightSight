/**
 * Created by ZCC on 2017/4/20.
 */
require.config({
    baseUrl: 'scripts/',
    paths : {
        "jQuery" : "jquery",
        "log" : "log",
        "fileinput" : "fileinput"
    }
})


requirejs(['log','jQuery','fileinput'], function(log,jq,file) {
    log.initFileInput("input-1","G:\\Data\\");
/*    var dir=$("#submit-1")
    dir.click(function(){
       log.logsubmit(dir.val())
    })*/
});
