package ddd.guild.courtbooking.domain.schedule

import ddd.guild.courtbooking.domain.DomainException

class ScheduleExceptions {
    class BookingBelongsToAnotherMember : DomainException()
    class BookingTimeConflict : DomainException()
    class InvalidDuration : DomainException()
    class InvalidTimeInterval : DomainException()
}