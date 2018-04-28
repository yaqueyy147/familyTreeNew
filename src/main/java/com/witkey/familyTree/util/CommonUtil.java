package com.witkey.familyTree.util;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 普通的工具类
 * @author TianChen
 *
 */
public class CommonUtil {

	public  static  Logger log = Logger.getLogger(new CommonUtil().getClass());
	
	
	/**
	 * 验证字符串是否是正确的时间格式
	 * @param str
	 * @return
	 */
	public static boolean validateTimeFormat(String str) {
		if(isBlank(str)){
			log.info("日期不能为空");
			return false;
		} else {
				if(str.trim().matches("^(19|20)\\d{2}[/\\s\\-\\.]*(0[1-9]|1[0-2]|[1-9])[/\\s\\-\\.]*(0[1-9]|3[01]|[12][0-9]|[1-9])([\\s] *(2[0-3]|[01]?\\d)(:[0-5]\\d){0,2}){0,1}$")) {
					return true;
				} else {
					return false;
				}
		}
	}
	
	/**
	 * 判读字符串是否为空
	 * @author TianChen
	 * @createdTime 2013-8-29 10:32am
	 * @param obj str
	 * @return true:为空; false:不为空
	 */
	public static boolean isBlank(Object obj) {
		try {
			if(obj == null || obj.toString().trim().length() == 0 || "null".equalsIgnoreCase(obj.toString()) || "null".equals(obj.toString().trim().toLowerCase())) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return true;
		}
	} 
	
	
	/**
	 * 取得短的(只包含年月日)时间.
	 * @return
	 */
	public static String getDateShort() {
		DateFormat df  = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar=Calendar.getInstance();   
	    calendar.setTime(new Date());
	    String time = df.format(calendar.getTime());
	    df = null;
	    calendar = null;
		return time;
	}
	
	/**
	 * 取得短的(只包含年月)时间.
	 * @return
	 */
	public static String getCurrentYearMonth() {
		DateFormat df  = new SimpleDateFormat("yyyy-MM");
		Calendar calendar=Calendar.getInstance();   
	    calendar.setTime(new Date());
	    String time = df.format(calendar.getTime());
	    df = null;
	    calendar = null;
		return time;
	}
	
	/**
	 * 取得长的(包含时分秒)时间.
	 * @return
	 */
	public static String getDateLong() {
		DateFormat df  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar   calendar=Calendar.getInstance();   
	    calendar.setTime(new Date()); 
	    String time = df.format(calendar.getTime());
	    df = null;
	    calendar = null;
		return time;
	}
	
	/**
	 * 取得加了一天的长的(包含时分秒)时间.(就是这样yyyy-MM-dd 23:59:59)
	 * @return
	 */
	public static String getDateLongPlus1Hour() {
		DateFormat df  = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		Calendar calendar=Calendar.getInstance();   
	    calendar.setTime(new Date()); 
	    String time = df.format(calendar.getTime());
	    df = null;
	    calendar = null;
		return time;
	}

	/**
	 * 取得长的(时分秒始终置为0)时间.
	 * @return
	 */
	public static String getDateLongZero() {
		DateFormat df  = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Calendar   calendar=Calendar.getInstance();   
	    calendar.setTime(new Date()); 
	    String time = df.format(calendar.getTime());
	    df = null;
	    calendar = null;
		return time;
	}
	
	/**
	 * 把对象里面的String域trim了后再放回去（若对象的String类型的变量的值为null， 则将其设置为""）
	 * @param obj(对象的类型任意)
	 * @returnu	把每个域都tirm()了过后的对象
	 */
	public static Object trimObject(Object obj) {
		Class classType = obj.getClass();	
		Field[] fields = classType.getDeclaredFields();
		int length = fields.length;
		String fName = "";
		Method getMethod_ = null;
		Method setMethod_ = null;
		String midLetter = "";
		for(Field f: fields) {
			//log.info("type: " + f.getType());
			if(f.getType().toString().equals("class java.lang.String")) { //只trim类型为String类型的
				f.setAccessible(true);   //************很重要
				fName = f.getName();
				midLetter = fName.substring(0, 1).toUpperCase();
				try {
					getMethod_ = classType.getMethod("get" + midLetter + fName.substring(1), new Class[] {});
					setMethod_ = classType.getMethod("set" + midLetter + fName.substring(1), new Class[] {f.getType()});
					Object value = getMethod_.invoke(obj, new Object[] {});
					//CommonUtil.log.info(value);
					//防止空指针. 若是null, 就给他赋一个"";
					if(CommonUtil.isBlank(value)){
						value = "";
					} else {
						value = value.toString().trim();
					}
					//CommonUtil.log.info(value);
					setMethod_.invoke(obj, new Object[] {value});
				} catch (Exception e) {
					CommonUtil.log.error("trim时错误!");
					classType = null;
					fields = null;
					getMethod_ = null;
					setMethod_ = null;
					e.printStackTrace();
				}
				
			}
		}
		classType = null;
		fields = null;
		getMethod_ = null;
		setMethod_ = null;
		return obj;
	}

	/**
	 * 根据传入的Entity来生成这个实体的查询语句(列出了这个实体的所有列)
	 * 要求:实体Entity里面的成员变量名要与数据库的完全一样(不分大小写)
	 * @param obj 实体
	 * @reurn String sql 查询语句
	 */
	public static String getSelectSql(Object obj) {
		Class classType = obj.getClass();	
		Field[] fields = classType.getDeclaredFields();
		String fName = "";
		String sql = " select ";
		for(Field f: fields) {
				fName = f.getName();
				sql += fName + ",";
		}
		sql = sql.substring(0, sql.length() - 1) + " from " + classType.getSimpleName();
		classType = null;
		fields = null;
		return sql;
	}
	
	/**
	 * 比较两String时间的先后
	 * @param beginTime	Object类型的开始时间
	 * @param endTime	Object类型的结束时间
	 * @return 只有在 endTime > beginTime 才返回true
	 * @throws Exception 
	 */
	public static boolean CommpareTime(Object beginTime, Object endTime) throws Exception {
		return ObjToDate(beginTime).before(ObjToDate(endTime));
	}

	/**
	 * 得到一个随机的密码字符串
	 * @param length 返回字符串的长度
	 * @return 随即密码字符串
	 */
	public static final String getRandomString(int length) {
		Random randGen = null;
		char[] numbersAndLetters = null;
		
		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			randGen = new Random();
			numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
					+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		randGen = null;
		numbersAndLetters = null;
		return new String(randBuffer);
	}
	   
	/**
	 * 去掉字符串中的%，并去掉收尾的空格
	 * @param str
	 * @return 操作后的字符串
	 */
	public static String delPercentage(String str) {
		if(isBlank(str)){
			return "";
		}
		str = str.replace("%", "").trim();
		return str;
	}

	
	/**
	 * 从map得到里面的value
	 * @param map
	 * @param str key键
	 * @return
	 */
	public static String getStrFrmMap(Map map, String str) {
		if(isBlank(map.get(str))) {
			return "";
		} else {
			return map.get(str).toString();
		}
	}
	
	/**
	 * 从map得到里面的value(忽略key的大小写)
	 * @param map
	 * @param str key键
	 * @return value
	 */
	public static String getStrFrmMapIgnoreCaption(Map map, String str) {
		if(isBlank(map.get(str))){				//若本来取不到才考虑忽略大小写
			str = str.toLowerCase();			//先全部转小写好比较
			Iterator it = map.keySet().iterator();	//遍历map的key
			String temp = "";				//用来存临时的next key
			while(it.hasNext()) {
				temp = it.next() + "";		//临时的next key
				if(str.equals(temp.toLowerCase())) {	//将key转小写后于转小写后的str比较
					it = null;
					return map.get(temp) + "";			//若想等, 则将map里的这个key的value返回
				}
			}
			it = null;
			return "";		//若还是没有找到匹配的,则放回空"".
		} else {			//若本身就能取到, 就直接取起走了.
			return map.get(str).toString();
		}
	}
	/**
	 * 保留小数点后2位(四舍五入).
	 * @param num
	 * @return String
	 */
	public static String get2DotStrFrmDouble(Double num) {
		String str = String.valueOf(num);
		str = new BigDecimal(str).toPlainString();
		int dotPlace = str.indexOf(".");
		if(dotPlace == -1) {
			return str;
		}
		//if(true){return "";}
		//小数点后不足3位时直接返回该数字
		if(str.length() - dotPlace <= 3) {
			return str;
		}
		
		char toKeepNum = str.charAt(dotPlace + 2);	//小数点后第二位
		char toDelNum = str.charAt(dotPlace + 3);	//小数点后第三位
		if(char2Int(toDelNum) >= 5) {
			return str.substring(0, dotPlace + 2) + (char2Int(toKeepNum) + 1);
		} else {
			return str.substring(0, dotPlace + 3);
		}
	}
	
	/**
	 * 保留小数点后2位(四舍五入).
	 * @param str num
	 * @return String
	 */
	public static String get2DotStrFrmStr(String str) {
		if(isBlank(str) || "null".equalsIgnoreCase(str)) {
			return "";
		}
		str = new BigDecimal(str).toPlainString();
		int dotPlace = str.indexOf(".");
		if(dotPlace == -1) {
			return str;
		}
		//if(true){return "";}
		//小数点后不足3位时直接返回该数字
		if(str.length() - dotPlace <= 3) {
			return str;
		}
		
		char toKeepNum = str.charAt(dotPlace + 2);	//小数点后第二位
		char toDelNum = str.charAt(dotPlace + 3);	//小数点后第三位
		if(char2Int(toDelNum) >= 5) {
			return str.substring(0, dotPlace + 2) + (char2Int(toKeepNum) + 1);
		} else {
			return str.substring(0, dotPlace + 3);
		}
	}
	
	/**
	 * 把char转化成int
	 * @param charNum
	 * @return
	 */
	public static Integer char2Int(char charNum) {
		return Integer.parseInt(String.valueOf(charNum));
	}
	
	/**
	 * 得到当前的jsp页面名字(不包含参数)
	 * @param request
	 * @return
	 */
	public static String getJspFileName(HttpServletRequest request) {
		String sp = request.getServletPath();
		return sp.substring(sp.lastIndexOf("/") + 1, sp.indexOf(".jsp") + 4);
	}
	
	/**
	 * 得到当前的jsp页面名字
	 * @param request
	 * @return
	 */
	public static String getJspFileName(HttpServletRequest request, boolean hasParam) {
		String sp = request.getServletPath();
		if(hasParam){
			return sp.substring(sp.lastIndexOf("/") + 1);
		} else {
			return sp.substring(sp.lastIndexOf("/") + 1, sp.indexOf(".jsp") + 4);
		}
		
	}
	
	
	/*
	 * 带有小数点的字符串转换为整型int
	 * @author ShiMingliang
	 */
	public  static Integer getInt(String xiangliang){
		Integer count=0;
		String test;
		try {
			if(isBlank(xiangliang))
			{
				count = 0;
			}
			if (xiangliang.indexOf(".") > 0) {
				test = xiangliang.substring(0, xiangliang.indexOf('.'));
				count = Integer.valueOf(test);
			} else {
				count = Integer.valueOf(xiangliang);
			}
			
		} catch (Exception e) {
			System.out.println("转型错误! 输入的字符串为: " + xiangliang);
		}
		return count;
	}
	
	/**
	 * 判断字符串是否可以转换成Integer
	 * @return
	 */
	public static boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	} 
	/**
	 * 得到页面的参数
	 * @param request
	 * @param paramName
	 * @return 若页面参数为空, 返回"", 否则返回trim()后的String
	 */
	public static String getParam(HttpServletRequest request, String paramName) throws Exception {
		String parameter = request.getParameter(paramName);
		parameter=parameter==null?"":URLDecoder.decode(parameter,"UTF-8").trim();
		return parameter;
	}
	
	/**
	 * 得到页面的参数
	 * @param params
	 * @param paramName
	 * @return 若页面参数为空, 返回"", 否则返回trim()后的String
	 */
	public static String getParam(Map<String,Object> params, String paramName) throws Exception {
		String parameter = params.get(paramName).toString();
		parameter=parameter==null?"":URLDecoder.decode(parameter,"UTF-8").trim();
		return parameter;
	}
	
	/**
	 * 从request中得到属性
	 * @param scope
	 * @param attrName
	 * @return 若页面参数为空, 返回"", 否则返回trim()后的String
	 */
	public static <T> String getAttr(T scope, String attrName) {
		if(scope instanceof HttpServletRequest) {
			return ((HttpServletRequest)scope).getAttribute(attrName)==null?"":((HttpServletRequest)scope).getAttribute(attrName).toString().trim();
		} else if(scope instanceof HttpSession) {
			return ((HttpSession)scope).getAttribute(attrName)==null?"":((HttpSession)scope).getAttribute(attrName).toString().trim();
		} else {
			return "";
		}
	}
	
	/**
	 * 按照传入的格式取得时间(格式含义: "yyyy-MM-dd HH:mm:ss" -- 对应 "年-月-日 时:分:秒")
	 * @return 字符串格式的时间
	 */
	public static String getDateFrmFormat(String format) {
		DateFormat df  = new SimpleDateFormat(format);
		Calendar   calendar=Calendar.getInstance();   
	    calendar.setTime(new Date()); 
	    String time = df.format(calendar.getTime());
	    df = null;
	    calendar = null;
		return time;
	}
	
	/**
	 * 按照传入的格式取得时间, 并且根据要求修正日期(格式含义: "yyyy-MM-dd HH:mm:ss" -- 对应 "年-月-日 时:分:秒")
	 * @param format String类型的日期样式
	 * @param difference Integer[]类型, difference[0-5] 分别代表 年月日时分秒的修正. 正数为+,负数为减
	 * @return 修正后的字符串格式的时间
	 */
	public static String getDateFrmFormat(String format, Integer[] difference) {
		GregorianCalendar now = new GregorianCalendar();	//日期调整类
		SimpleDateFormat sdft = new SimpleDateFormat(format,Locale.US);	//格式化类
		int len = difference.length;	//修正数组的长度
		if(len > 0) {	//若修正数组有第一个成员, 则是用于修正年份的
			now.add(GregorianCalendar.YEAR,difference[0]);
			if(len > 1) {	//若修正数组有第二个成员, 则是用于修正月份的
				now.add(GregorianCalendar.MONTH,difference[1]);
				if(len > 2) {
					now.add(GregorianCalendar.DATE,difference[2]);
					if(len > 3) {
						now.add(GregorianCalendar.HOUR,difference[3]);
						if(len > 4) {
							now.add(GregorianCalendar.MINUTE,difference[4]);
							if(len > 5) {
								now.add(GregorianCalendar.SECOND,difference[5]);
							}
						}
					}
				}
			}
		}
		return sdft.format(now.getTime());
	}
	
	/**
	 * 把一个数组转成以特定字符分割的字符串
	 * @param arr	数组
	 * @param splitStr 分隔符
	 * @return 合成后的字符串
	 */
	public static String getStrFrmArr(String[] arr, String splitStr) {
		int length = arr.length;
		if(length == 0) {
			return "";
		}
		String str = "";
		for(int i = 0; i < length; i++) {
			str += arr[i] + splitStr;
		}
		return str.substring(0, str.length() -1);
	}
	
	/**
	 * 把String类型数组转换为Double类型数组
	 * @param arr 数组
	 * @return 转换后的数组
	 */
	public static Double[] getDouarrFrmStrArr(String[] arr)
	{
		int len = arr.length;
		Double[] dd = new Double[len];
		for (int i = 0; i < len; i++)
		{
			if(isNum(arr[i]))
			{
				dd[i]=Double.parseDouble(arr[i]);
			}
			else 
			{
				dd[i]=0.0;
			}
		}
		return dd;
	}
	
	/**
	 * 判断一个字符串是否都为有效数字Double类型或int类型
	 * @param str 字符串
	 * @return 返回true或者false
	 * 
	 */
	public static Boolean isNum(String str)
	{
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	/**
	 * 从路径Object(String或File)中取得文件名(包括扩展名)
	 * @param path 路径
	 * @return 文件名
	 * @throws Exception 
	 */
	public static String getFileNameFrmPath(Object path) throws Exception {
		/**
		int left = path.lastIndexOf("/");
		int right = path.lastIndexOf("\\");
		left = left >= right?left:right;
		return path.substring(left + 1, path.length());
		*/
		return convert2File(path).getName();
	}
	
	/**
	 * 把Object(String 或  File) 转换才File
	 * @param path Object(String 或  File)
	 * @return File
	 * @throws Exception 
	 */
	public static File convert2File(Object path) throws Exception {
		if("java.lang.String".equals(path.getClass().getName())){//如果是String
			return new File(path + "");
		} else if("java.io.File".equals(path.getClass().getName())) {//如果是File
			return (File)path;
		} else {
			throw new Exception(" Path type error!");
		}
	}
	
	/**
	 * 从包含文件名的路径String中取得没有文件名的path
	 * @param path 没有文件名的路径
	 * @return 文件名
	 * @throws Exception 
	 */
	public static String getNoFilePathFrmPath(Object path) throws Exception {
		//return path.replaceAll(getFileNameFrmPath(path), "");
		File file = convert2File(path);
		if(file.isFile()) {
			return file.getParent();
		} else if(file.isDirectory()) {
			return file.getPath();
		} else {	//还有可能Path是像"/application/web/memberCenter/"这样的相对路劲, 这个我们也得兼容
			String pathStr = file.getPath();
			//throw new Exception(" path type error!");
			int left = pathStr.lastIndexOf("/");
			int right = pathStr.lastIndexOf("\\");
			left = left >= right?left:right;
			return pathStr.substring(left + 1, pathStr.length());
		}
	}
	
	/**
	 * 把路径转化为绝对路径
	 * @param request HttpServletRequest
	 * @param path String路径
	 * @return 文件对于操作系统的绝对路径
	 */
	public static String getRealPath4File(HttpServletRequest request, String path) {
		//如果不是给的绝对路径, 则转化成绝对路径
		if(path.indexOf(":") < 0){
			path = request.getRealPath(path);
		}
		return path;
	}
	
	/**
	 * 取得当前request的相对路径(如:"/application/web/memberCenter/")
	 * @return 相对路径String
	 * @throws Exception 
	 */
	public static String getCurrentPath(HttpServletRequest request) throws Exception {
		return CommonUtil.getNoFilePathFrmPath(request.getServletPath());
	}
	
	/**
	 * 给Object(String 或 File) 创建路径
	 * @param fileOrPath
	 * @throws Exception
	 */
	public static void mkDirs(Object fileOrPath) throws Exception {
		//判断路径存不存在,若不存在则新建路径
		File file = new File(CommonUtil.getNoFilePathFrmPath(fileOrPath));
		if(!file.exists()) {
			file.mkdirs();
		}
	}
	
	/**
	 * 打印错误栈
	 * @param e 异常
	 */
	public static void syso(Exception e) {
		StackTraceElement [] messages=e.getStackTrace();
		System.out.print(e.getClass());
		System.out.println("Case: " + e.getCause());
	   	System.out.println("Message: " + e.getMessage());
	    for(int i=0;i<messages.length;i++){
//			    System.out.println("ClassName:"+messages[i].getClassName());
//			    System.out.println("getFileName:"+messages[i].getFileName());
//			    System.out.println("getLineNumber:"+messages[i].getLineNumber());
//			    System.out.println("getMethodName:"+messages[i].getMethodName());
		    System.out.println(messages[i].toString());
	    }
	}
	
	/**
	 * 根据已知Object(已经设好值了的),来生成并返回一个往数据库插入这个Object的sql语句
	 * @param obj 已经设置好值的实体(要求:全部字段必须为String类型.)
	 * @return sql 生成的sql插入语句.
	 * @throws Exception 
	 */
	public static String getInsertSqlFromObj(Object obj) throws Exception {
		trimObject(obj);	//首先去掉语句收尾的空格
		Class classType = obj.getClass();	
		Field[] fields = classType.getDeclaredFields();
		int length = fields.length;
		String fName = "";
		String sql = "insert into " + classType.getSimpleName() + "(";
		String valuesSql = "values(";
		Method getMethod_ = null;
		Object value = null;
		String midLetter = null;
		String fieldType = "";
		//System.out.println(sql);
		//if(true) {return "";}
		for(Field f: fields) {
			f.setAccessible(true);   //************很重要
			fName = f.getName();
			midLetter = fName.substring(0, 1).toUpperCase();
			getMethod_ = null;
			fieldType = f.getType().toString();
			//System.out.println(fieldType);
			try {
				if(fieldType.equals("boolean")) {	//若是boolean 则没有get方法只有isBoolean方法
					getMethod_ = classType.getMethod("is" + midLetter + fName.substring(1), new Class[] {});
				} else {
					getMethod_ = classType.getMethod("get" + midLetter + fName.substring(1), new Class[] {});
				}
				
				value = getMethod_.invoke(obj, new Object[] {});
				if(CommonUtil.isBlank(value)){continue;} //如果为空值,则不插入
				sql += fName + ",";		//在要插入的域中加一个
				if(fieldType.equals("class java.lang.String")) {	//java.lang.String类型
					valuesSql += "'" + value + "',";
				} else if(fieldType.equals("class java.util.Date")){ //java.util.sql类型
					valuesSql += "'" + DateToStr((Date)value) + "',";
				} else if(fieldType.equals("int") || fieldType.equals("class java.lang.Integer")) {		//int 或Integer 类型
					valuesSql += value + ",";
				} else if(fieldType.equals("class java.lang.Double") || fieldType.equals("float")){ //java.lang.Double或float类型
					valuesSql += value + ",";
				} else if(fieldType.equals("boolean")){ //boolean类型
					valuesSql += value + ",";
				}
				
			} catch (Exception e) {
				CommonUtil.log.error("拼接sql时错误!");
				classType = null;
				fields = null;
				value = null;
				getMethod_ = null;
				throw e;
			} finally {
				
			}
		}
		sql = sql.substring(0, sql.length() - 1) + ") " + valuesSql.substring(0, valuesSql.length() - 1) + ") ";
		classType = null;
		fields = null;
		value = null;
		getMethod_ = null;
		return sql;
	}
	
	/**
	 * 根据已知Object(已经设好值了的),来生成并返回一个往数据库插入这个Object的PrepareSql语句(也就是有?的那种语句)
	 * @param obj 已经设置好值的实体(要求:全部字段必须为String类型.)
	 * @return sql 生成的PrepareSql插入语句.
	 * @throws Exception 
	 */
	public static Map getInsertPrepareSqlAndParamFromObj(Object obj) throws Exception {
		ArrayList<Object> paraList = new ArrayList<Object>();
		Map resMap = new HashMap<String, Object>();
		Class classType = obj.getClass();	
		Field[] fields = classType.getDeclaredFields();
		int length = fields.length;
		String fName = "";
		String sql = "insert into " + classType.getSimpleName() + "(";
		String valuesSql = "values(";
		Method getMethod_ = null;
		Object value = null;
		String midLetter = null;
		String fieldType = "";
		//System.out.println(sql);
		//if(true) {return "";}
		for(Field f: fields) {
			if(f.getType().toString().equals("class java.lang.String")) {
				f.setAccessible(true);   //************很重要
				fName = f.getName();
				sql += fName + ",";
				midLetter = fName.substring(0, 1).toUpperCase();
				getMethod_ = null;
				fieldType = f.getType().toString();
				try {
					if(fieldType.equals("boolean")) {	//若是boolean 则没有get方法只有isBoolean方法
						getMethod_ = classType.getMethod("is" + midLetter + fName.substring(1), new Class[] {});
					} else {
						getMethod_ = classType.getMethod("get" + midLetter + fName.substring(1), new Class[] {});
					}
					getMethod_ = classType.getMethod("get" + midLetter + fName.substring(1), new Class[] {});
					value = getMethod_.invoke(obj, new Object[] {});	//执行get方法
					if(CommonUtil.isBlank(value)){continue;} //如果为空值,则不插入
					valuesSql += "?,";
					paraList.add(value);
				} catch (Exception e) {
					CommonUtil.log.error("拼接sql时错误!");
					classType = null;
					fields = null;
					value = null;
					getMethod_ = null;
					throw e;
				} finally {
					
				}
			}
		}
		sql = sql.substring(0, sql.length() - 1) + ") " + valuesSql.substring(0, valuesSql.length() - 1) + ") ";
		classType = null;
		fields = null;
		value = null;
		getMethod_ = null;
		resMap.put("sql", sql);
		resMap.put("list", paraList);
		return resMap;
	}
	
	/**
	 * 
	 * @param conn	dao.getHibSessionFactory().openSession().connection()
	 * @param obj	要插入的实体
	 * @throws SQLException
	 */
	public static void insertObjPrepareStat(Connection conn, Object obj) throws Exception {
		Map map = null;
		List list = null;
		PreparedStatement preState = null;
		try {
			map = getInsertPrepareSqlAndParamFromObj(obj);
			String sql = map.get("sql").toString();
			list = (List) map.get("list");
			preState = conn.prepareStatement(sql);
			if (!CommonUtil.isBlank(list)) {
				for (int i = 1; i <= list.size(); i++) {
					preState.setObject(i, list.get(i - 1));
				}
			}
			preState.execute(); 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			preState.close();
			map.clear();
			map = null;
			list = null;
		}
		
	}
	
	/**
	 * 把map里面的与obj对应的属性set进去, 从而把map转为实体Entity
	 * 要求:map里面Key(必须全部为小写)要与obj里面的成员变量名相对应(忽略大小写)
	 * @param obj 实体
	 * @reurn String sql 查询语句
	 */
	public static Object getEntityFromMap(Map map, Object obj) throws Exception{
		Class<? extends Object> classType = obj.getClass();
		Field[] fields = classType.getDeclaredFields();
		String fName = "";
		String midLetter = null;
		Method setMethod_ = null;
		for(Field f: fields) {
			f.setAccessible(true);   //************很重要
			fName = f.getName();
			midLetter = fName.substring(0, 1).toUpperCase();
			try {
				setMethod_ = classType.getMethod("set" + midLetter + fName.substring(1), new Class[] {String.class});
				setMethod_.invoke(obj, new Object[] {map.get(fName.toLowerCase())});
			} catch (Exception e) {
				fields = null;
				classType = null;
				setMethod_ = null;
				throw e;
			} 
		}
		fields = null;
		classType = null;
		setMethod_ = null;

		return obj;
	}
	
	/**
	* 字符串转换成日期
	* @param str "yyyy-MM-dd HH:mm:ss" 格式的日期字符串
	* @return date 
	 * @throws Exception 
	*/
	public static Date StrToDate(String str) throws Exception {
	   SimpleDateFormat format = null;
	   if(isBlank(str)) {
		   throw new Exception("String日期不能为空");
	   }
	   str = str.trim();
	   if(str.length() > 19) {		//长度过长时要截取其中的yyyy-MM-dd HH:mm:ss
		   str = str.substring(0, 19);
	   }
	   if(str.trim().length() > 10 && str.trim().length() <= 19) {
		   format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   } else if(str.trim().length() <= 10){
		   format = new SimpleDateFormat("yyyy-MM-dd");
	   } else {
		   throw new Exception("String日期格式错误");
	   }
	   Date date = format.parse(str.trim());
	   format = null;
	   return date;
	}

	/**
	 * 字符串转换成日期
	 * @param str "yyyy-MM-dd HH:mm:ss" 格式的日期字符串
	 * @return date
	 * @throws Exception
	 */
	public static Date StrToDateFromFtr(String str,String ftr) throws Exception {
		SimpleDateFormat format = null;
		if(isBlank(str)) {
			throw new Exception("String日期不能为空");
		}
		str = str.trim();
		if(str.length() > 19) {		//长度过长时要截取其中的yyyy-MM-dd HH:mm:ss
			str = str.substring(0, 19);
		}
		if(str.trim().length() <= 19) {
			format = new SimpleDateFormat(ftr);
		} else {
			throw new Exception("String日期格式错误");
		}
		Date date = format.parse(str.trim());
		format = null;
		return date;
	}
	
	/**
	* Object)(字符串或Date)转换成日期
	* @param obj "yyyy-MM-dd HH:mm:ss" 格式的日期字符串
	* @return date 
	* @throws Exception 
	*/
	public static Date ObjToDate(Object obj) throws Exception {
		String className = obj.getClass().getName();
		Date date = null;
		if("java.lang.String".equals(className)) {
			return StrToDate(obj + "");
		} else if("java.util.Date".equals(className)){
			return (Date)obj;
		} else {
			throw new Exception("请输入正确的起始日期");
		}
	}
	
	/**
	* 日期转换成字符串
	* @param date 
	* @return str yyyy-MM-dd HH:mm:ss 格式的字符串
	*/
	public static String DateToStr(Date date) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String str = format.format(date);
	   format = null;
	   return str;
	} 
	
	/**
	* 日期转换成字符串
	* @param date 
	* @param ftr 想要的格式
	* @return str 格式的字符串
	*/
	public static String DateToStr(Date date,String ftr) {
		if(isBlank(date))
		{
			return "";
		}
		   SimpleDateFormat format = new SimpleDateFormat(ftr);
		   String str = format.format(date);
		   format = null;
		   return str;
		} 
	
	/**
	 * 数据库查询出的日期转换成指定格式的字符串
	 * @param str
	 * @return str ftr(日期格式)格式的字符串
	 */
	public static String StrToStrInDate(String str,String ftr) throws Exception
	{
		if(isBlank(str))
		{
			return "";
		}
		Date date = StrToDate(str);
		SimpleDateFormat format = new SimpleDateFormat(ftr);
		str = format.format(date);
		format = null;
		return str;
	}
	
	/**
	 * 数据库查询出的日期转换成制定格式的字符串
	 * @param str
	 * @return str yyyy-MM-dd格式的字符串
	 */
	public static String StrToStrInDate(String str) throws Exception
	{
		if(isBlank(str))
		{
			return "";
		}
		Date date = StrToDate(str);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		str = format.format(date);
		format = null;
		return str;
	}
	
	/**
	 * 把日期(String或Date) 转化成指定格式的字符串
	 * @param obj String或Date
	 * @param format 像yyyy-MM-dd HH:mm:ss这样的格式字符串
	 * @return String  指定格式的时间字符串
	 */
	public static String ObjToDateStr(Object obj, String format) throws Exception{
		Date date = ObjToDate(obj);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 验证邮箱地址
	 * @return true 正确; false 错误
	 */
	public static boolean chkMailAddress(String str) {
		if(isBlank(str)) {
			return false;
		}
		//没转义的原始正则^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$
		if(str.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 验证手机号码
	 * @return true 正确; false 错误
	 */
	public static boolean chkPhoneNum(String str) {
		if(isBlank(str)) {
			return false;
		}
		//没转义的原始正则(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\(\d{3}\))|(\d{3}\-))?(1[358]\d{9})$)
		if(str.matches("(^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$)|(^((\\(\\d{3}\\))|(\\d{3}\\-))?(1[358]\\d{9})$)")) {
			return true;
		} else {
			return false;
		}
	}
	
	/*** 
     * MD5加码 生成32位md5码 
     * @param inStr 待加密字符串
     * @return 32位加密后的字符串
     */  
    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
    }
    
    /**
     * 若参数为空, 则返回 0.0. 
     * @param str 字符串,数字或空
     * @return Double
     */
    public static Double parseDouble(Object str) {
    	if(isBlank(str)) {
    		return 0.0;
    	} else {
    		return Double.parseDouble(str.toString());
    	}
    	
    }
	
    /**
	 * 把数值型的Double,int,或String转成非科学计数法的,保留了 @scale 位的字符串(scale后的四舍五入)
	 * @Param num 数值型的对象
	 * @param scale 小数点后保留的位数 .若为 0, 则返回int的字符串. 为负数时则对正数部分的也四舍五入.
	 * @return String 非科学计数法的,保留了 @scale 位的字符串
	 */
	public static String number2String(Object num, Integer scale) {
		if("".equals(num + "") || num == null )
		{
			return "0.00";
		}

		return new BigDecimal(Double.parseDouble(num + "")).setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString();
	}
	
	/**
	 * 返回length个c字符串的重复叠加组合
	 * @param c			要重复的字符串
	 * @param length	重复的次数
	 * @return	重复后的字符串
	 */
	public static String getDuplicateChar(String c, int length) {
		int i = 0;
		String resString = "";
		for(i = 0; i < length; i++) {
			resString += c;
		}
		return resString;
	}
	
	/**
	 * 把一个字符串的某个范围覆盖成想要的字符串. 如:replaceRegion4String("0123456789", "*", 1,3) 返回 "0*3456789"
	 * @param originalString 原字符串
	 * @param coverString 用来覆盖的字符串
	 * @param beginPlace 开始覆盖的位置
	 * @param endPlace   结束覆盖的位置
	 * @return
	 * @throws Exception 
	 */
	public static String replaceRegion4String(String originalString, String coverString, int beginPlace, int endPlace) throws Exception {
		if(beginPlace > endPlace) {
			throw new Exception("结束位置不能比开始位置小   beginPlace: " + beginPlace + " > endPlace : " + endPlace);
		} 
//		System.out.println("ddddddd: originalString.substring(0, beginPlace):" + originalString.substring(0, beginPlace));
//		System.out.println("ddddddd: coverString:" + coverString);
//		System.out.println("ddddddd: originalString.substring(endPlace, originalString.length():" + originalString.substring(endPlace, originalString.length()));
//		System.out.println("beginPlace : " + beginPlace + "    endPlace" + endPlace);
		return originalString.substring(0, beginPlace) + coverString + originalString.substring(endPlace, originalString.length());
	}
    
	/**
	 * 在源String中count指定字符String的数量.
	 * @param originalStr	源String
	 * @param subStr		要匹配的字符串
	 * @return 匹配字符串数量
	 * @throws Exception
	 */
	public static int countSubstringInString(String originalStr, String subStr) throws Exception {
		int count = 0;
		String bonus = "";
		if(isBlank(subStr)) {
			throw new Exception("要匹配的字符串不能为空!!");
		}
		if(subStr.indexOf("(") > -1 || subStr.indexOf(")") > -1 || subStr.indexOf("\\") > -1 || subStr.indexOf(".") > -1) {	//如果包含了需要转移的字符
			bonus = "\\";
		}
//		System.out.println("**originalStr : " + originalStr);
//		System.out.println("**subStr : " + subStr);
//		System.out.println(originalStr.indexOf(subStr));
		while(originalStr.indexOf(subStr) > -1) {
			//System.out.println("subStr : " + subStr);
			count++;
			originalStr = originalStr.replaceFirst(bonus + subStr, "");
			if(count > 1000) {
				throw new Exception("Wrong!");
			}
		}
		return count;
	}
	
	/**
	 * 把map转成dto实体里去
	 * @param map			//待转的map
	 * @param classType		//dto实体的class
	 * @return	转换好的dto
	 * @throws Exception
	 */
	public static Object map2Dto(Map<String, Object> map, Class classType) throws Exception {
		Object obj = null;		//要返回的实例 	
		try {
			obj = classType.newInstance();
		} catch (Exception e1) {
			System.out.println("实例化失败");
			throw new Exception("实例化失败");
		}
		Object value = null; 	//临时存map的value
		String fName = "";		//成员变量名
		String midLetter = "";	//驼峰规则中间的大写字母
		Method setMethod_ = null;	//set方法
		Field[] fields = classType.getDeclaredFields();	//得到指定类的所有成员变量,包括private的.
		int fieldLength = fields.length;	//成员变量的数量
		int i = 0;		//用于for循环
		try {
			for(i = 0; i < fieldLength; i++) {
				fName = fields[i].getName();		//取得成员变量名
				value = getStrFrmMapIgnoreCaption(map, fName);	//在map中取得对应的value
				if(isBlank(value)) {	//若map里没有这个key, 则跳过
					continue;
				}
				fields[i].setAccessible(true);		//必须要的
				midLetter = fName.substring(0, 1).toUpperCase();	//得到方法中间的大写字母
				setMethod_ = classType.getMethod("set" + midLetter + fName.substring(1), new Class[] {fields[i].getType()});	//得到setMethod
				//System.out.println("aaaaa: fields[i].getClass()" + fields[i].getClass().getCanonicalName() +  "      " + fields[i].getName() + "   bbbbbb: " +  fields[i].getModifiers() + "  ccccc: " + fields[i].getType() + "  ddddd: " +  fields[i].getGenericType());
				value = convertObject2AppointedType(value, fields[i]);
				setMethod_.invoke(obj, new Object[] {value});		//执行set方法, 给成员变量赋值
			}
		} catch (Exception e) {
			System.out.println(fields[i].getName());
			syso(e);
			throw e;
		} finally {
			value = null;
			setMethod_ = null;
			fields = null;
		}
		return obj;
	}
	
	public static Object convertObject2AppointedType(Object obj, Field field) throws Exception {
		String typeName = field.getType().toString();
		//System.out.println(typeName);
		/** */
		if(typeName.equals("class java.lang.String")) {	//java.lang.String类型
			return obj + "";
		} else if(typeName.equals("class java.util.Date")){ //java.util.sql类型
			return StrToDate(obj + "");
		} else if(typeName.equals("int") || typeName.equals("class java.lang.Integer")) {		//int 或Integer 类型
			return Integer.parseInt(obj + "");
		} else if(typeName.equals("class java.lang.Double") || typeName.equals("double")){ //java.lang.Double或float类型
			//System.out.println("**************typeName: " + typeName + "   " + Double.parseDouble(obj + "") + "   ddd  " + Double.parseDouble(obj + "") + "");
			return Double.parseDouble(obj + "");
		} else if(typeName.equals("float") || typeName.equals("class java.lang.Float")){ //java.lang.Double或float类型
			//System.out.println("**************typeName: " + typeName + "   " + Double.parseDouble(obj + "") + "   ddd  " + Double.parseDouble(obj + "") + "");
			return Float.parseFloat(obj + "");
		} else if(typeName.equals("boolean") || typeName.equals("class java.lang.Boolean")){ //boolean类型
			return Boolean.parseBoolean(obj + "");
		} else if(typeName.equals("class java.sql.Timestamp")){ //boolean类型
			return Timestamp.valueOf(obj + "");
		} else {
			System.out.println("未能识别的类型: " + typeName);
			return obj;
		} 
	}
	
	public static boolean doesMapContainsKey(Map<String, Object> map, String key, boolean ignoreCaption) {
		
		return false;
	}

	/**
	 * 通过出生日期,计算年龄,精确到秒      convertBirth2Age(birth)[0]就是年龄(Integer的)
	 * @param obj java.util.Date或java.lang.String型的时间.
	 * @return Integer[] {year, month, day, hour, minute, second}
	 * @throws Exception
	 */
	public static Integer[] convertBirth2Age(Object obj) throws Exception {
		Date date = ObjToDate(obj);
		Date today = new Date();
		if (today.before(date)) {	//如果出生日期比现在还晚
			throw new Exception("出生日期不对.");
		}
		int year = today.getYear() - date.getYear();
		int month = today.getMonth() - date.getMonth();
		int day = today.getDate() - date.getDate();
		int hour = today.getHours() - date.getHours();
		int minute = today.getMinutes() - date.getMinutes();
		int second = today.getSeconds() - date.getSeconds();
		if (month < 0) {
			year -= 1;
			month += 12;
		}
		if (day < 0) {
			month -= 1;
			day += 30;
		}
		if (hour < 0) {
			day -= 1;
			hour += 24;
		}
		if (minute < 0) {
			hour -= 1;
			minute += 60;
		}
		if (second < 0) {
			minute -= 1;
			second += 60;
		}
//		StringBuffer sb = new StringBuffer();
//		sb.append(year + "岁").append(month + "月").append(day + "天").append(hour + "小时").append(minute + "分钟").append(second + "秒");
//		System.out.println(sb);
		return new Integer[] {year, month, day, hour, minute, second};
	}
	
	/**
	 * 比较两个日期的先后. dateObj1先于dateObj2 -> "0" dateObj1后于dateObj2 -> "1" 否则 -> "2"
	 * @param dateObj1  第一个日期(String 或 Date)
	 * @param dateObj2  第二个日期(String 或 Date)
	 * @return dateObj1先于dateObj2 -> "0" dateObj1后于dateObj2 -> "1" 否则 -> "2"
	 * @throws Exception
	 */
	public static String isAfter(Object dateObj1, Object dateObj2) throws Exception {
		Date date1 = ObjToDate(dateObj1);
		Date date2 = ObjToDate(dateObj2);
		if(date1.before(date2)) {
			return "0";
		} else if(date1.after(date2)) {
			return "1";
		} else {
			return "2";
		}
	}
	
	/**
	 * 比较后一个时间dateObj2比前一个时间dateObj1晚多少. compareDate(dateObj1,dateObj2)[0] 就是dateObj2比dateObj1晚的年数(Integer类型的)
	 * @param dateObj1
	 * @param dateObj2
	 * @return Integer[] {year, month, day, hour, minute, second, isAfter} 最后一个数isAfter代表第二个日期是否比第一个日期要晚(晚->1. 早 -> 0)
	 * @throws Exception 
	 */
	public static Integer[] compareDate(Object dateObj1, Object dateObj2) throws Exception {
		Date date1 = ObjToDate(dateObj1);
		Date date2 = ObjToDate(dateObj2);
		int year = date2.getYear() - date1.getYear();
		int month = date2.getMonth() - date1.getMonth();
		int day = date2.getDate() - date1.getDate();
		int hour = date2.getHours() - date1.getHours();
		int minute = date2.getMinutes() - date1.getMinutes();
		int second = date2.getSeconds() - date1.getSeconds();
		int isAfter = date2.after(date1)?1:0;	//第二个日期是否比第一个日期要晚(晚->1. 早 -> 0)
		if (month < 0) {
			year -= 1;
			month += 12;
		}
		if (day < 0) {
			month -= 1;
			day += 30;
		}
		if (hour < 0) {
			day -= 1;
			hour += 24;
		}
		if (minute < 0) {
			hour -= 1;
			minute += 60;
		}
		if (second < 0) {
			minute -= 1;
			second += 60;
		}
//		StringBuffer sb = new StringBuffer();
//		sb.append(year + "年").append(month + "月").append(day + "天").append(hour + "小时").append(minute + "分钟").append(second + "秒");
//		System.out.println(sb);
		return new Integer[] {year, month, day, hour, minute, second,isAfter};
	}
	
	/**
	 * 把obj转化成Integer
	 * @param obj
	 * @return
	 */
	public static Integer parseInt(Object obj) {
		if(isBlank(obj) || "null".equalsIgnoreCase(obj + "")) {
			return 0;
		} 
		return Integer.parseInt(obj + "");
	}
	
	/**
	 * 生成制定长度的纯数字随机字符串
	 * @param length 制定的字符串长度
	 * return String 纯数字的随机字符串
	 */
	public static final String getRandomNumString(int length) {
		Random randGen = null;
		char[] numbersAndLetters = null;
		
		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			randGen = new Random();
			numbersAndLetters = ("0123456789").toCharArray();
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(10)];
		}
		randGen = null;
		numbersAndLetters = null;
		return new String(randBuffer);
	}

	public static String getKSSJbyZKZH(String zkzh) {
		String yearString =zkzh.substring(0, 2);
		String monthString="1".equals(zkzh.substring(2, 3))?"3":("2".equals(zkzh.substring(2, 3))?"9":"");
		
		return yearString+"年"+monthString+"月";
	}
	
	/**
	 * 计算两个日期相隔多久，年、月或者日
	 * @param date1 小日期
	 * @param date2 大日期
	 * @param ss 想要的相隔种类 year  month  date
	 * @throws Exception
	 */
	public static Integer getDateFrm2Date(Date date1,Date date2,String ss) throws Exception
	{
		Integer xx = 0;
		Calendar aft = Calendar.getInstance();
		Calendar bef = Calendar.getInstance();
		aft.setTime(date1);
		bef.setTime(date2);
		//System.out.println(bef.get(Calendar.DATE) + "---:::---" + aft.get(Calendar.DATE));
		if("year".equals(ss))
		{
			xx = bef.get(Calendar.YEAR)-aft.get(Calendar.YEAR);
		}
		if("month".equals(ss))
		{
			if(bef.get(Calendar.YEAR) == aft.get(Calendar.YEAR))//如果是同年
			{
				xx = bef.get(Calendar.MONTH)-aft.get(Calendar.MONTH);
			}
			else//如果不同年
			{
				xx = 12*(bef.get(Calendar.YEAR)-aft.get(Calendar.YEAR)) + (bef.get(Calendar.MONTH) - aft.get(Calendar.MONTH));
			}
		}
		if("date".equals(ss))
		{
			long kk = (bef.getTimeInMillis()-aft.getTimeInMillis())/(1000*60*60*24);
			xx = Integer.parseInt(String.valueOf(kk));
		}
		return xx;
	}
	
	/**
	 * 根据生日和制定日期计算当前生日
	 * @param birthday
	 * @param pointdate
	 * @return
	 * @throws Exception
	 */
	public static Integer getAgeFrmBirthday(Date birthday,Date pointdate) throws Exception
	{
		Integer age = getDateFrm2Date(birthday,pointdate,"month")/12;
		int aa = birthday.getDate();
		int bb = pointdate.getDate();
		
		if(aa < bb )
		{
			age -- ;
		}
		
		return age;
	}
	
	/**
	 * 将str转换为arr数组返回
	 * @param str
	 * @return
	 */
	public static String[] strToStrarry(String str)
	{
		//去空格
		str = str.trim();
		//先将字符串转换为字符数组
		char[] ss = str.toCharArray();
		//定义一个字符串数组
		String[] arr = new String[ss.length];
		//将字符数组逐个写入字符串数组
		for (int i = 0; i < ss.length; i++)
		{
			arr[i] = ss[i]+"";
		}
		return arr;
	}
	/**
	 * 判断arry中是否存在str
	 * @param arry
	 * @param str
	 * @return
	 */
	public static Boolean isStrArryContainsValue(String[] arry,String str)
	{
		for (int i = 0; i < arry.length; i++) 
		{
			if(str.equals(arry[i]))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断arry中是否存在str
	 * @param arry
	 * @param xx
	 * @return
	 */
	public static Boolean isIntArryContainsValue(Integer[] arry,int xx)
	{
		for (int i = 0; i < arry.length; i++) 
		{
			if(xx == arry[i])
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 左边补0(或者其他的char) 如 leftConcat(3, char '0', Integer 3)  -> 003
	 * 若length小于originalStr, 返回元字符串右边的length位 如 leftConcat("1234", '0', 3) -> 234
	 * @param originalStr 原字符串
	 * @param toAdd 用来补的char
	 * @param length 补后的长度
	 * @return 补后的字符串
	 */
	public static String leftConcat(String originalStr, char toAdd, Integer length) {
		Integer originalLen = originalStr.length();
		if(originalLen > length) {
			return originalStr.substring(originalLen - length, originalLen);
		}
		return getDuplicateChar(toAdd, length - originalLen) + originalStr;
	}
	
	/**
	 * 返回length个c字符串的重复叠加组合
	 * @param c			要重复的字符串
	 * @param length	重复的次数
	 * @return	重复后的字符串
	 */
	public static String getDuplicateChar(Object c, int length) {
		int i = 0;
		String resString = "";
		for(i = 0; i < length; i++) {
			resString += c;
		}
		return resString;
	}
	
	/**
	 * 获取前几个月
	 * @param num
	 * @return
	 */
	public static List<Map<String,Object>> getLastSeveralMonths(int num,String key)
	{
		
		List<Map<String,Object>> listLastMonths = new ArrayList<Map<String,Object>>();
		
        Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1); //要先+1,才能把本月的算进去</span>  
        for(int i=0; i<num; i++){  
        	Map<String,Object> lastMonths = new HashMap<String, Object>();  
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1); //逐次往前推1个月  
            
            String last1Months = cal.get(Calendar.YEAR)+ "-" + leftConcat((cal.get(Calendar.MONTH)+1)+"", '0', 2);
            lastMonths.put(key, last1Months);
            listLastMonths.add(lastMonths);
        }  
		return listLastMonths;
	}

	/**
	 * 生成彩票数组
	 * @return
     */
	public static String[] getLotteryArr()
	{
		//红球数组
		String[] randNum1 = new String[33];
		for(int i=0;i<randNum1.length;i++) {
			randNum1[i] = leftConcat((i + 1) + "", '0', 2);
		}
		//蓝球数组
		String[] randNum2 = new String[16];
		for(int i=0;i<randNum2.length;i++) {
			randNum2[i] = leftConcat((i + 1) + "", '0', 2);
		}
		//随机
		Random random = new Random();
		//结果数组
		String[] result = new String[7];
		int index = 0;
		//随机取红球
		for (int i=0;i<6;i++)
		{
			index = random.nextInt(32);
			result[i] = randNum1[index];
		}
		//随机取蓝球
		index = random.nextInt(15);
		result[6] = randNum2[index];

		//遍历输出result
//		for (int i=0;i<result.length;i++)
//		{
//			System.out.println(result[i]);
//		}
		return result;
	}

	/**
	 * 生成彩票字符串
	 * @return
	 */
	public static String getLotteryStr()
	{
		//红球数组
		String[] redBall = getBall(33);
		//蓝球数组
		String[] blueBall = getBall(16);

		//标识是否已使用过的数组
		boolean[] used = new boolean[redBall.length];

		//随机
		Random random = new Random();
		//结果字符串
		String lottery = "";
		//数组下标
		int index = 0;
		//红球个数
		int ii = 0;
		//循环标志
		boolean bb = true;
		//随机去红球
		while (bb){
			//随机生成下标
			index = random.nextInt(32);
			//如果该下标已经使用过了，从新随机
			if(used[index]){
				continue;
			}
			//取红球
			lottery += "," + redBall[index];
			//设置该下标为已经使用过
			used[index] = true;
			//红球个数加1
			ii ++ ;
			//如果红球个数已经达到6个，跳出循环不再生成
			if (ii == 6){
				bb = false;
			}
		}

//		//随机取红球
//		for (int i=0;i<6;i++)
//		{
//			index = random.nextInt(32);
//			lottery += "," + redBall[index];
//		}
		//随机取蓝球
		index = random.nextInt(15);
		lottery += "," + blueBall[index];

		return lottery.substring(1);
	}

	/**
	 * 生成随机排列的数组
	 * @return
     */
	public static String[] getBall(int len){
		//定义一个存储红球的list
		List<String> list = new ArrayList<String>();
		//定义一个存储红球的String数组
		String[] ball = new String[len];
		//在红球数组中存入1-33的数字字符串，如果只有1位，昨天添'0'
		for(int i=0;i<ball.length;i++) {
			ball[i] = leftConcat((i + 1) + "", '0', 2);
		}
		//将redBall数组转换成list
		list = Arrays.asList(ball);
		//随机排列list
		Collections.shuffle(list,new Random());
		//将list转换成String数组
		ball = (String[])list.toArray();

		return ball;
	}

	/**
	 * 时间戳转换成日期+时间字符串
	 * @param timeStamp
	 * @return
     */
	public static String timeStampToDateStr(int timeStamp){
		Date date = new Date(timeStamp * 1000L);
		String dateStr = DateToStr(date,"yyyy-MM-dd HH:mm:ss");
		return dateStr;
	}

	/**
	 * 获取当前时间戳
	 * @return
	 */
	public static String getTimeStamp(){
		long time = System.currentTimeMillis();
		String t = String.valueOf(time/1000);
		return t;
	}

	/**
	 * 获取配置文件里面的值
	 * @param propertiesFileName
	 * @param keyName
	 * @return
	 */
	public static String getPropertiesValue(String propertiesFileName,String keyName){
		ResourceBundle resource = ResourceBundle.getBundle(propertiesFileName);//test为属性文件名，放在包com.mmq下，如果是放在src下，直接用test即可
		String value = resource.getBundle(propertiesFileName).getString(keyName);
		return value;
	}

	/**
	 * 日期格式字符串转换成时间戳
	 * @param dateStr 字符串日期
	 * @param format 如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String date2TimeStamp(String dateStr,String format){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return String.valueOf(sdf.parse(dateStr).getTime()/1000);
		} catch (Exception e) {
			log.error("异常>>",e);
		}
		return "";
	}

	/**
	 * 上传文件
	 * @param path
	 * @param file
	 * @return
	 */
	public static String uploadFile(String path, MultipartFile file) throws IOException{

		String filePath = "";

		String fileName = file.getOriginalFilename();
		String prefix = fileName.substring(fileName.lastIndexOf("."));
		fileName = UUID.randomUUID().toString() + prefix;
		// System.out.println("保存路径 " + path);
		File targetFile = new File(path, fileName);
		if(!targetFile.exists()){
			targetFile.mkdirs();
		}
		file.transferTo(targetFile);
		filePath = path + "/" + fileName;
		return filePath;
	}

	/**
	 * 根据路径判断文件是否存在
	 * @param path
	 * @return
	 */
	public static boolean isFile(String path){

		File file = new File(path);

		if(file.exists()){
			return true;
		}

		return false;
	}

	/**
	 * 将实体类对象转换成map
	 * @param object
	 * @return
	 */
	public static Map<String,Object> bean2Map(Object object){
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(object);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!"class".equals(name)) {
					params.put(name, propertyUtilsBean.getNestedProperty(object, name));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String toChinese(String string) {
		String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };

		String result = "";

		int n = string.length();
		for (int i = 0; i < n; i++) {

			int num = string.charAt(i) - '0';

			if (i != n - 1 && num != 0) {
				result += s1[num] + s2[n - 2 - i];
			} else {
				result += s1[num];
			}
		}
		return result;

	}

	public static void main(String[] args) throws Exception
	{
//		for(int i=0;i<5;i++)
//		{
//			System.out.println(getLotteryStr());
//		}

//		boolean bb = true;
//		long t1 = System.currentTimeMillis();
//		while(bb){
//			long t2 = System.currentTimeMillis();
//			System.out.println(getLotteryStr());
//			if(t2-t1 > 5*60*1000){
//				bb = false;
//			}
//		}
		Object ss = "2018-09-09 09:09:09.0";
		System.out.println(((Double) ss).toString());
	}
	
}
