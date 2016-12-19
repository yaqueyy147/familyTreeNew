/**
 * Copyright 2007-2008. Chongqing First Information & Network Co., Ltd. All rights reserved.
 * <a>http://www.cqfirst.com.cn</a>
 */
package com.witkey.familyTree.hibernate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

/**
 * @author liuhz create 2007-12-8
 */
public final class SqlUtils {
	private SqlUtils() {
	}

	private final static Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);

	/**
	 * 去除sql的select 子句，未考虑union及包含子查询的情况
	 */
	public static String removeSelect(String sql) {
		Assert.hasText(sql);
		int beginPos = sql.toLowerCase().indexOf(" from ");
		Assert.isTrue(beginPos != -1, "sql:" + sql + " must has a keyword 'from'");
		return sql.substring(beginPos);
	}

	/**
	 * 对没有first的SQL生成first语句(仅支持informix数据库)
	 */
	public static String limitSelect(String sql, int limit) {
		Assert.hasText(sql);
		String nsql = sql.toLowerCase();
		if (limit > 0 && nsql.indexOf(" union ") == -1 && nsql.indexOf(" first ") == -1) {
			int beginPos = nsql.indexOf("select");
			Assert.isTrue(beginPos != -1, "sql:" + sql + " must has a keyword 'select'");

			return "select first " + limit + sql.substring(beginPos + 6);
		} else {
			return sql;
		}
	}
	
	/**
	 * 对没有first的SQL生成first语句(仅支持informix数据库)
	 */
	public static String limitSelectCommand(String sql, int limit,String command) {
		Assert.hasText(sql);
		String nsql = sql.toLowerCase();
		if (limit > 0 && nsql.indexOf(" union ") == -1 && nsql.indexOf(" first ") == -1) {
			int beginPos = nsql.indexOf("select");
			Assert.isTrue(beginPos != -1, "sql:" + sql + " must has a keyword 'select'");

			return "select "+command+" first " + limit + sql.substring(beginPos + 6);
		} else {
			return sql;
		}
	}
	

	/**
	 * 去除sql的orderby 子句
	 */
	public static String removeOrders(String sql) {
		Assert.hasText(sql);
		Matcher m = p.matcher(sql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public static void main(String[] args) {
		String a = "AddA";
		String b = a.toLowerCase();
		System.out.println(a);
		System.out.println(b);
		System.out.println(limitSelect("select * from TAB", 2));
	}
}
