package ddd.guild.courtbooking.domain.schedule

import ddd.guild.courtbooking.domain.DomainEntity

class Booking(
        val id: String,
        val memberId: String,
        val courtId: String
) : DomainEntity {
    var isConfirmed = false
        private set

    fun confirm(memberId: String) {
        isConfirmed = true
    }
}