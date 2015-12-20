package com.chaoo.trackloca.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	 
	 public static Date todayTrim(){
		 return trim(new Date());
	 }
	 
	 public static Date tommorowTrim(){
		 return nextDayTrim(new Date());
	 }
	 
	 public static Date koTodayTrim(){
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		 return trim(calendar.getTime());
	 }
	 
	 public static Date koTommorowTrim(){
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
		 return nextDayTrim(new Date());
	 }

	 
	 
	 public static Date nextDayTrim(Date date){
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(date);
		 calendar.add(Calendar.DATE, 1);
		 return trim(calendar.getTime());
	 }
	 
	 public static Date trim(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.set(Calendar.MILLISECOND, 0);
	        calendar.set(Calendar.SECOND, 0);
	        calendar.set(Calendar.MINUTE, 0);
	        calendar.set(Calendar.HOUR, 0);
	        return calendar.getTime();
	 }
	 
	 public static Date getFromDate(){
		 SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMddHHmmss");
		 TimeZone tz = TimeZone.getTimeZone("Asia/Seoul"); 
		 formatter.setTimeZone(tz);
		 try {
			 return formatter.parse("20140101000000");
		 } catch (ParseException e) {
			 e.printStackTrace();
		 }
		return null;
	 }
	 
	 public static Date getEndDate(){
		 SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMddHHmmss");
		 TimeZone tz = TimeZone.getTimeZone("Asia/Seoul"); 
		 formatter.setTimeZone(tz);
		 try {
			 return formatter.parse("21990101000000");
		 } catch (ParseException e) {
			 e.printStackTrace();
		 }
		 return null;
	 }
}
