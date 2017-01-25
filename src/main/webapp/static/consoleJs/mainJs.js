/**
 * Created by suyx on 2017/1/16.
 */
$(function () {

});
function loadTab(tabId,tabTitle,tabUrl) {
    if ($('#tabTT').tabs('exists', tabTitle)){
        $('#tabTT').tabs('select', tabTitle);
    }else{
        $("#tabTT").tabs('add',{
            title:tabTitle,
            content:"<iframe src=\"" + projectUrl + tabUrl + "\"></iframe>",
            closable:true
        });
    }

}