package nerd.tuxmobil.fahrplan.congress.favorites

import android.content.Context
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView

import nerd.tuxmobil.fahrplan.congress.R
import nerd.tuxmobil.fahrplan.congress.base.LecturesAdapter
import nerd.tuxmobil.fahrplan.congress.extensions.textOrHide
import nerd.tuxmobil.fahrplan.congress.models.Lecture
import nerd.tuxmobil.fahrplan.congress.utils.DateHelper
import android.text.format.Time as Time1

class LectureArrayAdapter internal constructor(

        context: Context,
        list: List<Lecture>, numDays: Int

) : LecturesAdapter(

        context,
        R.layout.lecture_list_item,
        list,
        numDays

) {

    private val now: Time1 = Time1()

    @ColorInt
    private val pastEventTextColor = ContextCompat.getColor(context, R.color.favorites_past_event_text)

    override fun initViewSetup() {
        now.setToNow()
    }

    override fun setItemContent(position: Int, viewHolder: ViewHolder) {
        resetItemStyles(viewHolder)

        val lecture = getLecture(position)
        with(viewHolder) {
            if (lecture.tookPlace) {
                title.setPastEventTextColor()
                subtitle.setPastEventTextColor()
                speakers.setPastEventTextColor()
                lang.setPastEventTextColor()
                day.setPastEventTextColor()
                time.setPastEventTextColor()
                room.setPastEventTextColor()
                duration.setPastEventTextColor()
                withoutVideoRecording.setImageResource(R.drawable.ic_without_video_recording_took_place)
            }

            title.textOrHide = lecture.title
            subtitle.textOrHide = lecture.subtitle
            speakers.textOrHide = lecture.formattedSpeakers
            lang.textOrHide = lecture.lang
            lang.contentDescription = lecture.getLanguageContentDescription(context)
            day.visibility = View.GONE
            val timeText = DateHelper.getFormattedTime(lecture.dateUTC)
            time.textOrHide = timeText
            room.textOrHide = lecture.room
            val durationText = context.getString(R.string.event_duration, lecture.duration)
            duration.textOrHide = durationText
            video.visibility = View.GONE
            noVideo.visibility = View.GONE
            withoutVideoRecording.visibility = if (lecture.recordingOptOut) View.VISIBLE else View.GONE
        }
    }

    private val Lecture.tookPlace
        get() = dateUTC + duration * 60000 < now.toMillis(true)

    private fun TextView.setPastEventTextColor() = setTextColor(pastEventTextColor)

}
