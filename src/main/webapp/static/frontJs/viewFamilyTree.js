/**
 * Created by suyx on 2016/12/21 0021.
 */
$(function () {

    //时间空间初始化
    $("#birthTime").datetimepicker({
        format: 'yyyy-mm-dd hh:ii:ss',
        language: 'zh-CN',
        autoclose:true,
    });

    $("#dieTime").datetimepicker({
        format: 'yyyy-mm-dd hh:ii:ss',
        language: 'zh-CN',
        autoclose:true
    });

    $.fn.serializeObject = function()
    {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };

    $("#savePeople").click(function () {
        var data = $("#peopleForm").serializeObject();
        $.ajax({
            type:'post',
            url:'/family/savePeople',
            dataType:'json',
            data:JSON.stringify(data),
            contentType:'application/json',
            success:function (data) {
                alert(data.msg);
            },
            error:function (data) {
                
            }
        });
    });

    var setting = {
        view: {
            addDiyDom: addDiyDom
        }
    };

    var zNodes =[
        {id:1, name:"hover事件显示控件", open:true,
            children:[
                {id:11, name:"按钮1"},
                {id:12, name:"按钮2"},
                {id:13, name:"下拉框"},
                {id:141, name:"文本1"},
                {id:142, name:"文本2"},
                {id:15, name:"超链接"}

            ]},
        {id:2, name:"始终显示控件", open:true,
            children:[
                {id:21, name:"按钮1"},
                {id:22, name:"按钮2"},
                {id:23, name:"下拉框"},
                {id:24, name:"文本"},
                {id:25, name:"超链接"}
            ]}
    ];

    $.fn.zTree.init($("#familyTree"), setting, zNodes);

});
function addDiyDom(treeId, treeNode) {
    var IDMark_A = "_a";
    var aObj = $("#" + treeNode.tId + IDMark_A);

    var editStr = "<a id='diyBtn1_" +treeNode.id+ "' onclick='alert(1);return false;'>链接1</a>" +
        "<a id='diyBtn2_" +treeNode.id+ "' onclick='alert(2);return false;'>链接2</a>";
    aObj.after(editStr);
}