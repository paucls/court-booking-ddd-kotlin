package ddd.guild.courtbooking.domain.schedule

import ddd.guild.courtbooking.domain.shared.DomainException

class ScheduleExceptions {
    class InvalidTimeInterval : DomainException()
    class TimeConflict : DomainException()
}