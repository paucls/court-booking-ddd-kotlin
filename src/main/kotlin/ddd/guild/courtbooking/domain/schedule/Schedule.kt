package ddd.guild.courtbooking.domain.schedule

import ddd.guild.courtbooking.domain.DomainEntity
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

    fun addBooking(bookingId: String, memberId: String, startTime: LocalTime, endTime: LocalTime) {
        bookings.add(Booking(bookingId, memberId))
    }
}