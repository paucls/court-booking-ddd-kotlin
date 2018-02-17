package ddd.guild.courtbooking.domain.schedule

import ddd.guild.courtbooking.domain.shared.ValueObject
import java.time.Duration
import java.time.LocalTime
import javax.persistence.Embeddable
import javax.persistence.Transient

@Embeddable
data class BookingTime(
        val start: LocalTime,
        val end: LocalTime
) : ValueObject {

    @Transient
    private val minValidDuration = 30

    val duration: Long
        get() = Duration.between(start, end).toMinutes()

    init {
        if (start > end) throw ScheduleExceptions.InvalidTimeInterval()
        if (this.duration < minValidDuration) throw ScheduleExceptions.InvalidDuration()
    }

    fun overlapsWith(other: BookingTime): Boolean {
        return !(this.end <= other.start ||
                this.start >= other.end)
    }
}