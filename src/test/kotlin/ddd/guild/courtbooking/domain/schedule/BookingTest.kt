package ddd.guild.courtbooking.domain.schedule

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.time.LocalTime
import kotlin.test.assertFailsWith

private const val BOOKING_ID = "booking-id"
private const val MEMBER_ID = "member-id"
private const val COURT_ID = "court-id"

@RunWith(JUnitPlatform::class)
class BookingTest : Spek({

    val timeSlot = TimeSlot(LocalTime.of(10, 0), LocalTime.of(10, 40))

    it("can create booking") {
        val booking = Booking(BOOKING_ID, MEMBER_ID, COURT_ID, timeSlot)

        assertThat(booking.id).isEqualTo(BOOKING_ID)
        assertThat(booking.memberId).isEqualTo(MEMBER_ID)
        assertThat(booking.courtId).isEqualTo(COURT_ID)
        assertThat(booking.timeSlot).isEqualTo(timeSlot)
        assertThat(booking.status).isEqualTo(Booking.Status.CREATED)

        assertThat(booking.getDomainEvents()).containsExactly(
                ScheduleEvents.BookingCreated(BOOKING_ID)
        )
    }

    it("can cancel booking") {
        val booking = Booking(BOOKING_ID, MEMBER_ID, COURT_ID, timeSlot)

        booking.cancel(MEMBER_ID)

        assertThat(booking.status).isEqualTo(Booking.Status.CANCELLED)
        assertThat(booking.getDomainEvents().last()).isEqualTo(
                ScheduleEvents.BookingCancelled(BOOKING_ID, MEMBER_ID)
        )
    }

    it("can confirm booking") {
        val booking = Booking(BOOKING_ID, MEMBER_ID, COURT_ID, timeSlot)

        booking.confirm(MEMBER_ID)

        assertThat(booking.status).isEqualTo(Booking.Status.CONFIRMED)
        assertThat(booking.getDomainEvents().last()).isEqualTo(
                ScheduleEvents.BookingConfirmed(BOOKING_ID, MEMBER_ID)
        )
    }

    it("can not confirm booking of another member") {
        val booking = Booking(BOOKING_ID, "another-member-id", COURT_ID, timeSlot)

        assertFailsWith<ScheduleExceptions.BookingBelongsToAnotherMember> {
            booking.confirm(MEMBER_ID)
        }
    }

    it("can update court") {
        val booking = Booking(BOOKING_ID, MEMBER_ID, COURT_ID, timeSlot)
        val newCourtId = "new-court-id"

        booking.updateCourt(newCourtId)

        assertThat(booking.courtId).isEqualTo(newCourtId)
    }

    it("can update time") {
        val booking = Booking(BOOKING_ID, MEMBER_ID, COURT_ID, timeSlot)
        val newTimeSlot = TimeSlot(LocalTime.of(11, 0), LocalTime.of(11, 40))

        booking.updateTime(newTimeSlot)

        assertThat(booking.timeSlot).isEqualTo(newTimeSlot)
    }

})