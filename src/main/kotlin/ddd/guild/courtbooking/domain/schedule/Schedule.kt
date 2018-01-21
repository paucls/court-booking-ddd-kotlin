package ddd.guild.courtbooking.domain.schedule

import ddd.guild.courtbooking.domain.DomainEntity
import java.time.LocalDate

/**
 * Aggregate Root
 */
class Schedule(
        val id: String,
        val locationId: String,
        val day: LocalDate
) : DomainEntity {
}