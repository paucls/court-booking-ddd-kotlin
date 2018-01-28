package ddd.guild.courtbooking.domain.schedule

import ddd.guild.courtbooking.domain.shared.DomainEntity
import ddd.guild.courtbooking.domain.shared.DomainEventPublisher
import java.time.LocalDate
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Booking(
        @Id
        val id: String,
        val memberId: String,
        courtId: String,
        val day: LocalDate,
        timeSlot: TimeSlot
) : DomainEntity {

    var courtId: String
        private set
    @Embedded
    var timeSlot: TimeSlot
        private set
    var status: Status
        private set

    init {
        this.courtId = courtId
        this.timeSlot = timeSlot
        status = Status.CREATED

        DomainEventPublisher.publish(ScheduleEvents.BookingCreated(id))
    }

    fun cancel(memberId: String) {
        status = Status.CANCELLED

        DomainEventPublisher.publish(ScheduleEvents.BookingCancelled(id, memberId))
    }

    fun confirm(memberId: String) {
        if (this.memberId != memberId) {
            throw ScheduleExceptions.BookingBelongsToAnotherMember()
        }

        status = Status.CONFIRMED

        DomainEventPublisher.publish(ScheduleEvents.BookingConfirmed(id, memberId))
    }

    fun updateCourt(newCourtId: String) {
        if (newCourtId == courtId) return

        courtId = newCourtId
    }

    fun updateTime(newTimeSlot: TimeSlot) {
        if (newTimeSlot == timeSlot) return

        timeSlot = newTimeSlot
    }

    enum class Status {
        CREATED,
        CANCELLED,
        CONFIRMED
    }
}

