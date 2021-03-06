package info.metadude.android.eventfahrplan.database.extensions

import android.support.test.runner.AndroidJUnit4
import info.metadude.android.eventfahrplan.database.contract.FahrplanContract.AlarmsTable.Columns.*
import info.metadude.android.eventfahrplan.database.models.Alarm
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlarmExtensionsTest {

    @Test
    fun toContentValues() {
        val alarm = Alarm(
                alarmTimeInMin = 20,
                day = 4,
                displayTime = 1509617700000L,
                eventId = "5237",
                time = 1509617700001L,
                timeText = "02/11/2017 11:05",
                title = "My title"
        )
        val values = alarm.toContentValues()
        assertThat(values.getAsInteger(ALARM_TIME_IN_MIN)).isEqualTo(20)
        assertThat(values.getAsInteger(DAY)).isEqualTo(4)
        assertThat(values.getAsLong(DISPLAY_TIME)).isEqualTo(1509617700000L)
        assertThat(values.getAsString(EVENT_ID)).isEqualTo("5237")
        assertThat(values.getAsLong(TIME)).isEqualTo(1509617700001L)
        assertThat(values.getAsString(TIME_TEXT)).isEqualTo("02/11/2017 11:05")
        assertThat(values.getAsString(EVENT_TITLE)).isEqualTo("My title")
    }

}
