package ddd.guild.courtbooking.domain.schedule

import ddd.guild.courtbooking.domain.booking.Booking
import ddd.guild.courtbooking.domain.booking.BookingTime
import ddd.guild.courtbooking.domain.shared.DomainEntity
import java.time.LocalDate
import java.time.LocalTime

/**
 * Aggregate Root
 */
class Schedule(
        val id: String,
        val clubId: String,
        val day: LocalDate
) : DomainEntity {
    val bookings = mutableListOf<Booking>()

    fun addBooking(bookingId: String, memberId: String, courtId: String, startTime: LocalTime, endTime: LocalTime) {
        val bookingTime = BookingTime(startTime, endTime)

        validateTimeDoNotConflict(bookingTime)

        bookings.add(Booking(bookingId, memberId, courtId, day, bookingTime))
    }

    private fun validateTimeDoNotConflict(time: BookingTime) {
        val overlaps = bookings.any {
            it.time.overlapsWith(time)
        }
        if (overlaps) throw ScheduleExceptions.BookingTimeConflict()
    }
}