/**
 * Created by suyx on 2016/12/18.
 */
$(function () {

    $("#regedit").click(function () {
        $("#regeditForm").attr("action",projectUrl + "/sign/regester");
        $("#regeditForm").submit();
    });

    $("#companyRegeditbb").click(function () {
        $("#companyForm").attr("action",projectUrl + "/sign/companyRegester");
        $("#companyForm").submit();
    });

});
