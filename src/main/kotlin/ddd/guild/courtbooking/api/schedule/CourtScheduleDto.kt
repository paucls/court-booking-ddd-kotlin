package ddd.guild.courtbooking.api.schedule

import ddd.guild.courtbooking.api.courts.CourtDto
import java.time.LocalDate

class CourtScheduleDto(
        val day: LocalDate,
        val court: CourtDto,
        val events: List<EventDto>
)
