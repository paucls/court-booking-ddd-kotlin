package ddd.guild.courtbooking.domain.schedule

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.LocalTime

private const val SCHEDULE_ID = "schedule-id"
private const val LOCATION_ID = "location-id"
private const val BOOKING_ID = "booking-id"
private const val MEMBER_ID = "member-id"

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

        schedule.addBooking(BOOKING_ID, MEMBER_ID, startTime, endTime)

        assertThat(schedule.bookings.size).isOne()
    }

})