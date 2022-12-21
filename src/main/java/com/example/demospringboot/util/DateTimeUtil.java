package com.example.demospringboot.util;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * <h2>简述</h2>
 * 		<ol>时间工具方法</ol>
 * <h2>功能描述</h2>
 * 		<ol>请添加功能详细的描述</ol>
 * <h2>修改历史</h2>
 *      <ol>如有修改，添加修改历史</ol>
 * </p>
 *
 * @author Sjun
 * @version 1.0
 * @Date 2018-7-10 上午10:46:22
 */
public class DateTimeUtil {

    private static final Logger LOGGER = Logger.getLogger(DateTimeUtil.class);

    private static final String SEPARATELine = "-";

    private static final String SEPARATEAT = "@";

    private static final String SEPARATEPOINT = ".";

    private static final String SEPARATECOLON = ":";

    private static final DateTimeUtil instance = new DateTimeUtil();

    public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static final String PATTERN_DATE = "yyyy-MM-dd";

    public static final String PATTERN_TIME = "HH:mm:ss";

    public static final String REGEX = "[0-9]{4}-[0-9]{2}-[0-9]{2}";

    public static final String REGEX_NEW = "[0-9]{4}[0-9]{2}[0-9]{2}";

    public static final DateTimeUtil getInstance() {
        return instance;
    }


    public static int getCurrentMonth(String dateStr) {
        String[] date = dateStr.split(SEPARATELine);
        return Integer.parseInt(date[1], 10);
    }

    public static String getCurMonth(String dateStr) {
        return dateStr.substring(4, 6);
    }

    public static String getMonth(String dateStr) {
        return dateStr.substring(5, 7);
    }

    public static String getCurDay(String dateStr) {
        return dateStr.substring(6);
    }

    public static String getCurYear(String dateStr) {
        return dateStr.substring(0, 4);
    }

    public static String getCurYM(String dateStr) {
        return dateStr.substring(0, 6);
    }

    public static String removeZero(String dateStr) {
        String rv = dateStr;
        if (rv.indexOf("0") == 0) {
            rv = rv.substring(1);
        }
        return rv;
    }

    public static int getMonthDays(int year, int month) {
        int days = 1;
        boolean isrn = ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
        switch (month) {
            case 1:
                days = 31;
                break;
            case 2:
                if (isrn) days = 29;
                else
                    days = 28;
                break;
            case 3:
                days = 31;
                break;
            case 4:
                days = 30;
                break;
            case 5:
                days = 31;
                break;
            case 6:
                days = 30;
                break;
            case 7:
                days = 31;
                break;
            case 8:
                days = 31;
                break;
            case 9:
                days = 30;
                break;
            case 10:
                days = 31;
                break;
            case 11:
                days = 30;
                break;
            case 12:
                days = 31;
        }
        return days;
    }

    public static int getMonthDays(String currDate, int year, int month) {
        int days = 1;
        String[] date = currDate.split(SEPARATELine);
        if ((Integer.parseInt(date[0]) == year) && (Integer.parseInt(date[1]) == month))
            days = Integer.parseInt(date[2]);
        else {
            days = getMonthDays(year, month);
        }
        return days;
    }

    public static int getCurrentYear(String dateStr) {
        String[] date = dateStr.split(SEPARATELine);
        return Integer.parseInt(date[0], 10);
    }

    public static int getCurrentDay(String dateStr) {
        String[] date = dateStr.split(SEPARATELine);
        return Integer.parseInt(date[2], 10);
    }

    public static int getJiDuDays(String dateStr) {
        int days = 0;
        String[] date = dateStr.split(SEPARATELine);
        int day = Integer.parseInt(date[2], 10);
        int yy = Integer.parseInt(date[0], 10);
        boolean isrn = ((yy % 4 == 0) && (yy % 100 != 0)) || (yy % 400 == 0);
        switch (Integer.parseInt(date[1], 10)) {
            case 1:
                days = day;
                break;
            case 2:
                days = 31 + day;
                break;
            case 3:
                if (isrn) days = 60 + day;
                else
                    days = 59 + day;
                break;
            case 4:
                days = day;
                break;
            case 5:
                days = 30 + day;
                break;
            case 6:
                days = 61 + day;
                break;
            case 7:
                days = day;
                break;
            case 8:
                days = 31 + day;
                break;
            case 9:
                days = 62 + day;
                break;
            case 10:
                days = day;
                break;
            case 11:
                days = 31 + day;
                break;
            case 12:
                days = 61 + day;
        }

        return days;
    }

    public static int getBetweenDays(String beginDate, String endDate) {
        int sum = 0;
        int beginYear = getCurrentYear(beginDate);
        int beginMonth = getCurrentMonth(beginDate);
        int beginDay = getCurrentDay(beginDate);
        int endYear = getCurrentYear(endDate);
        int endMonth = getCurrentMonth(endDate);
        int endDay = getCurrentDay(endDate);
        String startDateStr = bYearZero(beginYear) + bZero(beginMonth) + "01";

        int sumMonth = (endYear - beginYear + 1) * 12 - beginMonth - (12 - endMonth);
        for (int i = 0; i < sumMonth; i++) {
            String dateStr = getDateStr(startDateStr, i);
            sum += getMonthDays(getCurrentYear(dateStr), getCurrentMonth(dateStr));
        }

        sum = sum - beginDay + endDay;
        return sum;
    }

    public static String getDateStr(String dateStr, int hkm) {
        String reDateStr = "";
        int yy = Integer.parseInt(dateStr.substring(0, 4), 10);
        int mm = Integer.parseInt(dateStr.substring(4, 6), 10);
        int dd = Integer.parseInt(dateStr.substring(6, 8), 10);

        int yy2 = 0;
        int mm2 = 0;
        int dd2 = dd;
        if ((mm + hkm) % 12 == 0) {
            yy2 = yy + (mm + hkm) / 12 - 1;
            mm2 = 12;
        } else if ((mm + hkm) % 12 == 1) {
            yy2 = yy + (mm + hkm) / 12;
            mm2 = 1;
        } else {
            yy2 = yy + (mm + hkm) / 12;
            mm2 = (mm + hkm) % 12;
        }

        reDateStr = String.valueOf(yy2) + SEPARATELine + bZero(mm2) + SEPARATELine + bZero(dd2);
        return reDateStr;
    }

    public static String bZero(int sz) {
        return sz < 10 ? "0" + String.valueOf(sz) : String.valueOf(sz);
    }

    public static String bYearZero(int y) {
        if (y < 10) return "000" + String.valueOf(y);
        if (y < 100) return "00" + String.valueOf(y);
        if (y < 1000) return "0" + String.valueOf(y);

        return String.valueOf(y);
    }

    public static int compareDate(String date1, String date2) {
        int i = 0;
        String[] date1Array = date1.split(SEPARATELine);
        String[] date2Array = date2.split(SEPARATELine);
        java.sql.Date date11 = new java.sql.Date(Integer.parseInt(date1Array[0], 10), Integer.parseInt(date1Array[1], 10),
                Integer.parseInt(date1Array[2], 10));
        java.sql.Date date22 = new java.sql.Date(Integer.parseInt(date2Array[0], 10), Integer.parseInt(date2Array[1], 10),
                Integer.parseInt(date2Array[2], 10));
        return date11.compareTo(date22);
    }

    public static Date strToDate(String dateStr) {
        String[] dateArray = dateStr.split(SEPARATELine);
        Date date = new Date(Integer.parseInt(dateArray[0], 10) - 1900,
                Integer.parseInt(dateArray[1], 10) - 1, Integer.parseInt(dateArray[2], 10));
        return date;
    }

    public static String dateToStrByFormat(Date date, String dataFormat) {
        String str = "";
        try {
            if (date != null) {
                SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);
                str = sdf.format(date);
            }
        } catch (Exception ex) {
            str = "";
        }
        return str;
    }

    public static String dateToStr(Date date, String fgf) {
        String str = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + fgf + "MM" + fgf + "dd");
            str = sdf.format(date);
        } catch (Exception ex) {
            str = "";
        }
        return str;
    }

    /**
     * <p>
     * <h2>简述</h2>
     * 		<ol>字符串形式的时间转换为Date格式</ol>
     * <h2>功能描述</h2>
     * 		<ol></ol>
     * <h2>修改历史</h2>
     *      <ol>如有修改，添加修改历史</ol>
     * </p>
     *
     * @return
     * @Date 2019-5-14 上午10:57:51
     * @version 1.0
     */
    public static Date strToDateFormat(String str, String dataFormat) {
        Date date = null;
        try {
            if (StringUtils.hasText(str)) {
                SimpleDateFormat sdf = new SimpleDateFormat(dataFormat);
                date = sdf.parse(str);
            }
        } catch (Exception ex) {
        }
        return date;
    }

    public static String getCurDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(date);
        return strDate;
    }

    public static String getCurDate(String formatString) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        String strDate = sdf.format(date);
        return strDate;
    }

    public static String getCurTimeStamp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd@kk:mm:ss.S");
        String strStamp = sdf.format(date);

        return strStamp;
    }

    private static String getCurTimeStamp2() {
        Calendar rightNow = Calendar.getInstance();

        int DD = rightNow.get(5);

        int YYYY = rightNow.get(1);

        int hh = rightNow.get(11);

        int mm = rightNow.get(12);

        int ss = rightNow.get(13);

        int ms = rightNow.get(14);

        String strTimeStamp = null;
        String strYYYY = null;
        String strMM = null;
        String strDD = null;
        String strhh = null;
        String strmm = null;
        String strss = null;
        String strms = null;

        if (YYYY < 10)
            strYYYY = "000" + String.valueOf(YYYY);
        else if ((YYYY < 100) && (YYYY >= 10))
            strYYYY = "00" + String.valueOf(YYYY);
        else if ((YYYY < 1000) && (YYYY >= 100))
            strYYYY = "0" + String.valueOf(YYYY);
        else if ((YYYY < 10000) && (YYYY >= 1000)) {
            strYYYY = String.valueOf(YYYY);
        }

        if (String.valueOf(DD).length() == 1)
            strDD = "0" + String.valueOf(DD);
        else {
            strDD = String.valueOf(DD);
        }

        if (String.valueOf(hh).length() == 1)
            strhh = "0" + String.valueOf(hh);
        else {
            strhh = String.valueOf(hh);
        }

        if (String.valueOf(mm).length() == 1)
            strmm = "0" + String.valueOf(mm);
        else {
            strmm = String.valueOf(mm);
        }

        if (String.valueOf(ss).length() == 1)
            strss = "0" + String.valueOf(ss);
        else {
            strss = String.valueOf(ss);
        }

        if (ms < 10)
            strms = "00" + String.valueOf(ms);
        else if ((ms < 100) && (ms >= 10))
            strms = "0" + String.valueOf(ms);
        else if ((ms < 1000) && (ms >= 100)) {
            strms = String.valueOf(ms);
        }

        strTimeStamp = strYYYY + SEPARATELine + strMM + SEPARATELine + SEPARATELine + strDD;
        strTimeStamp = strTimeStamp + SEPARATEAT;
        strTimeStamp = strTimeStamp + strhh + SEPARATECOLON + strmm + SEPARATECOLON + strss;
        strTimeStamp = strTimeStamp + SEPARATEPOINT + strms;
        return strTimeStamp;
    }

    public static String getLastDateOfQuarter(String date) {
        String year = date.substring(0, 4);
        String month = date.substring(5, 7);
        String day = date.substring(7);
        String quarter = "";

        switch (Integer.parseInt(month)) {
            case 1:
            case 2:
            case 3:
                quarter = "03";
                break;
            case 4:
            case 5:
            case 6:
                quarter = "06";
                break;
            case 7:
            case 8:
            case 9:
                quarter = "09";
                break;
            case 10:
            case 11:
            case 12:
                quarter = "12";
        }

        return year + quarter + day;
    }

    public static String getLastDateOfYear(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        String day = date.substring(8);

        return year + "12" + day;
    }

    public static boolean checkDate1BeforeDate2(String date1, String date2) {
        Date d1 = strToDate(date1);
        Date d2 = strToDate(date2);
        return d1.before(d2);
    }

    public static String getQuarter(String str) {
        String rv = "";
        switch (Integer.parseInt(str)) {
            case 1:
            case 2:
            case 3:
                rv = "1";
                break;
            case 4:
            case 5:
            case 6:
                rv = "2";
                break;
            case 7:
            case 8:
            case 9:
                rv = "3";
                break;
            case 10:
            case 11:
            case 12:
                rv = "4";
        }

        return rv;
    }

    public static String getHelfYear(String str) {
        String rv = "";
        switch (Integer.parseInt(str)) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                rv = "1";
                break;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                rv = "2";
        }

        return rv;
    }

    private static String ADD_DATE(int optype, String date, int num) {
        String st_return = "";
        try {
            DateFormat daf_date = DateFormat.getDateInstance(2, Locale.CHINA);
            daf_date.parse(date);
            Calendar calendar = daf_date.getCalendar();
            calendar.add(optype, num);
            if (optype == 2) {
                calendar.add(5, -1);
            }
            String st_m = "";
            String st_d = "";
            int y = calendar.get(1);
            int m = calendar.get(2) + 1;
            int d = calendar.get(5);
            if (m <= 9)
                st_m = "0" + m;
            else {
                st_m = "" + m;
            }
            if (d <= 9)
                st_d = "0" + d;
            else {
                st_d = "" + d;
            }
            st_return = y + SEPARATELine + st_m + SEPARATELine + st_d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return st_return;
    }

    private static String ADD_DATE2(int optype, String date, int num) {
        String st_return = "";
        try {
            DateFormat daf_date = DateFormat.getDateInstance(2, Locale.CHINA);
            daf_date.parse(date);
            Calendar calendar = daf_date.getCalendar();
            calendar.add(optype, num);
            if (optype == 2) {
                calendar.add(5, 0);
            }
            String st_m = "";
            String st_d = "";
            int y = calendar.get(1);
            int m = calendar.get(2) + 1;
            int d = calendar.get(5);
            if (m <= 9)
                st_m = "0" + m;
            else {
                st_m = "" + m;
            }
            if (d <= 9)
                st_d = "0" + d;
            else {
                st_d = "" + d;
            }
            st_return = y + SEPARATELine + st_m + SEPARATELine + st_d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return st_return;
    }

    public static String ADD_DAY(String date, int n) {
        return ADD_DATE(5, date, n);
    }

    public static String ADD_MONTH(String date, int n) {
        return ADD_DATE(2, date, n);
    }

    public static String ADD_MONTH2(String date, int n) {
        return ADD_DATE2(2, date, n);
    }

    public static String ADD_YEAR(String date, int n) {
        return ADD_DATE(1, date, n);
    }

    public static String getPeryyMMdd(String yyMMdd, String termType) {
        String rv = yyMMdd;

        Calendar calendar = Calendar.getInstance();

        String m = getCurMonth(yyMMdd);
        String y = getCurYear(yyMMdd);
        String d = getCurDay(yyMMdd);

        int year = Integer.parseInt(y);
        int month = Integer.parseInt(removeZero(m));
        int date = Integer.parseInt(removeZero(d));

        calendar.set(year, month, date);

        switch (Integer.parseInt(termType)) {
            case 1:
                calendar.add(2, -2);
                break;
            case 2:
                calendar.add(2, -4);
                break;
            case 3:
                calendar.add(2, -7);
                break;
            case 4:
                calendar.add(2, -13);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        rv = sdf.format(calendar.getTime());
        return rv;
    }

    public static String[] getPerNyyMMdd(String yyMMdd, String termType, int term) {
        String[] rv = new String[term];

        Calendar calendar = Calendar.getInstance();

        String m = getCurMonth(yyMMdd);
        String y = getCurYear(yyMMdd);
        String d = getCurDay(yyMMdd);

        int year = Integer.parseInt(y);
        int month = Integer.parseInt(removeZero(m));
        int date = Integer.parseInt(removeZero(d));

        rv[0] = yyMMdd;
        for (int i = 0; i < term - 1; i++) {
            calendar.set(year, month, date);
            switch (Integer.parseInt(termType)) {
                case 1:
                    calendar.add(2, -(2 + i));
                    break;
                case 2:
                    calendar.add(2, -(4 + i * 3));
                    break;
                case 3:
                    calendar.add(2, -(7 + i * 6));
                    break;
                case 4:
                    calendar.add(2, -(13 + i * 12));
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            rv[(i + 1)] = sdf.format(calendar.getTime());
        }

        return rv;
    }

    public static int countMonth(String yyMMdd1, String yyMMdd2) {
        String startDate = yyMMdd1;
        String endDate = yyMMdd2;

        int rv = 0;

        if (Long.parseLong(startDate) > Long.parseLong(endDate)) {
            String tmp = endDate;
            endDate = startDate;
            startDate = tmp;
        }

        int yy1 = Integer.parseInt(startDate.substring(0, 4));
        int mm1 = Integer.parseInt(startDate.substring(4, 6));
        int dd1 = Integer.parseInt(startDate.substring(6));

        int yy2 = Integer.parseInt(endDate.substring(0, 4));
        int mm2 = Integer.parseInt(endDate.substring(4, 6));
        int dd2 = Integer.parseInt(endDate.substring(6));

        rv = (yy2 - yy1) * 12 + (mm2 - mm1);

        return rv;
    }

    public static String getRandomNum(int length) {
        String rt = "";
        for (int i = 0; i < length; i++) {
            Random r = new Random();
            rt = rt + String.valueOf(r.nextInt(9));
        }

        return rt;
    }

    public static String getCharacterAndNumber(int length) {
        String rt = "";

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";

            if ("char".equalsIgnoreCase(charOrNum)) {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                rt = rt + (char) (choice + random.nextInt(26));
            } else {
                if (!"num".equalsIgnoreCase(charOrNum))
                    continue;
                rt = rt + String.valueOf(random.nextInt(10));
            }
        }

        return rt;
    }

    public static String getPK() {
        String rt = "";
        rt = rt + getCurTimeStamp4PK();
        rt = rt + getCharacterAndNumber(15);
        return rt;
    }

    public static String getCurTimeStamp4PK() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddkkmmssSSS");
        String strStamp = sdf.format(date);

        return strStamp;
    }

    public static String getYesterday(String date) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            Date d = sf.parse(date);
            cal.setTime(d);
            cal.set(5, cal.get(5) - 1);
            date = sf.format(cal.getTime());
        } catch (Exception localException) {
        }
        return date;
    }

    public static String getDateTime(String pattern) {
        String dateTime = "";
        Calendar calender = Calendar.getInstance();
        if ((pattern == null) || (pattern.equals(""))) pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        dateTime = sf.format(calender.getTime());
        return dateTime;
    }

    public static int getBetweenYears(String startDate, String endDate) {
        int yearNum = 0;
        int startYear = getCurrentYear(startDate);
        int endYear = getCurrentYear(endDate);

        int startMonth = getCurrentMonth(startDate);
        int endMonth = getCurrentMonth(endDate);

        int startDay = getCurrentDay(startDate);
        int endDay = getCurrentDay(endDate);

        yearNum = endYear - startYear;

        if (yearNum > 0) {
            if (endMonth < startMonth)
                yearNum--;
            else if ((endMonth == startMonth) &&
                    (endDay < startDay)) {
                yearNum--;
            }
        }

        return yearNum;
    }

    public static int getBetweenYearsByYM(String startDate, int year, int month) {
        int yearNum = 0;

        int startYear = getCurrentYear(startDate);

        int startMonth = getCurrentMonth(startDate);

        yearNum = year - startYear;

        if ((yearNum > 0) &&
                (month < startMonth)) {
            yearNum--;
        }

        return yearNum;
    }

    public static String getDateDiff(long times) {
        long oneDay = 86400000L;
        long oneHou = 3600000L;
        long oneMin = 60000L;

        long day = times / oneDay;
        long hours = times % oneDay / oneHou;
        long mint = times % oneDay % oneHou / oneMin;
        String outStr;
        if (day > 0L) {
            outStr = day + "天" + hours + "小时" + mint + "分钟";
        } else {
            if (hours > 0L) {
                outStr = hours + "小时" + mint + "分钟";
            } else {
                if (mint > 0L) {
                    outStr = mint + "分钟";
                } else
                    outStr = "0分钟";
            }
        }
        return outStr;
    }

    public static String getDateOfCountMonth(String date, int count) {
        Calendar cal = getCalendarObject(date);

        cal.add(2, count);

        return new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(cal.getTime());
    }

    public static Calendar getCalendarObject(String date) {
        StringTokenizer str = new StringTokenizer(date, SEPARATELine);

        int year = Integer.parseInt(str.nextToken());
        int month = Integer.parseInt(str.nextToken());
        int day = Integer.parseInt(str.nextToken());

        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(2, month - 1);
        cal.set(5, day);

        return cal;
    }

    public static String formatStrDt(String dateStr) {
        String str = "";
        try {
            String year = dateStr.substring(0, 4);
            String month = dateStr.substring(5, 7);
            String day = dateStr.substring(8, 10);
            str = year + month + day;
        } catch (Exception ex) {
            str = "";
        }
        return str;
    }

    public static int getMonthSpace(String date1, String date2) {
        int result = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();

            c1.setTime(sdf.parse(date1));
            c2.setTime(sdf.parse(date2));

            result = c2.get(2) - c1.get(2);
        } catch (Exception localException) {
        }
        return result == 0 ? 1 : Math.abs(result);
    }

    public static String getLastDayOfMonth(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String month = date.substring(5, 7);
        String year = date.substring(0, 4);

        int days = 1;
        boolean isrn = ((Integer.parseInt(year) % 4 == 0) && (Integer.parseInt(year) % 100 != 0)) || (Integer.parseInt(year) % 400 == 0);

        switch (Integer.parseInt(month)) {
            case 1:
                days = 31;
                break;
            case 2:
                if (isrn) days = 29;
                else
                    days = 28;
                break;
            case 3:
                days = 31;
                break;
            case 4:
                days = 30;
                break;
            case 5:
                days = 31;
                break;
            case 6:
                days = 30;
                break;
            case 7:
                days = 31;
                break;
            case 8:
                days = 31;
                break;
            case 9:
                days = 30;
                break;
            case 10:
                days = 31;
                break;
            case 11:
                days = 30;
                break;
            case 12:
                days = 31;
        }
        String last_date = date.substring(0, 7) + SEPARATELine + days;
        return last_date;
    }

    public static String getFirthDayOfMonth(String date) {
        String s = date.substring(0, 7) + "-01";
        return s;
    }


    /**
     * <p>
     * <h2>简述</h2>
     * 		<ol>是否在时间段内</ol>
     * <h2>功能描述</h2>
     * 		<ol>请添加功能详细的描述</ol>
     * <h2>修改历史</h2>
     *      <ol>如有修改，添加修改历史</ol>
     * </p>
     *
     * @param nowTime
     * @param startTime
     * @param endTime
     * @return
     * @author 75291
     * @Date 2018-6-27 上午11:53:55
     * @version 1.0
     */
    public static boolean WithinTime(Date nowTime, Date startTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * <p>
     * <h2>简述</h2>
     * 		<ol>判断前面的时间是否大于后面的时间</ol>
     * <h2>功能描述</h2>
     * 		<ol>请添加功能详细的描述</ol>
     * <h2>修改历史</h2>
     *      <ol>如有修改，添加修改历史</ol>
     * </p>
     *
     * @param startTime
     * @param endTime
     * @return
     * @author 75291
     * @Date 2018-7-6 下午7:09:56
     * @version 1.0
     */
    public static boolean WithTimeOut(Date startTime, Date endTime) {
        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (end.after(begin)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * <p>
     * <h2>简述</h2>
     * 		<ol>计算两个日期相隔的毫秒数</ol>
     * <h2>功能描述</h2>
     * 		<ol>请添加功能详细的描述</ol>
     * <h2>修改历史</h2>
     *      <ol>如有修改，添加修改历史</ol>
     * </p>
     *
     * @param date1 ,被减数
     * @param date2 ， 减数
     * @return
     * @author 75291
     * @Date 2018-7-13 下午4:09:06
     * @version 1.0
     */
    public static BigDecimal compareTimes(String date1, String date2) {
        BigDecimal result = BigDecimal.ZERO;
        try {
            SimpleDateFormat sfm = new SimpleDateFormat(PATTERN_DATE_TIME);
            Date time = sfm.parse(date1);
            Date startDate_2 = sfm.parse(date2);
            long times = time.getTime() - startDate_2.getTime();
            result = BigDecimal.valueOf(times);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * <p>
     * <h2>简述</h2>
     * 		<ol>根据输入的日期生成当前时间</ol>
     * <h2>功能描述</h2>
     * 		<ol>输入的日期要求格式为yyyy-MM-dd，输出时间格式为yyyy-MM-dd HH:mm:ss</ol>
     * <h2>修改历史</h2>
     *      <ol>如有修改，添加修改历史</ol>
     * </p>
     *
     * @param openDay
     * @return
     * @author Sjun
     * @Date 2018-8-14 上午10:57:51
     * @version 1.0
     */
    public static String getCurrentDateTime(String openDay) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String sCtrlDate = openDay + " " + df.format(date);
        return sCtrlDate;
    }

    /**
     * 根据出生日期算年龄
     */
    public static int getAgeByBirth(String birthday, String dataFormat) {
        int age = 0;
        try {
            Date birthdayDate = strToDateFormat(birthday, dataFormat);
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(birthdayDate);

            if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 检查日期格式是否为yyyy-MM-dd,格式错误，返回空
     *
     * @param date
     * @return
     */
    public static String checkDateFormat(String date) {
        if (StringUtils.hasText(date)) {
            Pattern pattern = Pattern.compile(REGEX);
            Matcher m = pattern.matcher(date);
            boolean dateFlag = m.matches();
            if (!dateFlag) {
                return "";
            }
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setLenient(false);
            try {
                Date dateformat = formatter.parse(date);
            } catch (Exception e) {
                return "";
            }
        }
        return date;
    }

    /**
     * 日期格式yyyyMMdd 转换为 yyyy-MM-dd
     * 格式错误，返回空
     * 格式 yyyy-MM-dd 直接返回
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static String changeDateFormat(String date) {
        if (StringUtils.hasText(checkDateFormat(date))) {
            return date;
        }
        if (StringUtils.hasText(date)) {
            Pattern pattern = Pattern.compile(REGEX_NEW);
            Matcher m = pattern.matcher(date);
            boolean dateFlag = m.matches();
            if (!dateFlag) {
                LOGGER.error("[changeDateFormat]  日期格式转换错误");
                return "";
            }
            DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            formatter.setLenient(false);
            try {
                Date dateformat = formatter.parse(date);
                date = date.subSequence(0, 4) + "-" + date.subSequence(4, 6) + "-" + date.subSequence(6, 8);
            } catch (Exception e) {
                LOGGER.error("[changeDateFormat]  日期格式转换错误：" + e);
                return "";
            }
        }
        return date;
    }

    public static void main(String[] args) {
        int betweenDays = DateTimeUtil.getBetweenDays("2022-09-08", "2022-11-16");
        System.out.println(betweenDays);
    }
}