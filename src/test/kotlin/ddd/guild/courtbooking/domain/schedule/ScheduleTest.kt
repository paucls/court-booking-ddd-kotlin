package ddd.guild.courtbooking.domain.schedule

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import java.time.LocalDate
import java.time.LocalTime
import kotlin.test.assertFailsWith

private const val SCHEDULE_ID = "schedule-id"
private const val LOCATION_ID = "location-id"
private const val BOOKING_ID = "booking-id"
private const val MEMBER_ID = "member-id"
private const val COURT_ID = "court-id"

class ScheduleTest : Spek({

    val day = LocalDate.of(2018, 1, 30)

    it("can create schedule for a day") {
        val schedule = Schedule(SCHEDULE_ID, LOCATION_ID, day)

        assertThat(schedule.id).isEqualTo(SCHEDULE_ID)
        assertThat(schedule.locationId).isEqualTo(LOCATION_ID)
        assertThat(schedule.day).isEqualTo(day)
    }

    it("can add new booking") {
        val schedule = Schedule(SCHEDULE_ID, LOCATION_ID, day)
        val startTime = LocalTime.of(10, 0)
        val endTime = LocalTime.of(10, 40)

        schedule.addBooking(BOOKING_ID, MEMBER_ID, COURT_ID, startTime, endTime)

        assertThat(schedule.bookings.size).isOne()
    }

    it("can confirm booking") {
        val schedule = Schedule(SCHEDULE_ID, LOCATION_ID, day)
        schedule.addBooking(BOOKING_ID, MEMBER_ID, COURT_ID, LocalTime.of(10, 0), LocalTime.of(10, 40))

        schedule.confirmBooking(BOOKING_ID, MEMBER_ID)

        assertThat(schedule.bookings.first().status).isEqualTo(Booking.Status.CONFIRMED)
    }

    it("can not confirm booking of another member") {
        val schedule = Schedule(SCHEDULE_ID, LOCATION_ID, day)
        schedule.addBooking(BOOKING_ID, "another-member-id", COURT_ID, LocalTime.of(10, 0), LocalTime.of(10, 40))

        assertFailsWith<ScheduleExceptions.BookingBelongsToAnotherMember> {
            schedule.confirmBooking(BOOKING_ID, MEMBER_ID)
        }
    }

    it("can not add a booking which duration is less than 40 minutes") {
        val schedule = Schedule(SCHEDULE_ID, LOCATION_ID, day)

        assertFailsWith<ScheduleExceptions.InvalidDuration> {
            schedule.addBooking(BOOKING_ID, MEMBER_ID, COURT_ID, LocalTime.of(10, 0), LocalTime.of(10, 39))
        }
    }

    it("can not add a new booking that overlaps in time with an existing one") {
        val schedule = Schedule(SCHEDULE_ID, LOCATION_ID, day)
        schedule.addBooking(BOOKING_ID, MEMBER_ID, COURT_ID, LocalTime.of(10, 0), LocalTime.of(11, 20))

        assertFailsWith<ScheduleExceptions.BookingTimeConflict> {
            schedule.addBooking(BOOKING_ID, MEMBER_ID, COURT_ID, LocalTime.of(10, 40), LocalTime.of(11, 20))
        }
    }

})