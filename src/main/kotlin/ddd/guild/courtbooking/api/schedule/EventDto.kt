package ddd.guild.courtbooking.api.schedule

import java.time.LocalDateTime

class EventDto(
        val id: String,
        val start: LocalDateTime,
        val end: LocalDateTime,
        val description: String?
)