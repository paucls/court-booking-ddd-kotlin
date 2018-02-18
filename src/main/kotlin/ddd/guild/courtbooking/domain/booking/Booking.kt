package ddd.guild.courtbooking.domain.booking

import ddd.guild.courtbooking.domain.schedule.TimeRange
import ddd.guild.courtbooking.domain.shared.DomainEntity
import ddd.guild.courtbooking.domain.shared.DomainEventPublisher
import java.time.LocalDate
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
        val day: LocalDate,
        time: TimeRange
) : DomainEntity {

    var courtId: String
        private set
    @Embedded
    var time: TimeRange
        private set
    var status: Status
        private set

    @Transient
    private val minValidDuration = 30

    init {
        if (time.durationInMinutes < minValidDuration) throw BookingExceptions.InvalidDuration()

        this.courtId = courtId
        this.time = time
        status = Status.CREATED

        DomainEventPublisher.publish(BookingEvents.BookingCreated(id))
    }

    fun cancel(memberId: String) {
        status = Status.CANCELLED

        DomainEventPublisher.publish(BookingEvents.BookingCancelled(id, memberId))
    }

    fun confirm(memberId: String) {
        if (this.memberId != memberId) {
            throw BookingExceptions.BookingBelongsToAnotherMember()
        }

        status = Status.CONFIRMED

        DomainEventPublisher.publish(BookingEvents.BookingConfirmed(id, memberId))
    }

    fun updateCourt(newCourtId: String) {
        if (newCourtId == courtId) return

        courtId = newCourtId
    }

    fun updateTime(newTime: TimeRange) {
        if (newTime == time) return

        time = newTime
    }

    enum class Status {
        CREATED,
        CANCELLED,
        CONFIRMED
    }
}

