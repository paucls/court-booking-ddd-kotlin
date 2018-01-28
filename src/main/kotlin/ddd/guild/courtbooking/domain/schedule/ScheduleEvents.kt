package ddd.guild.courtbooking.domain.schedule

import ddd.guild.courtbooking.domain.shared.DomainEvent

sealed class ScheduleEvents : DomainEvent {
    data class BookingCreated(val id: String) : ScheduleEvents()
    data class BookingCancelled(val id: String, val memberId: String) : ScheduleEvents()
    data class BookingConfirmed(val id: String, val memberId: String) : ScheduleEvents()
}