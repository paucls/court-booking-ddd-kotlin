package ddd.guild.courtbooking.application.schedule

import ddd.guild.courtbooking.domain.schedule.Schedule
import org.springframework.data.repository.Repository
import java.time.LocalDate
import java.util.*

interface ScheduleRepository : Repository<Schedule, String> {
    fun save(schedule: Schedule): Schedule

    fun findByClubIdAndCourtIdAndDay(clubId: String, courtId: String, day: LocalDate): Optional<Schedule>
}

fun ScheduleRepository.nextIdentity(): String {
    return java.util.UUID.randomUUID().toString()
}
