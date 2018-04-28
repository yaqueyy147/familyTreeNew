package com.witkey.familyTree.exportexcel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {
	public static String DEFAULT_FORMAT = "yyyy-MM-dd";
	
	
	
	/**
	 * 获得当前日期上月最后一天的日期
	 */
	public static Date getPreviousMonthEnd(Date date){
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(date);
		lastDate.add(Calendar.MONTH,-1);//减一个月
		lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天
		return lastDate.getTime();
	}

	
	/**
	 * 计算两个日期之间相差的天数
	 * @param smdate	较小的时间
	 * @param bdate		较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static Long daysBetween(Date smdate, Date bdate){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis(); // smdate
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis(); // bdate
			long between_days = (time2 - time1) / (1000 * 3600 * 24);

			return Long.parseLong(String.valueOf(between_days));
		} catch (Exception e) {
			throw new RuntimeException("daysBetween error"+smdate+";"+bdate,e);
		}
	}

	/**
	 * 时间相加
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	public static Date addMonth(Date date, int month) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}

	public static int getDay(Date d) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 
	 * return yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String convertDateToString(Date date) {
		SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
		String sDate = f.format(date);
		return sDate;
	}
	public static Date convertStringToDate(String strDate)
			throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(DEFAULT_FORMAT);
		Date date = null;
		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}
		return date;
	}
	
	
	public static String format(String timestamp) {
		Date date=new Date(Timestamp.valueOf(timestamp).getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", getDefaultLocale());
		return sdf.format(date);
	}
	private static Locale getDefaultLocale() {
		Locale locale = null;
		try {
			locale = Locale.CHINA;
		} catch (IllegalStateException ex) {
			locale = Locale.CHINA;
		}
		return locale;
	}
}
