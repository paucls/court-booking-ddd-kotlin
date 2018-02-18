package ddd.guild.courtbooking.api.schedule

import java.time.LocalDateTime

class EntryDto(
        val id: String,
        val start: LocalDateTime,
        val end: LocalDateTime,
        val description: String?
)