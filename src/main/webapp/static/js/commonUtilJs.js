/**
 * Created by suyx on 2017/1/6 0006.
 */
$(function () {
    // $('#imgFile').uploadify({
    //     'swf'           : projectUrl + '/static/uploadify/uploadify.swf',
    //     'uploader'      : projectUrl + '/family/uploadImg',
    //     'cancelImg'     : projectUrl + '/static/uploadify/cancel.png',
    //     'auto'          : true,
    //     'queueID'       : 'progress_bar',
    //     'fileObjName'   : 'uploadFile',
    //     "buttonCursor"  : "hand",
    //     "buttonText"    : "选择图片",
    //     'fileDesc'      : '支持格式:jpg,jpeg,gif,png,bmp', //如果配置了以下的'fileExt'属性，那么这个属性是必须的
    //     'fileExt'       : '*.jpg;*.jpeg;*.gif;*.png;*.bmp',//允许的格式
    //     'onUploadSuccess' : function(file, data, response) {
    //         var result = eval('(' + data + ')');
    //         var imgPath = result.filePath;
    //         $("#result_img").attr('src',imgPath);
    //         $("#result_img").show();
    //         $("#imgFile").hide();
    //         $("#photoUrl").attr('value',imgPath);
    //         $("#show_img").mouseover(function(){
    //             $("#result_img").attr('src',projectUrl + "/static/images/deleteImg.png");
    //         });
    //         $("#show_img").mouseout(function(){
    //             $("#result_img").attr('src',imgPath);
    //         });
    //         $("#result_img").click(function(){
    //             $("#result_img").hide();
    //             $("#imgFile").show();
    //             $("#photoUrl").removeAttr('value');
    //             $("#show_img").unbind('mouseover');
    //             $("#show_img").unbind('mouseout');
    //
    //         });
    //     },
    //     onUploadError:function (file, errorCode, errorMsg, errorString) {
    //         alert("error-->" + errorString);
    //     }
    // });
});

function getData(url,params){
    var result;
    $.ajax({
        type:'post',
        url: projectUrl + url,
        dataType:'json',
        async:false,
        data:params,
        success:function (data) {
            result = data;
        },
        error:function (data) {
            alert(JSON.stringify(data));
        }
    });
    return result;
}

function pagerFilter(data){
    if (typeof data.length == 'number' && typeof data.splice == 'function'){	// is array
        data = {
            total: data.length,
            rows: data
        }
    }
    var dg = $(this);
    var opts = dg.datagrid('options');
    var pager = dg.datagrid('getPager');
    pager.pagination({
        displayMsg:"当前显示从{from}到{to},共{total}条信息",
        onSelectPage:function(pageNum, pageSize){
            opts.pageNumber = pageNum;
            opts.pageSize = pageSize;
            pager.pagination('refresh',{
                pageNumber:pageNum,
                pageSize:pageSize
            });

            dg.datagrid('loadData',data);
        }
    });
    if (!data.originalRows){
        data.originalRows = (data.rows);
    }
    var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.originalRows.slice(start, end));
    return data;
}

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

$.fn.populateForm = function(data){
    return this.each(function(){
        var formElem, name;
        if(data == null){this.reset(); return; }
        for(var i = 0; i < this.length; i++){
            formElem = this.elements[i];
            //checkbox的name可能是name[]数组形式
            name = (formElem.type == "checkbox")? formElem.name.replace(/(.+)\[\]$/, "$1") : formElem.name;
            if(data[name] == undefined) continue;
            switch(formElem.type){
                case "checkbox":
                    if(data[name] == ""){
                        formElem.checked = false;
                    }else{
                        //数组查找元素
                        if(data[name].indexOf(formElem.value) > -1){
                            formElem.checked = true;
                        }else{
                            formElem.checked = false;
                        }
                    }
                    break;
                case "radio":
                    if($.trim(data[name]).length <= 0){
                        formElem.checked = false;
                    }else if(formElem.value == data[name]){
                        formElem.checked = true;
                    }
                    break;
                case "button": break;
                default: formElem.value = data[name];
            }
        }
    });
};

function pPopover(obj,state){
    if(state == 1){
        $(obj).popover("show");
    }else{
        $(obj).popover('hide');
    }

}
