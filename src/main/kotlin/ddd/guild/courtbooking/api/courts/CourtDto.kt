package ddd.guild.courtbooking.api.courts

class CourtDto(
        val clubId: String,
        val id: String,
        val name: String,
        val dayStarHour: Number,
        val dayEndHour: Number
)