package ddd.guild.courtbooking.api.booking

import java.time.LocalDateTime

data class BookingDto(
        val id: String?,
        val courtId: String,
        val start: LocalDateTime,
        val end: LocalDateTime,
        val memberId: String?,
        val status: String?
)
