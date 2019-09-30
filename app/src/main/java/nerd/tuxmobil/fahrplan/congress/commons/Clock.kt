package nerd.tuxmobil.fahrplan.congress.commons

interface Clock {

    fun getFormatted(pattern: String): String

    fun getYear(): Int

    fun getMonth(): Int

    fun getMonthDay(): Int

    fun getHour(): Int

    fun getMinute(): Int

    fun setYear(year: Int)

    fun setMonth(month: Int)

    fun setMonthDay(monthDay: Int)

    fun setHour(hour: Int)

    fun setMinute(minute: Int)

    fun setMilliseconds(milliseconds : Long)

    fun toMilliseconds(): Long

    fun minusHours(hours: Int)

    fun minusMinutes(minutes: Int)

    fun normalize(): Long

}
