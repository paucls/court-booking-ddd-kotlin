package ddd.guild.courtbooking.application.booking

import ddd.guild.courtbooking.application.schedule.ScheduleRepository
import ddd.guild.courtbooking.application.schedule.nextIdentity
import ddd.guild.courtbooking.domain.booking.Booking
import ddd.guild.courtbooking.domain.schedule.Schedule
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class BookingService(val bookingRepository: BookingRepository, val scheduleRepository: ScheduleRepository) {

    fun makeBooking(c: BookingCommands.MakeBooking): Booking {
        val bookingId = bookingRepository.nextIdentity()

        val schedule = getScheduleForDay(c.clubId, c.courtId, c.day)
        val time = schedule.allocateTimeForBooking(bookingId, c.startTime, c.endTime)

        val booking = Booking(bookingId, c.memberId, c.courtId, c.day, time)

        scheduleRepository.save(schedule)
        bookingRepository.save(booking)

        return booking
    }

    fun getScheduleForDay(clubId: String, courtId: String, day: LocalDate): Schedule {
        val schedule = scheduleRepository.findByClubIdAndCourtIdAndDay(clubId, courtId, day)
        return if (schedule.isPresent) {
            schedule.get()
        } else {
            Schedule(scheduleRepository.nextIdentity(), clubId, courtId, day)
        }
    }

    fun cancelBooking(c: BookingCommands.CancelBooking) {
        val booking = bookingRepository.findById(c.bookingId).get()

        booking.cancel(c.userId)

        bookingRepository.save(booking)
    }

    fun confirmBooking(c: BookingCommands.ConfirmBooking) {
        val booking = bookingRepository.findById(c.bookingId).get()

        booking.confirm(c.userId)

        bookingRepository.save(booking)
    }
}