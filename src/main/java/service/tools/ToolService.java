package service.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ZH on 2015/5/21.
 */
public class ToolService {
    /**
     * 判断data1是否大于data2，默认按照yyyy-MM-dd格式比较
     *
     * @param date1
     * @param date2
     * @return <li>data1 大于 data2 返回1</li> <li>data1==data2 返回0</li><li>
     *         data1 小于 data2 返回-1</li>
     */
    public static int compare(String date1, String date2) {
        return compare(date1, date2, "yyyy-MM-dd");
    }

    public static int compare(String date1, String date2, String format) {
        Date d1 = parseDateTime(date1, format);
        Date d2 = parseDateTime(date2, format);
        return d1.compareTo(d2);
    }

    public static Date parseDateTime(String dateTime, String format) {
        try {
            SimpleDateFormat t = new SimpleDateFormat(format);
            return t.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 直接获取当前日期的"yyyy-MM-dd HH:mm:ss" 格式
    public static String getCrtDateYYYYMMDDHHmmss() {
        return getCurrentDateString("yyyy-MM-dd HH:mm:ss");
    }

    // 直接获取当前日期的"yyyy-MM-dd" 格式
    public static String getCrtDateYYYYMMDD() {
        return getCurrentDateString("yyyy-MM-dd");
    }
    public static synchronized String getCurrentDateString(String dateFormat) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setTimeZone(TimeZone.getDefault());

        return sdf.format(cal.getTime());
    }
    /**
     * 根据日期获得所在周的日期
     * @param mdate
     * @return
     */
    public static List<Date> dateToWeek(Date mdate) {
        int b = mdate.getDay();
        Date fdate;
        List<Date> list = new ArrayList<Date>();
        Long fTime = mdate.getTime() - b * 24 * 3600;
        for (int a = 1; a <= 7; a++) {
            fdate = new Date();
            fdate.setTime(fTime + (a * 24 * 3600000));
            list.add(a - 1, fdate);
        }
        return list;
    }
}
