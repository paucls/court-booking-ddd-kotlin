package ddd.guild.courtbooking.api.courtschedules

import ddd.guild.courtbooking.api.courts.CourtDto
import java.time.LocalDate

class CourtScheduleDto(
        val day: LocalDate,
        val court: CourtDto,
        val events: List<EventDto>
)
