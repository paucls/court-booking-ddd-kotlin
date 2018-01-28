package ddd.guild.courtbooking.domain

import ddd.guild.courtbooking.domain.schedule.ScheduleEvents

/**
 * A local domain event publisher
 */
object DomainEventPublisher {
    val domainEvents = mutableListOf<ScheduleEvents>()

    fun publish(event: ScheduleEvents) {
        domainEvents.add(event)
        println("Published Event: $event")
    }

    fun clear() {
        domainEvents.clear()
    }
}