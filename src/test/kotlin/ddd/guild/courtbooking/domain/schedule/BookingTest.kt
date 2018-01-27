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
        assertThat(booking.isConfirmed).isFalse()
    }

    it("can confirm booking") {
        val booking = Booking(BOOKING_ID, MEMBER_ID, COURT_ID, timeSlot)

        booking.confirm(MEMBER_ID)

        assertThat(booking.isConfirmed).isTrue()
    }

    it("can not confirm booking of another member") {
        val booking = Booking(BOOKING_ID, "another-member-id", COURT_ID, timeSlot)

        assertFailsWith<ScheduleExceptions.BookingBelongsToAnotherMember> {
            booking.confirm(MEMBER_ID)
        }
    }

})