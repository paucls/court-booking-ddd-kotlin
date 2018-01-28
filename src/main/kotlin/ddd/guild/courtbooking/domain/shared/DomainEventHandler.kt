package ddd.guild.courtbooking.domain.shared

interface DomainEventHandler {
    fun handle(domainEvent: DomainEvent)
}