package ddd.guild.courtbooking.domain.booking

import ddd.guild.courtbooking.domain.shared.DomainEvent

sealed class BookingEvents : DomainEvent {
    data class BookingCreated(val id: String) : BookingEvents()
    data class BookingCancelled(val id: String, val memberId: String) : BookingEvents()
    data class BookingConfirmed(val id: String, val memberId: String) : BookingEvents()
}