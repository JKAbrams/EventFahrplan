package nerd.tuxmobil.fahrplan.congress.commons

import android.text.format.Time

class SystemClock(initializeNow: Boolean = true) : Clock {

    private val time = Time()

    init {
        if (initializeNow) {
            time.setToNow()
        }
    }

    override fun getFormatted(pattern: String): String = time.format(pattern)

    override fun getYear() = time.year

    override fun getMonth() = time.month

    override fun getMonthDay() = time.monthDay

    override fun getHour() = time.hour

    override fun getMinute() = time.minute

    override fun setYear(year: Int) {
        time.year = year
    }

    override fun setMonth(month: Int) {
        time.month = month
    }

    override fun setMonthDay(monthDay: Int) {
        time.monthDay = monthDay
    }

    override fun setHour(hour: Int) {
        time.hour = hour
    }

    override fun setMinute(minute: Int) {
        time.minute = minute
    }

    override fun setMilliseconds(milliseconds: Long) {
        time.set(milliseconds)
    }

    override fun toMilliseconds() = time.toMillis(true)

    override fun minusHours(hours: Int) {
        time.hour -= hours
    }

    override fun minusMinutes(minutes: Int) {
        time.minute -= minutes
    }

    override fun normalize(): Long {
        return time.normalize(true)
    }

}
