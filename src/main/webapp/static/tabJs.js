/**
 * Created by suyx on 2016/11/23 0023.
 */
var bodyHeight = $(document.body).height();
var tabTop = $(".pageP").position().top;
var footTop = $(".footPanel").position().top;
function loadTab(obj,tabName,tabUrl,tabId){

    //当前menuId
    var menuId = $(obj).attr("id");
    //查看当前tabId的元素是否已经存在了
    var temp = $("#" + tabId).html();
    //若存在，则不打开新标签
    if(temp != null && $.trim(temp).length > 0){
        tabActive4Id(tabId,menuId);
        menuActive(obj);
        return;
    }
    var iframeHeight = footTop - tabTop - 1;
    var tab = "<li>";
    tab += "<a href=\"javascript:void(0)\" onclick=\"selectTab(this,\'" + menuId + "\')\">"+tabName+"</a>";
    //设置关闭按钮，并将关联的菜单ID设置为按钮的name属性，以供关闭tab时激活前一tab和相应菜单时使用
    tab += "<span name=\"" + menuId + "\" onclick=\"closeTab(this)\">";
    tab += "&times;";
    tab += "</span>";//</a>
    tab += "</li>";
    $(".tabP ul").append(tab);
    var tabContent = "<div id=\""+tabId+"\">";
    tabContent += "<iframe src='"+ tabUrl +"' frameborder='0' style='height: "+iframeHeight+"px;width: 100%' ></iframe>";
    tabContent += "</div>";
    $(".pageP").append(tabContent);

    tabActive4Id(tabId,menuId);
    menuActive(obj);
}
//关闭标签页
function closeTab(obj){

    var activeIndex = getActiveTabIndex();
    //删除本节点
    var index = $(obj).parent().index();
    $(".tabP ul").find("li:eq(" + index + ")").remove();
    $(".pageP").find("div:eq(" + index + ")").remove();
    //如果是关闭的当前激活节点，激活前一节点
    if(activeIndex == index){
        if(index > 0){
            index = index-1;
        }
        //前一节点的关闭按钮的name属性，这是事先设置好的相关联的菜单ID
        var menuId = $(".tabP ul").find("li:eq(" + index + ")").find("span").attr("name");
        //激活tab和菜单
        tabActive4Index(index,menuId);
    }

}

//获取当前激活的tab的index
function getActiveTabIndex(){
    var tabs = $(".tabP ul li");
    var index = 0;
    for(var i = 0;i<tabs.length;i++){
        var ii = tabs[i];
        if($(ii).hasClass("tabActive")){
            index = $(ii).index();
        }
    }
    return index;
}

//根据tabId激活tab
function tabActive4Id(taId,menuId) {
    //获取当前tab的index
    var index = $("#" + taId).index();
    //根据index激活tab
    tabActive4Index(index,menuId);
}

//根据tabIndex激活tab
function tabActive4Index(tabIndex,menuId){
    //现将所有的tab设置为未激活
    tabNoActive();
    //根据index激活相应的tab和页面
    $(".tabP ul").find("li:eq(" + tabIndex + ")").addClass("tabActive");
    $(".pageP").find("div:eq(" + tabIndex + ")").show();
    //激活相应的菜单
    activeMenu4Id(menuId);
}

//将所有tab都设置为非active
function tabNoActive(){
    var tabPs = $(".tabP ul").find("li");
    var pagePs = $(".pageP").find("div");

    for(var i=0;i<tabPs.length;i++){
        var ii = tabPs[i];
        $(ii).removeClass("tabActive");
    }

    for(var i=0;i<pagePs.length;i++){
        var ii = pagePs[i];
        $(ii).hide();
    }
}

//切换tab
function selectTab(obj,menuId){
    var index = $(obj).parent().index();
    tabActive4Index(index,menuId);
}

//菜单选中
function menuActive(obj) {
    //获取所有菜单
    var menus = $("#menuList").find("li");
    for(var i=0;i<menus.length;i++){
        var ii = menus[i];
        $(ii).find("a").removeClass("menuActive");
    }
    $(obj).addClass("menuActive");
}
//根据ID选中菜单
function activeMenu4Id(menuId){
    //获取所有菜单
    var menus = $("#menuList").find("li");
    //先将所有菜单设置为未选中
    for(var i=0;i<menus.length;i++){
        var ii = menus[i];
        $(ii).find("a").removeClass("menuActive");
    }
    $("#" + menuId).addClass("menuActive");
}

//根据index选中菜单
function activeMenu4Index(index){
    //获取所有菜单
    var menus = $("#menuList").find("li");
    for(var i=0;i<menus.length;i++){
        var ii = menus[i];
        $(ii).find("a").removeClass("menuActive");
    }
    $("#menuList").find("li:eq("+ index +")").find("a").addClass("menuActive");
}