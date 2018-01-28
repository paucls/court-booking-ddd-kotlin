package ddd.guild.courtbooking.domain

import ddd.guild.courtbooking.domain.schedule.ScheduleEvents

object DomainEventPublisher {
    val domainEvents = mutableListOf<ScheduleEvents>()

    fun publish(event: ScheduleEvents) {
        domainEvents.add(event)
        print("Published Event: $event")
    }
}