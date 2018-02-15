package ddd.guild.courtbooking.api.courtschedules

import java.time.LocalDateTime

class EventDto(
        val id: String,
        val start: LocalDateTime,
        val end: LocalDateTime,
        val description: String?
)