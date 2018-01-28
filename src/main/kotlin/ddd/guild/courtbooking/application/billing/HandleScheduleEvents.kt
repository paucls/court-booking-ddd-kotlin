package ddd.guild.courtbooking.application.billing

import ddd.guild.courtbooking.domain.schedule.ScheduleEvents
import ddd.guild.courtbooking.domain.shared.DomainEvent
import ddd.guild.courtbooking.domain.shared.DomainEventHandler
import ddd.guild.courtbooking.domain.shared.DomainEventPublisher
import org.springframework.stereotype.Service

@Service
class HandleScheduleEvents(domainEventPublisher: DomainEventPublisher) : DomainEventHandler {

    init {
        domainEventPublisher.register(this)
    }

    override fun handle(domainEvent: DomainEvent) {
        when (domainEvent) {
            is ScheduleEvents.BookingConfirmed -> {
                println("Handling Booking Confirmed: $domainEvent")

                // TODO: Call Billing App Service to deal with this use case
                println("... creating new Invoice and sending it to Member")
            }

            is ScheduleEvents.BookingCancelled -> {
                println("Handling Booking Cancelled: $domainEvent")

                // TODO: Call Billing App Service to deal with this use case
                println("... applying cancellation fee to Member")
            }
        }
    }

}