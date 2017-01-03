/**
 * Created by suyx on 2016/12/18.
 */
$(function () {

    $("#regedit").click(function () {
        $("#regeditForm").attr("action",projectUrl + "/sign/regester");
        $("#regeditForm").submit();
    });

});
