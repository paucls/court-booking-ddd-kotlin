package ddd.guild.courtbooking.api.schedule

import ddd.guild.courtbooking.api.courts.CourtDto
import ddd.guild.courtbooking.application.schedule.ScheduleRepository
import ddd.guild.courtbooking.domain.schedule.Schedule
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate
import java.time.LocalDateTime

@Controller
class CourtScheduleController(
        private val scheduleRepository: ScheduleRepository
) {

    private val stubCourts = listOf(
            CourtDto("club-1", "court-1", "Court 1", 9, 20),
            CourtDto("club-1", "court-2", "Court 2", 9, 20),
            CourtDto("club-1", "court-3", "Court 3", 9, 16)
    )

    @RequestMapping(value = ["{clubId}/court-schedules"], method = [(RequestMethod.GET)])
    fun getCourtSchedules(@PathVariable clubId: String, @RequestParam day: String): ResponseEntity<List<CourtScheduleDto>> {
        val date = LocalDate.parse(day)

        val courtSchedules = stubCourts.map {
            val schedule = scheduleRepository.findByClubIdAndCourtIdAndDay(clubId, it.id, date)

            if (schedule.isPresent) {
                CourtScheduleDto(date, it, parseEntries(schedule.get()))
            } else {
                CourtScheduleDto(date, it, emptyList())
            }
        }

        return ResponseEntity(courtSchedules, HttpStatus.OK)
    }

    private fun parseEntries(schedule: Schedule): List<EntryDto> {
        return schedule.entries.map { entry ->
            val start = LocalDateTime.of(schedule.day, entry.time.start)
            val end = LocalDateTime.of(schedule.day, entry.time.end)
            EntryDto(entry.id, start, end, entry.description)
        }
    }
}
