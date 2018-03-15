package com.witkey.familyTree.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by suyx on 2018/3/5 0005.
 */

public class TxtUtil {
    /**
     * 导出
     * @param file Txt文件(路径+文件名)，Txt文件不存在会自动创建
     * @param dataList  数据
     * @param heads  表头
     * @return
     * @author liuweilong@zhicall.com
     * @create 2016-4-27 上午9:49:49
     */
    public static boolean exportTxt(File file, List<String> dataList,String heads){
        FileOutputStream out=null;
        try {
            out = new FileOutputStream(file);
            return exportTxtByOS(out, dataList, heads);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 导出
     * @param out 输出流
     * @param dataList  数据
     * @param heads  表头
     * @return
     * @author liuweilong@zhicall.com
     * @create 2016-4-27 上午9:49:49
     */
    public static boolean exportTxtByOS(OutputStream out, List<String> dataList,String heads){
        boolean isSucess=false;

        OutputStreamWriter osw=null;
        BufferedWriter bw=null;
        try {
            osw = new OutputStreamWriter(out);
            bw =new BufferedWriter(osw);
            //循环表头
            if(heads!=null&&!heads.equals("")){
                bw.append(heads).append("\r");
            }
            //循环数据
            if(dataList!=null && !dataList.isEmpty()){
                for(String data : dataList){
                    bw.append(data).append("\r\n");
                }
            }
            isSucess=true;
        } catch (Exception e) {
            e.printStackTrace();
            isSucess=false;
        }finally{
            if(bw!=null){
                try {
                    bw.close();
                    bw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(osw!=null){
                try {
                    osw.close();
                    osw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out!=null){
                try {
                    out.close();
                    out=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return isSucess;
    }

    /**
     * 导入
     *
     * @param file Txt文件(路径+文件)
     * @return
     */
    public static List<String> importTxtasliststr(File file){
        List<String> dataList=new ArrayList<String>();
        BufferedReader br=null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                dataList.add(line);
            }
        }catch (Exception e) {
        }finally{
            if(br!=null){
                try {
                    br.close();
                    br=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataList;
    }

    /**
     * 导入
     *
     * @param file Txt文件(路径+文件)
     * @return
     */
    public static List<String[]> importTxtaslistarr(File file){
        List<String[]> dataList=new ArrayList<String[]>();
        BufferedReader br=null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] linearr = line.split("|");
                dataList.add(linearr);
            }
        }catch (Exception e) {
        }finally{
            if(br!=null){
                try {
                    br.close();
                    br=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataList;
    }


    /**
     *  测试
     * @param args
     * @author liuweilong@zhicall.com
     * @create 2016-4-27 上午10:11:46
     */
    public static void main(String[] args) {
        //导出数据测试
//       List<String> dataList=new ArrayList<String>();
//          dataList.add("1,张三,男");
//          dataList.add("2,李四,男");
//          dataList.add("3,小红,女");
//          File file = new File("E:/test");
//          if(!file.exists()){
//              file.mkdir();
//          }
//          boolean isSuccess=TextUtils.exportTxt(new File("E:/test/ljq.txt"), dataList,"编码,姓名,性别");
//          System.out.println(isSuccess);

        //导入数据测试

        List<String[]> dataList= TxtUtil.importTxtaslistarr(new File("E:/test/ljq.txt"));
        for (String[] arr : dataList) {
            System.out.println(arr[0] + "-->" + arr[1] + "-->" + arr[2]);
        }

    }
}
