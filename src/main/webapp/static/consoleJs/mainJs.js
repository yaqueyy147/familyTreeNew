/**
 * Created by suyx on 2017/1/16.
 */
$(function () {

});
function loadTab(tabId,tabTitle,tabUrl) {
    $("#tabTT").tabs('add',{
        title:tabTitle,
        content:"<iframe src=\"" + projectUrl + tabUrl + "\"></iframe>",
        closable:true
    });
}