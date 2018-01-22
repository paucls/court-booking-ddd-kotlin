package ddd.guild.courtbooking.domain.schedule

import ddd.guild.courtbooking.domain.ValueObject
import java.time.Duration
import java.time.LocalTime

class TimeSlot(
        val startTime: LocalTime,
        val endTime: LocalTime
) : ValueObject {

    private val minValidDuration = 30

    val duration: Long
        get() = Duration.between(startTime, endTime).toMinutes()

    init {
        if (startTime > endTime) throw ScheduleExceptions.InvalidTimeInterval()
        if (this.duration < minValidDuration) throw ScheduleExceptions.InvalidDuration()
    }
}