package ddd.guild.courtbooking.domain.schedule

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.LocalTime
import kotlin.test.assertFailsWith

private const val SCHEDULE_ID = "schedule-id"
private const val LOCATION_ID = "location-id"
private const val BOOKING_ID = "booking-id"
private const val MEMBER_ID = "member-id"
private const val COURT_ID = "court-id"

@RunWith(JUnitPlatform::class)
class ScheduleTest : Spek({

    it("can create schedule for a day") {
        val today = LocalDate.now()
        val schedule = Schedule(SCHEDULE_ID, LOCATION_ID, today)

        assertThat(schedule.id).isEqualTo(SCHEDULE_ID)
        assertThat(schedule.locationId).isEqualTo(LOCATION_ID)
        assertThat(schedule.day).isEqualTo(today)
    }

    it("can add new booking") {
        val schedule = Schedule(SCHEDULE_ID, LOCATION_ID, LocalDate.now())
        val startTime = LocalTime.of(10, 0)
        val endTime = LocalTime.of(10, 30)

        schedule.addBooking(BOOKING_ID, MEMBER_ID, COURT_ID, startTime, endTime)

        assertThat(schedule.bookings.size).isOne()
        assertThat(schedule.bookings.first().memberId).isEqualTo(MEMBER_ID)
        assertThat(schedule.bookings.first().courtId).isEqualTo(COURT_ID)
        assertThat(schedule.bookings.first().isConfirmed).isFalse()
    }

    it("can confirm booking") {
        val schedule = Schedule(SCHEDULE_ID, LOCATION_ID, LocalDate.now())
        schedule.addBooking(BOOKING_ID, MEMBER_ID, COURT_ID, LocalTime.of(10, 0), LocalTime.of(10, 30))

        schedule.confirmBooking(BOOKING_ID, MEMBER_ID)

        assertThat(schedule.bookings.first().isConfirmed).isTrue()
    }

    it("can not confirm booking of another member") {
        val schedule = Schedule(SCHEDULE_ID, LOCATION_ID, LocalDate.now())
        schedule.addBooking(BOOKING_ID, "another-member-id", COURT_ID, LocalTime.of(10, 0), LocalTime.of(10, 30))

        assertFailsWith<ScheduleExceptions.BookingBelongsToAnotherMember> {
            schedule.confirmBooking(BOOKING_ID, MEMBER_ID)
        }
    }

})