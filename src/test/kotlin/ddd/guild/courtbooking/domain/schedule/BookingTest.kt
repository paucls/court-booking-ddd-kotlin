package ddd.guild.courtbooking.domain.schedule

import ddd.guild.courtbooking.domain.shared.DomainEventPublisher
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import java.time.LocalDate
import java.time.LocalTime
import kotlin.test.assertFailsWith

private const val BOOKING_ID = "booking-id"
private const val MEMBER_ID = "member-id"
private const val COURT_ID = "court-id"

class BookingTest : Spek({

    val day = LocalDate.of(2018, 1, 30)
    val time = BookingTime(LocalTime.of(10, 0), LocalTime.of(10, 40))

    beforeEachTest {
        DomainEventPublisher.clear()
    }

    it("can create booking") {
        val booking = Booking(BOOKING_ID, MEMBER_ID, COURT_ID, day, time)

        assertThat(booking.id).isEqualTo(BOOKING_ID)
        assertThat(booking.memberId).isEqualTo(MEMBER_ID)
        assertThat(booking.courtId).isEqualTo(COURT_ID)
        assertThat(booking.time).isEqualTo(time)
        assertThat(booking.status).isEqualTo(Booking.Status.CREATED)

        assertThat(DomainEventPublisher.domainEvents).containsExactly(
                ScheduleEvents.BookingCreated(BOOKING_ID)
        )
    }

    it("can cancel booking") {
        val booking = Booking(BOOKING_ID, MEMBER_ID, COURT_ID, day, time)

        booking.cancel(MEMBER_ID)

        assertThat(booking.status).isEqualTo(Booking.Status.CANCELLED)
        assertThat(DomainEventPublisher.domainEvents.last()).isEqualTo(
                ScheduleEvents.BookingCancelled(BOOKING_ID, MEMBER_ID)
        )
    }

    it("can confirm booking") {
        val booking = Booking(BOOKING_ID, MEMBER_ID, COURT_ID, day, time)

        booking.confirm(MEMBER_ID)

        assertThat(booking.status).isEqualTo(Booking.Status.CONFIRMED)
        assertThat(DomainEventPublisher.domainEvents.last()).isEqualTo(
                ScheduleEvents.BookingConfirmed(BOOKING_ID, MEMBER_ID)
        )
    }

    it("can not confirm booking of another member") {
        val booking = Booking(BOOKING_ID, "another-member-id", COURT_ID, day, time)

        assertFailsWith<ScheduleExceptions.BookingBelongsToAnotherMember> {
            booking.confirm(MEMBER_ID)
        }
    }

    it("can update court") {
        val booking = Booking(BOOKING_ID, MEMBER_ID, COURT_ID, day, time)
        val newCourtId = "new-court-id"

        booking.updateCourt(newCourtId)

        assertThat(booking.courtId).isEqualTo(newCourtId)
    }

    it("can update time") {
        val booking = Booking(BOOKING_ID, MEMBER_ID, COURT_ID, day, time)
        val newTime = BookingTime(LocalTime.of(11, 0), LocalTime.of(11, 40))

        booking.updateTime(newTime)

        assertThat(booking.time).isEqualTo(newTime)
    }

})