package com.seekon.mars.wf.engine.calendar;

public class BusinessCalendarConst {
  // 时间格式,如HH:mm。
  public static final String HOUR_FORMAT = "hour.format";

  //日期格式，如yyyy-MM-dd
  public static final String DAY_FORMAT = "day.format";

  //一天的工作时段，例如8:30-12:00 & 13:00-17:00 表示早上8点半到中午12点和下午1点到5点
  public static final String BUSINESS_TIME = "business.time";

  //每天的工作时间，例如：如果business_time="8:30-12:00 & 13:30-17:30"，则每天的工作时间长度是7个半小时，
  //则HOURS_OF_BUSINESS_DAY只能等于7.5。因此BUSINESS_TIME和HOURS_OF_BUSINESS_DAY要相匹配。
  public static final String HOURS_OF_BUSINESS_DAY = "business.day.expressed.in.hours";

  public static final String BUSINESS_TIME_MONDAY = "business.week.monday";

  public static final String BUSINESS_TIME_TUESDAY = "business.week.tuesday";

  public static final String BUSINESS_TIME_WEDNESDAY = "business.week.wednesday";

  public static final String BUSINESS_TIME_THURSDAY = "business.week.thursday";

  public static final String BUSINESS_TIME_FRIDAY = "business.week.friday";

  public static final String BUSINESS_TIME_SATURDAY = "business.week.saturday";

  public static final String BUSINESS_TIME_SUNDAY = "business.week.sunday";
  
  public static final String BUSINESS_TIME_DEFAULTDAY = "business.week.defaultday";
  
  public static final String BUSINESS_DAY_EXPRESSED_WORKDAY ="business.day.expressed.workday";
  
  public static final String HOLIDAY_KEY="holiday";
}
