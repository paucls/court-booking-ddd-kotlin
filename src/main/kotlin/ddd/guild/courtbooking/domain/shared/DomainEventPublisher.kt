package ddd.guild.courtbooking.domain.shared

import ddd.guild.courtbooking.domain.schedule.ScheduleEvents

/**
 * A poor man's local domain event publisher
 */
object DomainEventPublisher {
    val domainEvents = mutableListOf<ScheduleEvents>()

    private val handlers = mutableListOf<DomainEventHandler>()

    fun publish(event: ScheduleEvents) {
        println("Publishing Event: $event")
        domainEvents.add(event)
        handlers.forEach { it.handle(event) }
    }

    fun clear() {
        domainEvents.clear()
    }

    fun register(handler: DomainEventHandler) {
        handlers.add(handler)
    }
}

