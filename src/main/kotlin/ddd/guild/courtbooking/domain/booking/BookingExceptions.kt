package ddd.guild.courtbooking.domain.booking

import ddd.guild.courtbooking.domain.shared.DomainException

class BookingExceptions {
    class BookingBelongsToAnotherMember : DomainException()
    class InvalidDuration : DomainException()
}