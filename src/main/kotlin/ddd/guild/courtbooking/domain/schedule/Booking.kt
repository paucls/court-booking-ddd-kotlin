package ddd.guild.courtbooking.domain.schedule

import ddd.guild.courtbooking.domain.DomainEntity

class Booking(
        val id: String,
        val memberId: String,
        val courtId: String,
        val timeSlot: TimeSlot
) : DomainEntity {

    var status: Status private set

    private val domainEvents = mutableListOf<ScheduleEvents>()

    init {
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

    enum class Status {
        CREATED,
        CANCELLED,
        CONFIRMED
    }
}

