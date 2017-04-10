package com.witkey.familyTree.util;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by suyx on 2016/8/22.
 */
public class PageUtil {

    private static final Logger LOGGER = Logger.getLogger(PageUtil.class);

    /**
     * 根据页码和总页数生成简单的（首页-上一页-下一页-末页-页码）翻页按钮
     * @param pageNo
     * @param totalPage
     * @return
     */
    public static String getSimplePageChanger(int pageNo,int totalPage,int pageSize,String tableId) throws UnsupportedEncodingException{
        int prevP = 1;
        int nextP = totalPage;
        StringBuffer pageChanger = new StringBuffer("");
        pageChanger.append(getPageSizeStr(pageSize,tableId));
        if(pageNo == 1){
            pageChanger.append("<a class=\"btn btn-default btn-sm\" href=\"javascript:void (0);\" id=\"headP\" onclick=\"" + tableId + "PageChange('1')\" disabled>首页</a>");//首页
        }else{
            pageChanger.append("<a class=\"btn btn-default btn-sm\" href=\"javascript:void (0);\" id=\"headP\" onclick=\"" + tableId + "PageChange('1')\">首页</a>");//首页
        }
//        pageChanger.append("<a class=\"btn btn-default btn-sm\" href=\"javascript:void (0);\" id=\"headP\" onclick=\"" + tableId + "PageChange('1')\">首页</a>");
        pageChanger.append(getPrevPageStr(pageNo,tableId));
        pageChanger.append(getNextPageStr(pageNo,totalPage,tableId));
        pageChanger.append(getEndPageStr(pageNo,totalPage,tableId));
        return new String(pageChanger.toString().getBytes(),"UTF-8");
    }

    /**
     * 根据页码和总页数生成复杂的（首页-上一页-数字页码-下一页-末页-页码）翻页按钮
     * @param pageNo
     * @param totalPage
     * @return
     */
    public static String getNumberPageChanger(int pageNo,int totalPage,int pageNum,int pageSize,String tableId) throws UnsupportedEncodingException{

        int index = pageNo;
        StringBuffer pageChanger = new StringBuffer("");
//        pageChanger.append(getPageSizeStr(pageSize,tableId));
        if(pageNo == 1){
            pageChanger.append("<a class=\"btn btn-default btn-sm\" href=\"javascript:void (0);\" id=\"headP\" onclick=\"" + tableId + "PageChange('1')\" disabled>首页</a>");//首页
        }else{
            pageChanger.append("<a class=\"btn btn-default btn-sm\" href=\"javascript:void (0);\" id=\"headP\" onclick=\"" + tableId + "PageChange('1')\">首页</a>");//首页
        }

        pageChanger.append(getPrevPageStr(pageNo,tableId));
        pageChanger.append(getNumberPageStr(pageNo,totalPage,pageNum,tableId));
        pageChanger.append(getNextPageStr(pageNo,totalPage,tableId));
        pageChanger.append(getEndPageStr(pageNo,totalPage,tableId));
        return new String(pageChanger.toString().getBytes(),"UTF-8");
    }

    /**
     * 生成修改每页行数(pageSize)按钮
     * @param pageSize
     * @return
     */
    private static String getPageSizeStr(int pageSize,String tableId){
        StringBuffer pageSizeC = new StringBuffer("");

        pageSizeC.append("<select id=\"pageSizeS\" onchange=\"" + tableId + "ChangePageSize(this)\">");
        if(pageSize == 10){
            pageSizeC.append("<option value=\"10\" selected=\"selected\">10</option>");
        }else{
            pageSizeC.append("<option value=\"10\">10</option>");
        }
        if(pageSize == 20){
            pageSizeC.append("<option value=\"20\" selected=\"selected\">20</option>");
        }else{
            pageSizeC.append("<option value=\"20\">20</option>");
        }
        if(pageSize == 30){
            pageSizeC.append("<option value=\"30\" selected=\"selected\">30</option>");
        }else{
            pageSizeC.append("<option value=\"30\">30</option>");
        }
        if(pageSize == 40){
            pageSizeC.append("<option value=\"40\" selected=\"selected\">40</option>");
        }else{
            pageSizeC.append("<option value=\"40\">40</option>");
        }
        if(pageSize == 50){
            pageSizeC.append("<option value=\"50\" selected=\"selected\">50</option>");
        }else{
            pageSizeC.append("<option value=\"50\">50</option>");
        }
        if(pageSize == 100){
            pageSizeC.append("<option value=\"100\" selected=\"selected\">100</option>");
        }else{
            pageSizeC.append("<option value=\"100\">100</option>");
        }
        pageSizeC.append("</select>");

        return pageSizeC.toString();
    }

    /**
     * 生成数字页码
     * @param pageNo
     * @param totalPage
     * @param pageNum
     * @return
     */
    private static String getNumberPageStr(int pageNo,int totalPage,int pageNum,String tableId){
        int index = 1;//数字页码起始页码
        if(totalPage < pageNum){//如果总页数小于要显示的页码数
            pageNum = totalPage;
        }
        int halfIndex = pageNum / 2;//显示页码数的一半，显示页码以pageNo为中间
        //如果pageNo - halfIndex > 1，将起始页码设置为pageNo - halfIndex
        if(pageNo - halfIndex > 1){
            index = pageNo - halfIndex;
        }
        if(pageNo - halfIndex + pageNum > totalPage){
            index = totalPage - pageNum + 1;
        }

        StringBuffer numPage = new StringBuffer("");
        //从index开始循环pageNum个，生成数字页码
        for(int i=index; i< index + pageNum; i++){
            if(i == index){
                numPage.append("&nbsp;&bull;&nbsp;");
            }
            if(i == pageNo){
                numPage.append("<a class=\"active\" href=\"javascript:void (0);\">");
                numPage.append(i);
                numPage.append("</a>&nbsp;&bull;&nbsp;");
            }else{
                numPage.append("<a href=\"javascript:void (0);\" onclick=\"" + tableId + "PageChange('");
                numPage.append(i);
                numPage.append("')\">");
                numPage.append(i);
                numPage.append("</a>&nbsp;&bull;&nbsp;");
            }



        }

        return numPage.toString();
    }

    /**
     * 生成上一页按钮Str
     * @param pageNo
     * @return
     */
    private static String getPrevPageStr(int pageNo,String tableId){
        int prevP = 1;
        StringBuffer prevPage = new StringBuffer("");
        if(pageNo > 1) {
            prevP = pageNo - 1;
            prevPage.append("<a class=\"btn btn-default btn-sm\" href=\"javascript:void (0);\" id=\"prevP\" onclick=\"" + tableId + "PageChange('");
            prevPage.append(prevP);
            prevPage.append("')\">上一页</a>");//上一页
        }
        return prevPage.toString();
    }

    /**
     * 生成下一页按钮Str
     * @param pageNo
     * @param totalPage
     * @return
     */
    private static String getNextPageStr(int pageNo,int totalPage,String tableId){
        int nextP = totalPage;
        StringBuffer nextPage = new StringBuffer("");
        if(pageNo < totalPage) {
            nextP = pageNo + 1;
            nextPage.append("<a class=\"btn btn-default btn-sm\" href=\"javascript:void (0);\" id=\"nextP\" onclick=\"" + tableId + "PageChange('");
            nextPage.append(nextP);
            nextPage.append("')\">下一页</a>");//下一页
        }
        return nextPage.toString();
    }

    /**
     * 生成末页Str
     * @param pageNo
     * @param totalPage
     * @return
     */
    private static String getEndPageStr(int pageNo,int totalPage,String tableId){
        StringBuffer endPage = new StringBuffer("");

        endPage.append("<a class=\"btn btn-default btn-sm\" href=\"javascript:void (0);\" id=\"endP\" onclick=\"" + tableId + "PageChange('");
        endPage.append(totalPage);
        if(totalPage == pageNo){
            endPage.append("')\" disabled>末页</a>");//末页
        }else{
            endPage.append("')\">末页</a>");//末页
        }

        endPage.append("&nbsp;&nbsp;第&nbsp;" + pageNo + "/" + totalPage + "&nbsp;页");
        return endPage.toString();
    }

    /**
     * 根据list设置table内容
     * @param list
     * @return
     */
    public static String getTableContentStr(List<Map<String,Object>> list){

        String tableContent = "";

        for (Map<String,Object> map : list) {
            tableContent += "<tr>";
            for(Map.Entry<String,Object> entry : map.entrySet()){
                tableContent += "<td>" + entry.getValue() + "</td>";
            }
            tableContent += "</tr>";
        }

        return tableContent;
    }

}
