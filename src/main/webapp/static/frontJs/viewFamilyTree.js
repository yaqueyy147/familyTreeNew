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

});
