package ddd.guild.courtbooking.domain.schedule

import ddd.guild.courtbooking.domain.DomainEntity

class Booking(
        val id: String,
        val memberId: String,
        val courtId: String,
        val timeSlot: TimeSlot
) : DomainEntity {
    var isConfirmed = false
        private set

    fun confirm(memberId: String) {
        if (this.memberId != memberId) {
            throw ScheduleExceptions.BookingBelongsToAnotherMember()
        }
        isConfirmed = true
    }
}
