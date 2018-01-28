package ddd.guild.courtbooking.domain.schedule

import ddd.guild.courtbooking.domain.shared.DomainEntity
import java.time.LocalDate
import java.time.LocalTime

/**
 * Aggregate Root
 */
class Schedule(
        val id: String,
        val locationId: String,
        val day: LocalDate
) : DomainEntity {
    val bookings = mutableListOf<Booking>()

    fun addBooking(bookingId: String, memberId: String, courtId: String, startTime: LocalTime, endTime: LocalTime) {
        val timeSlot = TimeSlot(startTime, endTime)

        validateTimeDoNotConflict(timeSlot)

        bookings.add(Booking(bookingId, memberId, courtId, day, timeSlot))
    }

    private fun validateTimeDoNotConflict(timeSlot: TimeSlot) {
        val overlaps = bookings.any {
            it.timeSlot.overlapsWith(timeSlot)
        }
        if (overlaps) throw ScheduleExceptions.BookingTimeConflict()
    }

    fun confirmBooking(bookingId: String, memberId: String) {
        bookings
                .find { it.id == bookingId }
                ?.confirm(memberId)
    }
}