package com.tnecesoc.MyInfoDemo.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Tnecesoc on 2016/12/24.
 */
public class FlexDateFormat {

    private SimpleDateFormat sameDayFormat, withinAWeekFormat, longAgoFormat;

    public Date firstDayInWeek, yesterday, today;

    public FlexDateFormat() {
        sameDayFormat = new SimpleDateFormat("H:mm", Locale.getDefault());
        withinAWeekFormat = new SimpleDateFormat("E", Locale.getDefault());
        longAgoFormat = new SimpleDateFormat("y/M/d", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        int day = calendar.get(Calendar.DAY_OF_WEEK);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        firstDayInWeek = calendar.getTime();

        calendar.set(Calendar.DAY_OF_WEEK, day - 1);
        yesterday = calendar.getTime();

        calendar.add(Calendar.DATE, 1);
        today = calendar.getTime();
    }

    public String format(Date date) {

        if (date.after(today)) {
            return sameDayFormat.format(date);
        } else if (date.after(yesterday)) {
            return "昨天";
        } else if (date.after(firstDayInWeek)) {
            return withinAWeekFormat.format(date);
        } else {
            return longAgoFormat.format(date);
        }
    }
}
