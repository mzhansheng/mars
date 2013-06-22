package com.seekon.mars.wf.engine.calendar;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.seekon.mars.wf.engine.spi.util.DateUtil;

/**
 * 缺省的日历服务实现类。请在业务系统中扩展该类。
 */
public class DefaultCalendarService implements ICalendarService {
  /**
   * 用于日历换算的属性。 
   * 缺省实现类在构造函数中设置了日历的相关属性，你可以扩展这种实现方式，将日历属性放在配置文件中
   */
  private Properties businessCalendarProperties = new Properties();

  public DefaultCalendarService() {
    try {
      businessCalendarProperties.load(DefaultCalendarService.class
        .getResourceAsStream("calendarConfig.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 默认方法，返回时间间隔后的日期
   * @param fromDate 开始时间
   * @param hour 间隔小时
   * @return 日期
   * @throws ParseException
   */
  public Date dateAfter(String fromDate, Double hour) throws ParseException {
    if (fromDate.indexOf("-") > 0) {
      return dateAfter(DateUtil.parseDate(fromDate, "yyyy-MM-dd HH:mm"), new Duration((hour.intValue() * 60),
        Duration.MINUTE));
    } else {
      return dateAfter(DateUtil.parseDate(fromDate, "yyyyMMddHHmmss"), new Duration((hour.intValue() * 60),
        Duration.MINUTE));
    }
  }

  /**
   * 计算一定时间间隔后的日期。 在本缺省实现中只区分工作日/自然日，工作时/自然时；其他都按照自然时间间隔计算。
   * 这种实现方法已经满足绝大多数业务需求（时间差之后的日期）。
   * @param fromDate
   *            开始日期
   * @param duration
   *            时间间隔：时间差值
   * @return 返回时间差之后的日期
   * @throws ParseException 
   */
  public Date dateAfter(Date fromDate, Duration duration) throws ParseException {
    if ((duration.getUnit().equals(Duration.SECOND) && !duration.isBusinessTime())
      || (duration.getUnit().equals(Duration.MINUTE) && !duration.isBusinessTime())
      || duration.getUnit().equals(Duration.WEEK) || duration.getUnit().equals(Duration.MONTH)
      || duration.getUnit().equals(Duration.YEAR)
      || (duration.getUnit().equals(Duration.DAY) && !duration.isBusinessTime())
      || (duration.getUnit().equals(Duration.HOUR) && !duration.isBusinessTime())) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(fromDate);
      if (duration.getUnit().equals(Duration.MONTH)) {
        cal.add(Calendar.MONTH, duration.getValue());
      } else if (duration.getUnit().equals(Duration.YEAR)) {
        cal.add(Calendar.YEAR, duration.getValue());
      } else if (duration.getUnit().equals(Duration.DAY)) {
        cal.add(Calendar.DATE, duration.getValue());
      } else if (duration.getUnit().equals(Duration.HOUR)) {
        cal.add(Calendar.HOUR, duration.getValue());
      } else if (duration.getUnit().equals(Duration.MINUTE)) {
        cal.add(Calendar.MINUTE, duration.getValue());
      } else if (duration.getUnit().equals(Duration.SECOND)) {
        cal.add(Calendar.SECOND, duration.getValue());
      } else if (duration.getUnit().equals(Duration.WEEK)) {
        cal.add(Calendar.DATE, duration.getValue() * 7);
      }
      return cal.getTime();
    } // 计算工作日间隔
    else if (duration.getUnit().equals(Duration.DAY) && duration.isBusinessTime()) {
      float hoursPerDay = Float.parseFloat(this.getBusinessCalendarProperties().getProperty(
        BusinessCalendarConst.HOURS_OF_BUSINESS_DAY));
      int totalDurationInMillseconds = (int) (duration.getValue() * hoursPerDay * 60 * 60 * 1000);
      return businessDateAfter(fromDate, totalDurationInMillseconds);

    } // 计算工作时间隔
    else if (duration.getUnit().equals(Duration.HOUR) && duration.isBusinessTime()) {
      int totalDurationInMillseconds = (int) (duration.getValue() * 60 * 60 * 1000);
      return businessDateAfter(fromDate, totalDurationInMillseconds);
    } else if (duration.getUnit().equals(Duration.MINUTE) && duration.isBusinessTime()) {
      int totalDurationInMillseconds = (int) (duration.getValue() * 60 * 1000);
      return businessDateAfter(fromDate, totalDurationInMillseconds);
    } else if (duration.getUnit().equals(Duration.SECOND) && duration.isBusinessTime()) {
      int totalDurationInMillseconds = (int) (duration.getValue() * 1000);
      return businessDateAfter(fromDate, totalDurationInMillseconds);
    }
    return null;
  }

  /**
   * 计算一定毫秒数间隔后的日期
   * @param fromDate 业务开始日期
   * @param totalDurationInMillseconds 毫秒差值
   * @return 毫秒间隔后的日期
   * @throws ParseException 
   */
  protected Date businessDateAfter(Date fromDate, int totalDurationInMillseconds) throws ParseException {
    int remaining = totalDurationInMillseconds;
    int workingTimeOfDay = 0;
    Date theDate = fromDate;
    int rtnInt = getBusinessHourPart(theDate);
    SimpleDateFormat dateFormat = new SimpleDateFormat(this.businessCalendarProperties
      .getProperty(BusinessCalendarConst.HOUR_FORMAT));
    boolean spanDay = false;
    boolean isStart = false;
    while (remaining > 0) {
      if (!isStart) {
        try {
          if (rtnInt != 1)
            workingTimeOfDay = this.getReleaseWorkingTime(theDate);
          isStart = true;
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        workingTimeOfDay = this.getTotalWorkingTime(theDate);
      }
      remaining = remaining - workingTimeOfDay;

      if (remaining > 0) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(theDate);
        if (rtnInt == 1) {
          cal.add(Calendar.DATE, 1);
        }
        while (!this.isBusinessDay(DateUtil.parseDate(DateUtil.getDate(cal.getTime()),
          businessCalendarProperties.getProperty(BusinessCalendarConst.DAY_FORMAT)))) {
          cal.add(Calendar.DATE, 1);
        }
        spanDay = true;

        // 定位到新的一天的开始工作时间点
        String businessTime = this.getBusinessTime(cal.getTime());
        int idx = businessTime.indexOf("-");
        Date dateTmp = null;
        try {
          dateTmp = dateFormat.parse(businessTime.substring(0, idx));
        } catch (ParseException ex) {
          Logger.getLogger(DefaultCalendarService.class.getName()).log(Level.SEVERE, null, ex);
        }
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(dateTmp);
        cal.set(Calendar.HOUR_OF_DAY, cal2.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal2.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal2.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal2.get(Calendar.MILLISECOND));
        theDate = cal.getTime();
      }
    }

    if (remaining <= 0) {
      remaining = remaining + workingTimeOfDay;
      String businessTime = getBusinessTime(theDate);
      if (businessTime != null) {
        StringTokenizer stringTokenizer = new StringTokenizer(businessTime, "&");
        int totalTime = 0;
        while (stringTokenizer.hasMoreTokens()) {
          String bizTimeSpan = stringTokenizer.nextToken().trim();
          Calendar cal = Calendar.getInstance();
          cal.setTime(theDate);
          // 如果跨了日期或者当前时间在工作时间段之前，则定位到下一时间段的起始点
          int inTheSpan = testTimeInTheTimeSpan(theDate, bizTimeSpan);
          if (spanDay || inTheSpan == -1) {
            int index1 = bizTimeSpan.indexOf(":");
            int index2 = bizTimeSpan.indexOf("-");
            int hour = Integer.parseInt(bizTimeSpan.substring(0, index1));
            int minute = Integer.parseInt(bizTimeSpan.substring(index1 + 1, index2));

            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            theDate = cal.getTime();
          } else if (inTheSpan == 1) {
            continue;
          } else if (inTheSpan == 0) {
            String timeStart = dateFormat.format(theDate);
            int indexTmp = bizTimeSpan.indexOf("-");
            bizTimeSpan = timeStart + bizTimeSpan.substring(indexTmp);
          }

          totalTime = getTotalWorkingTime(bizTimeSpan);
          if (totalTime < remaining) {
            remaining = remaining - totalTime;

          } else {
            cal.add(Calendar.MILLISECOND, remaining);
            theDate = cal.getTime();
            break;
          }
        }
      }
    }

    return theDate;
  }

  /**
   * 获得当天的(剩余)工时，以毫秒为单位
   * @param date 当前日期
   * @return
   * @throws ParseException 
   */
  public int getTotalWorkingTime(Date date) throws ParseException {
    if (!this.isBusinessDay(date)) {
      return 0;// 非工作日的工时为0
    }

    String businessTime = getBusinessTime(date);
    if (businessTime == null) {
      return 0;
    }

    // 判断date属于哪个时间段
    SimpleDateFormat dFormat = new SimpleDateFormat(this.businessCalendarProperties
      .getProperty(BusinessCalendarConst.HOUR_FORMAT));
    String paramTimeStr = dFormat.format(date);
    StringTokenizer stringTokenizer = new StringTokenizer(businessTime, "&");
    List timeSpanList = new ArrayList();
    while (stringTokenizer.hasMoreTokens()) {
      String timeSpan = stringTokenizer.nextToken();
      int isInTheSpan = testTimeInTheTimeSpan(date, timeSpan);
      if (isInTheSpan == -1) {
        timeSpanList.add(timeSpan);
      } else if (isInTheSpan == 0) {
        String tmp = paramTimeStr + timeSpan.substring(timeSpan.indexOf("-"));
        timeSpanList.add(tmp);
      }

    }

    int totalTime = 0;
    for (int i = 0; i < timeSpanList.size(); i++) {
      String timeSpan = (String) timeSpanList.get(i);
      totalTime = totalTime + getTotalWorkingTime(timeSpan);
    }

    return totalTime;
  }

  /**
   * 检查时间点是否在给定的区间内
   * @param d 检查日期
   * @param timeSpan 时间区间 例如：8:30-17:00
   * @return 0-在给定的区间内，-1在给定的区间前，1在给定的区间后
   */
  private int testTimeInTheTimeSpan(Date d, String timeSpan) {
    SimpleDateFormat dFormat = new SimpleDateFormat(this.businessCalendarProperties
      .getProperty(BusinessCalendarConst.DAY_FORMAT));
    String dayStr = dFormat.format(d);
    dFormat.applyPattern(this.businessCalendarProperties.getProperty(BusinessCalendarConst.DAY_FORMAT) + " "
      + this.businessCalendarProperties.getProperty(BusinessCalendarConst.HOUR_FORMAT));
    if (timeSpan.indexOf("&") > 0) {
      String timeSpans[] = timeSpan.split("&");
      int r = processTimeSpan(d, timeSpans[0], dFormat, dayStr);
      if (r == 0) {
        return 0;
      } else if (r == 1) {
        int s = processTimeSpan(d, timeSpans[1], dFormat, dayStr);
        if (s == 0)
          return 0;
        else if (s == 1) {
          return 1;
        } else {
          return -1;
        }
      } else {
        return -1;
      }
    } else {
      return processTimeSpan(d, timeSpan, dFormat, dayStr);
    }
  }

  private int processTimeSpan(Date d, String timeSpan, SimpleDateFormat dFormat, String dayStr) {
    int idx = timeSpan.indexOf("-");
    String date1Str = dayStr + " " + timeSpan.substring(0, idx);
    String date2Str = dayStr + " " + timeSpan.substring(idx + 1);
    Date date1 = null;
    Date date2 = null;
    try {
      date2 = dFormat.parse(date2Str);
    } catch (ParseException ex) {
      Logger.getLogger(DefaultCalendarService.class.getName()).log(Level.SEVERE, null, ex);
    }
    try {
      date1 = dFormat.parse(date1Str);
    } catch (ParseException ex) {
      Logger.getLogger(DefaultCalendarService.class.getName()).log(Level.SEVERE, null, ex);
    }
    if (d.before(date1)) {
      return -1;
    } else if (d.after(date2)) {
      return 1;
    }

    return 0;
  }

  //上班时间
  private String getBusinessTime(Date date) {
    String businessTime = this.businessCalendarProperties.getProperty(BusinessCalendarConst.BUSINESS_TIME);
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    String businessTime2 = null;
    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    switch (dayOfWeek) {
    case 1:
      businessTime2 = this.businessCalendarProperties.getProperty(BusinessCalendarConst.BUSINESS_TIME_SUNDAY);// 周日
      break;

    case 2:
      businessTime2 = this.businessCalendarProperties.getProperty(BusinessCalendarConst.BUSINESS_TIME_MONDAY);// 周1
      break;

    case 3:
      businessTime2 = this.businessCalendarProperties
        .getProperty(BusinessCalendarConst.BUSINESS_TIME_TUESDAY);// 周2
      break;

    case 4:
      businessTime2 = this.businessCalendarProperties
        .getProperty(BusinessCalendarConst.BUSINESS_TIME_WEDNESDAY);// 周3
      break;

    case 5:
      businessTime2 = this.businessCalendarProperties
        .getProperty(BusinessCalendarConst.BUSINESS_TIME_THURSDAY);// 周4
      break;

    case 6:
      businessTime2 = this.businessCalendarProperties.getProperty(BusinessCalendarConst.BUSINESS_TIME_FRIDAY);// 周5
      break;

    case 7:
      businessTime2 = this.businessCalendarProperties
        .getProperty(BusinessCalendarConst.BUSINESS_TIME_SATURDAY);// 周六
      break;
    default:
      businessTime2 = this.businessCalendarProperties
        .getProperty(BusinessCalendarConst.BUSINESS_TIME_DEFAULTDAY);

    }

    List workDays = extractWorkdayOfYear();
    try {
      Date dStr = DateUtil.parseDate(DateUtil.getDate(date), businessCalendarProperties
        .getProperty(BusinessCalendarConst.DAY_FORMAT));
      if (workDays.contains(dStr)) {
        businessTime2 = businessCalendarProperties
          .getProperty(BusinessCalendarConst.BUSINESS_TIME_DEFAULTDAY);
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }

    if (businessTime2 != null) {
      businessTime = businessTime2;
    }

    return businessTime;
  }

  /**
   * 获得时间段的工作时间，以毫秒表示
   * 
   * @param timeSpan
   *            例如:8:30-12:00
   * @return
   */
  private int getTotalWorkingTime(String timeSpan) {
    if (timeSpan == null) {
      return 0;
    }

    StringTokenizer stringTokenizer = new StringTokenizer(timeSpan, "-");
    String dStr1 = null;
    String dStr2 = null;
    if (stringTokenizer.hasMoreTokens()) {
      dStr1 = stringTokenizer.nextToken();
    }

    if (stringTokenizer.hasMoreTokens()) {
      dStr2 = stringTokenizer.nextToken();
    }

    if (dStr1 == null || dStr2 == null) {
      return 0;
    }

    SimpleDateFormat dFormat = new SimpleDateFormat(this.businessCalendarProperties
      .getProperty(BusinessCalendarConst.HOUR_FORMAT));
    Date d1 = null;
    try {
      d1 = dFormat.parse(dStr1);
    } catch (ParseException ex) {
      Logger.getLogger(DefaultCalendarService.class.getName()).log(Level.SEVERE, null, ex);
    }
    Date d2 = null;
    try {
      d2 = dFormat.parse(dStr2);
    } catch (ParseException ex) {
      Logger.getLogger(DefaultCalendarService.class.getName()).log(Level.SEVERE, null, ex);
    }

    if (d1 == null || d2 == null) {
      return 0;
    }

    return (int) (d2.getTime() - d1.getTime());
  }

  /**
   * 得到一天的剩余毫秒数
   * @return
   * @throws ParseException,Exception 
   * @throws Exception 
   */
  private int getReleaseWorkingTime(Date d) throws ParseException, Exception {
    if (!this.isBusinessDay(d)) {
      return 0;
    }
    String workSpan = getBusinessTime(d);
    String nowStr = DateUtil.getDate(d);
    if (workSpan != null && workSpan.length()>0) {
      String pans[] = workSpan.split("&");
      String sPans[] = pans[0].trim().split("-");
      String ssDate = nowStr + " " + sPans[0].trim();
      String esDate = nowStr + " " + sPans[1].trim();
      String ePans[] = pans[1].trim().split("-");
      String seDate = nowStr + " " + ePans[0].trim();
      String eeDate = nowStr + " " + ePans[1].trim();

      Date startSDate = DateUtil.parseHourTime(ssDate);
      Date startEDate = DateUtil.parseHourTime(esDate);
      Date endSDate = DateUtil.parseHourTime(seDate);
      Date endEDate = DateUtil.parseHourTime(eeDate);

      int ssBetween = (int) (d.getTime() - startSDate.getTime());
      int seBetween = (int) (startEDate.getTime() - d.getTime());
      int esBetween = (int) (d.getTime() - endSDate.getTime());
      int eeBetween = (int) (endEDate.getTime() - d.getTime());
      int total = (int) ((startEDate.getTime() - startSDate.getTime()) + (endEDate.getTime() - endSDate
        .getTime()));
      if (ssBetween > 0 && seBetween > 0) {
        return total - ssBetween;
      } else if (esBetween > 0 && eeBetween > 0) {
        return total - ((int) (startEDate.getTime() - startEDate.getTime() + esBetween));
      } else if (seBetween < 0 && esBetween < 0) {
        return total - ((int) (startEDate.getTime() - startEDate.getTime()));
      } else if (eeBetween < 0) {
        return (int) ((startEDate.getTime() - startEDate.getTime()) + (endEDate.getTime() - endSDate
          .getTime()));
      }
    }

    return 0;
  }

  //日期是否是工作日
  public boolean isBusinessDay(Date d) throws ParseException {
    if (d == null) {
      return false;
    }
    List holidayList = holidayDateofYear();
    if (holidayList.contains(DateUtil.parseDate(DateUtil.getDate(d), businessCalendarProperties
      .getProperty(BusinessCalendarConst.DAY_FORMAT)))) {
      return false;
    }

    List workDays = extractWorkdayOfYear();
    if (workDays.contains(DateUtil.parseDate(DateUtil.getDate(d), businessCalendarProperties
      .getProperty(BusinessCalendarConst.DAY_FORMAT)))) {
      return true;
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(d);
    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    if (dayOfWeek == 1 || dayOfWeek == 7) {
      return false;
    }

    return true;
  }

  /**
   * 返回日期时间在工作期间的范围：-1 工作起点的一天开始部分；0 工作期间部分（包括工作休息间隔）；1 工作下班到一天结束（23：59：59点）
   * @param d
   * @return
   */
  public int getBusinessHourPart(Date d) {
    int returnInt = 0;
    String workSpan = getBusinessTime(d);
    String nowStr = DateUtil.getDate(d);
    if (workSpan != null) {
      String pans[] = workSpan.split("&");
      String sPans[] = pans[0].trim().split("-");
      String sDate = nowStr + " " + sPans[0].trim();
      String ePans[] = pans[1].trim().split("-");
      String eDate = nowStr + " " + ePans[1].trim();
      try {
        Date startSDate = DateUtil.parseHourTime(sDate);
        Date endEDate = DateUtil.parseHourTime(eDate);
        if (d.compareTo(startSDate) < 0) {
          returnInt = -1;
        }
        if (d.compareTo(endEDate) > 0) {
          returnInt = 1;
        }
      } catch (ParseException e) {
        e.printStackTrace();
      }

    }
    return returnInt;
  }

  //得到调休日
  public List extractWorkdayOfYear() {
    List workDays = new ArrayList();
    Enumeration enumeration = businessCalendarProperties.keys();
    while (enumeration.hasMoreElements()) {
      String key = enumeration.nextElement().toString();
      if (key.toLowerCase().startsWith(BusinessCalendarConst.BUSINESS_DAY_EXPRESSED_WORKDAY)) {
        String workDay = businessCalendarProperties.getProperty(key);
        try {
          workDays.add(DateUtil.parseDate(workDay, businessCalendarProperties
            .getProperty(BusinessCalendarConst.DAY_FORMAT)));
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
    }

    return workDays;
  }

  public List holidayDateofYear() {
    List dateList = new ArrayList();
    Calendar cal = Calendar.getInstance();
    Enumeration enumeration = businessCalendarProperties.keys();
    while (enumeration.hasMoreElements()) {
      String key = enumeration.nextElement().toString();
      if (key.toLowerCase().startsWith(BusinessCalendarConst.HOLIDAY_KEY)) {
        String period = businessCalendarProperties.getProperty(key);
        String periodReg[] = period.split("&");
        try {
          if (periodReg.length == 1) {
            dateList.add(DateUtil.parseDate(periodReg[0].trim(), businessCalendarProperties
              .getProperty(BusinessCalendarConst.DAY_FORMAT)));
          } else {
            Date startDate = DateUtil.parseDate(periodReg[0].trim(), businessCalendarProperties
              .getProperty(BusinessCalendarConst.DAY_FORMAT));
            Date endDate = DateUtil.parseDate(periodReg[1].trim(), businessCalendarProperties
              .getProperty(BusinessCalendarConst.DAY_FORMAT));
            cal.setTime(startDate);
            while (cal.getTimeInMillis() <= endDate.getTime()) {
              dateList.add(cal.getTime());
              cal.add(Calendar.DAY_OF_YEAR, 1);
            }
          }
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
    }
    return dateList;
  }

  /**
   * 返回日历属性
   * 
   * @return
   */
  public Properties getBusinessCalendarProperties() {
    return businessCalendarProperties;
  }

  /**
   * 设置日历属性。该设置不是一个替换操作，而是一个覆盖操作，代码如下<br>
   * this.businessCalendarProperties.putAll(props);<br>
   * 即方法参数中提供的属性被合并到缺省定义中。如果参数中提供的属性和缺省属性同名，则缺省属性被覆盖，否则缺省属性被保留。
   * 
   * @param props
   */
  public void setBusinessCalendarProperties(Properties props) {
    this.businessCalendarProperties.putAll(props);
  }

  public Date getSysDate() {
    return new Date();
  }

  public static void main(String[] args) throws ParseException {
    DefaultCalendarService calendarService = new DefaultCalendarService();
    //    System.out.println(calendarService.dateAfter(new Date(), new Duration(1, Duration.MONTH)));
    //    System.out.println(calendarService.dateAfter(new Date(), new Duration(12, Duration.DAY)));
    //    System.out.println(calendarService.dateAfter(new Date(), new Duration(2, Duration.HOUR)));
    //    System.out.println(calendarService.dateAfter(new Date(), new Duration(3, Duration.MINUTE)));
    //    System.out.println(calendarService.dateAfter(new Date(), new Duration(3600 * 24 * 2, Duration.SECOND)));
    //
    //    System.out.println(calendarService.testTimeInTheTimeSpan(calendarService.dateAfter(new Date(), new Duration(3, Duration.HOUR)), calendarService.getBusinessTime(new Date())));
    //    System.out.println(calendarService.getBusinessTime(new Date()));//上班期间
    //    System.out.println(calendarService.isBusinessDay(DateUtil.parseDate("2009-10-01", "yyyy-MM-dd")));
    //    System.out.println(DateUtil.parseDate("2010-12-01 10:26:07", "yyyy-MM-dd HH:mm:ss"));
    System.out.println(DateUtil.parseHourTime("20101202202607"));
    System.out.println(calendarService.dateAfter(DateUtil.parseDate("20101202202607", "yyyyMMddHHmmss"),
      new Duration(5 * 60, Duration.MINUTE)));
  }
}
