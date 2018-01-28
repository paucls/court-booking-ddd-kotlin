package ddd.guild.courtbooking.api.schedule

import java.time.LocalDate
import java.time.LocalTime

data class BookingDto(
        val id: String?,
        val courtId: String,
        val day: LocalDate,
        val startTime: LocalTime,
        val endTime: LocalTime,
        val memberId: String?,
        val status: String?
)
