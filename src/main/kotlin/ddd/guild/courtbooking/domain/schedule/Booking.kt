package ddd.guild.courtbooking.domain.schedule

import ddd.guild.courtbooking.domain.DomainEntity
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Transient

@Entity
class Booking(
        @Id
        val id: String,
        val memberId: String,
        courtId: String,
        timeSlot: TimeSlot
) : DomainEntity {

    var courtId: String
        private set
    @Embedded
    var timeSlot: TimeSlot
        private set
    var status: Status
        private set

    @Transient
    private val domainEvents = mutableListOf<ScheduleEvents>()

    init {
        this.courtId = courtId
        this.timeSlot = timeSlot
        status = Status.CREATED
        domainEvents.add(ScheduleEvents.BookingCreated(id))
    }

    fun getDomainEvents(): List<ScheduleEvents> {
        return domainEvents.toList()
    }

    fun cancel(memberId: String) {
        status = Status.CANCELLED
        domainEvents.add(ScheduleEvents.BookingCancelled(id, memberId))
    }

    fun confirm(memberId: String) {
        if (this.memberId != memberId) {
            throw ScheduleExceptions.BookingBelongsToAnotherMember()
        }
        status = Status.CONFIRMED
        domainEvents.add(ScheduleEvents.BookingConfirmed(id, memberId))
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

