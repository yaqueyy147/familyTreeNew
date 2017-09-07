package com.witkey.familyTree.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Created by suyx on 2017/8/30 0030.
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public String [] getParameterValues (String parameter) {
        String [] values = super. getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values. length;
        String [] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = cleanXSS(values[i]);
        }
        return encodedValues;
    }
    public String getParameter (String parameter) {
        String value = super. getParameter(parameter);
        if (value == null) {
            return null;
        }
        return cleanXSS(value);
    }
    public String getHeader (String name) {
        String value = super. getHeader(name);
        if (value == null)
            return null;
        return cleanXSS(value);
    }
    private String cleanXSS (String value) {

        /*String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|\\*|%|chr|mid|master|" +
                "char|declare|sitename|net user|xp_cmdshell|;|or|\\-|\\+|,|like'|create|" +
                "table|from|grant|use|group_concat|column_name|" +
                "information_schema.columns|table_schema|union|order|by|" +
                "truncate|--|like|//|/|#|script|javascript|frame|iframe|<|>|eval";//过滤掉的sql关键字，可以手动添加
                */
        String badStr = "'|insert|delete|update|drop|" +
                "column_name|" +
                "information_schema.columns|table_schema|" +
                "script|javascript";//过滤掉的sql关键字，可以手动添加

        StringBuilder sb = new StringBuilder(badStr);
        sb.append(badStr.toUpperCase());

        String[] bads = sb.toString().split("\\|");
        for(String ss : bads){
            value = value.replaceAll(ss,"");
        }
        return value;
    }

}
