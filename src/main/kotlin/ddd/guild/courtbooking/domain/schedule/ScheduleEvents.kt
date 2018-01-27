package ddd.guild.courtbooking.domain.schedule

sealed class ScheduleEvents {
    data class BookingCreated(val id: String) : ScheduleEvents()
    data class BookingCancelled(val id: String, val memberId: String) : ScheduleEvents()
    data class BookingConfirmed(val id: String, val memberId: String) : ScheduleEvents()
}