package nerd.tuxmobil.fahrplan.congress.utils;

import android.support.annotation.NonNull;
import android.text.format.Time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import nerd.tuxmobil.fahrplan.congress.commons.Clock;
import nerd.tuxmobil.fahrplan.congress.commons.SystemClock;

public class DateHelper {

    /**
     * Returns a formatted string for the current date. Pattern YYYY-MM-DD.
     */
    public static String getCurrentDate() {
        return getFormattedDate(new SystemClock());
    }

    /**
     * Returns a formatted string for the given clock. Pattern YYYY-MM-DD.
     */
    public static String getFormattedDate(@NonNull Clock clock) {
        StringBuilder date = new StringBuilder();
        date.append(String.format("%d", clock.getYear()));
        date.append("-");
        date.append(String.format("%02d", clock.getMonth() + 1));
        date.append("-");
        date.append(String.format("%02d", clock.getMonthDay()));
        return date.toString();
    }

    public static String getFormattedTime(long time) {
        DateFormat dateFormat = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT);
        return dateFormat.format(new Date(time));
    }

    public static String getFormattedDate(long time) {
        DateFormat dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT);
        return dateFormat.format(new Date(time));
    }

    public static String getFormattedDateTime(long time) {
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance(
                SimpleDateFormat.FULL, SimpleDateFormat.SHORT, Locale.getDefault());
        return dateFormat.format(new Date(time));
    }

    public static int getMinutesOfDay(long dateUtc) {
        Time time = new Time();
        time.set(dateUtc);
        return time.hour * 60 + time.minute;
    }

    public static int getDayOfMonth(long dateUtc) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(dateUtc);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

}
