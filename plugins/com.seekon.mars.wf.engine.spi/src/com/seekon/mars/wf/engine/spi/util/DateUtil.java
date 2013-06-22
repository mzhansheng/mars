package com.seekon.mars.wf.engine.spi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

  public static String getDate() {
    return getDate(new Date(System.currentTimeMillis()));
  }

  public static String getDate(Date date) {
    return formatDateTime(date, "yyyy-MM-dd");
  }

  public static String getTime() {
    return getTime(new Date(System.currentTimeMillis()));
  }

  public static String getTime(Date time) {
    return formatDateTime(time, "yyyy-MM-dd HH:mm:ss");
  }

  public static int getYear() {
    return getYear(new Date(System.currentTimeMillis()));
  }

  public static int getYear(Date date) {
    return getCalendarField(date, Calendar.YEAR);
  }

  public static int getMonth() {
    return getYear(new Date(System.currentTimeMillis()));
  }

  public static int getMonth(Date date) {
    return getCalendarField(date, Calendar.MONTH);
  }

  public static int getCalendarField(Date date, int field) {
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(date);
    return calendar.get(field);
  }

  public static Date parseTime(String dateString) throws ParseException {
    if (dateString.indexOf("-") > 0) {
      return parseDate(dateString, "yyyy-MM-dd HH:mm:ss");
    } else {
      return parseDate(dateString, "yyyyMMddHHmmss");
    }
  }

  //��ָ�
  public static Date parseHourTime(String dateString) throws ParseException {
    if (dateString.indexOf("-") > 0) {
      return parseDate(dateString, "yyyy-MM-dd HH:mm");
    } else {
      return parseDate(dateString, "yyyyMMddHHmmss");
    }

  }

  public static Date parseDate(String dateString, String format) throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
    return (Date) simpleDateFormat.parseObject(dateString);
  }

  public static String formatDateTime(Object date, String format) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    return dateFormat.format(date);
  }

  public static void main(String[] args) {
    try {
      Date d1 = parseHourTime("20100129233658");
      Date d2 = parseHourTime("2009-10-09 09:26");
      System.out.println(d1.compareTo(d2));
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

}
