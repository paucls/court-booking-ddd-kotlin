package ddd.guild.courtbooking.domain.schedule

import ddd.guild.courtbooking.domain.ValueObject
import java.time.Duration
import java.time.LocalTime

class TimeSlot(
        val startTime: LocalTime,
        val endTime: LocalTime
) : ValueObject {

    private val minValidDuration = 40

    val duration: Long
        get() = Duration.between(startTime, endTime).toMinutes()

    init {
        if (startTime > endTime) throw ScheduleExceptions.InvalidTimeInterval()
        if (this.duration < minValidDuration) throw ScheduleExceptions.InvalidDuration()
    }

    fun overlapsWith(other: TimeSlot): Boolean {
        return !(this.endTime <= other.startTime ||
                this.startTime >= other.endTime)
    }
}