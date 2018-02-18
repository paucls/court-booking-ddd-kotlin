package ddd.guild.courtbooking.domain.schedule

import ddd.guild.courtbooking.domain.shared.DomainEntity
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import javax.persistence.*

/**
 * Aggregate Root
 */
@Entity
class Schedule(
        @Id
        val id: String,
        val clubId: String,
        val courtId: String,
        val day: LocalDate
) : DomainEntity {

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "schedule_id")
    val entries = mutableListOf<Entry>()

    fun allocateTimeForBooking(bookingId: String, startTime: LocalTime, endTime: LocalTime): TimeRange {
        val time = TimeRange(startTime, endTime)

        validateTimeDoNotConflict(time)

        entries.add(Entry(id = UUID.randomUUID().toString(), time = time, description = "Booked"))

        return time
    }

    private fun validateTimeDoNotConflict(time: TimeRange) {
        val overlaps = entries.any {
            it.time.overlapsWith(time)
        }
        if (overlaps) throw ScheduleExceptions.TimeConflict()
    }
}