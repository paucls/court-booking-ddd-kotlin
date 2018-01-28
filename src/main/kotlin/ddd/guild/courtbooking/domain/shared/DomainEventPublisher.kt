package ddd.guild.courtbooking.domain.shared

import org.springframework.stereotype.Service

/**
 * A poor man's local domain event publisher
 */
@Service
class DomainEventPublisher {

    fun publish(event: DomainEvent) = DomainEventPublisher.publish(event)

    fun clear() = DomainEventPublisher.clear()

    fun register(handler: DomainEventHandler) = DomainEventPublisher.register(handler)

    companion object {
        val domainEvents = mutableListOf<DomainEvent>()
        private val handlers = mutableListOf<DomainEventHandler>()

        fun publish(event: DomainEvent) {
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
}

