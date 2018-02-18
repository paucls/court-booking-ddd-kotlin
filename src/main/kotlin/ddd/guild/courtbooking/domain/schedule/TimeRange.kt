package ddd.guild.courtbooking.domain.schedule

import ddd.guild.courtbooking.domain.shared.ValueObject
import java.time.Duration
import java.time.LocalTime
import javax.persistence.Embeddable

@Embeddable
data class TimeRange(val start: LocalTime, val end: LocalTime) : ValueObject {

    val durationInMinutes: Long
        get() = Duration.between(start, end).toMinutes()

    init {
        if (start > end) throw ScheduleExceptions.InvalidTimeInterval()
    }

    fun overlapsWith(other: TimeRange): Boolean {
        return !(this.end <= other.start ||
                this.start >= other.end)
    }
}