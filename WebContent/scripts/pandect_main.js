/**
 * Created by ZCC on 2017/4/29.
 */
require.config({
    baseUrl: 'scripts/',
    paths : {
        "jQuery" : "jquery",
        "pandect":"pandect"
    }
})


requirejs(['pandect','jQuery'], function(pandect,jq) {
    pandect.initLogUser();

});
