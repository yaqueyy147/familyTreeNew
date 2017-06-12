/**
 * Created by suyx on 2017/6/12 0012.
 */
function ajaxErrorToLogin() {
    if (window != top){
        top.location.href = projectUrl + "/sign?loginCode=-2";
    }
    location.href =  projectUrl + "/sign?loginCode=-2";
}