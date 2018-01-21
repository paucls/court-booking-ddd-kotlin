package ddd.guild.courtbooking.domain.schedule

import ddd.guild.courtbooking.domain.DomainException

class ScheduleExceptions {
    class BookingBelongsToAnotherMember : DomainException()
}