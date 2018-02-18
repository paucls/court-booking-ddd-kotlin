package ddd.guild.courtbooking.domain.schedule

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import java.time.LocalDate
import java.time.LocalTime
import kotlin.test.assertFailsWith

private const val SCHEDULE_ID = "schedule-id"
private const val CLUB_ID = "club-id"
private const val COURT_ID = "court-id"
private const val BOOKING_ID = "booking-id"

class ScheduleTest : Spek({

    val day = LocalDate.of(2018, 1, 30)

    it("can create schedule for a day") {
        val schedule = Schedule(SCHEDULE_ID, CLUB_ID, COURT_ID, day)

        assertThat(schedule.id).isEqualTo(SCHEDULE_ID)
        assertThat(schedule.clubId).isEqualTo(CLUB_ID)
        assertThat(schedule.day).isEqualTo(day)
    }

    it("can allocate time for a court booking") {
        val schedule = Schedule(SCHEDULE_ID, CLUB_ID, COURT_ID, day)
        val startTime = LocalTime.of(10, 0)
        val endTime = LocalTime.of(10, 30)

        schedule.allocateTimeForBooking(BOOKING_ID, startTime, endTime)

        assertThat(schedule.entries.size).isOne()
        assertThat(schedule.entries[0].description).isEqualTo("Booked")
    }

    it("can not allocate an entry that overlaps in time with an existing one ") {
        val schedule = Schedule(SCHEDULE_ID, CLUB_ID, COURT_ID, day)
        schedule.allocateTimeForBooking(BOOKING_ID, LocalTime.of(10, 0), LocalTime.of(11, 0))

        assertFailsWith<ScheduleExceptions.TimeConflict> {
            schedule.allocateTimeForBooking(BOOKING_ID, LocalTime.of(10, 30), LocalTime.of(11, 0))
        }
    }

})