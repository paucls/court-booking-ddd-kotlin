package ddd.guild.courtbooking.api.courtschedules

import ddd.guild.courtbooking.api.courts.CourtDto
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
class CourtScheduleController {

    private val day: LocalDate = LocalDate.of(2018, 2, 11)
    private val court1 = CourtDto("club-1", "court-1", "Court 1", 9, 20)
    private val court2 = CourtDto("club-1", "court-2", "Court 2", 9, 20)
    private val court3 = CourtDto("club-1", "court-3", "Court 3", 9, 16)
    private val event1 = EventDto(
            id = "event-1",
            description = "Booked",
            start = LocalDateTime.of(2018, 2, 11, 10, 0),
            end = LocalDateTime.of(2018, 2, 11, 10, 30)
    )
    private val event2 = EventDto(
            id = "event-2",
            description = "Booked",
            start = LocalDateTime.of(2018, 2, 11, 11, 0),
            end = LocalDateTime.of(2018, 2, 11, 12, 0)
    )
    private val stubCourtSchedules = listOf(
            CourtScheduleDto(day, court1, events = listOf(event1)),
            CourtScheduleDto(day, court2, events = listOf(event2)),
            CourtScheduleDto(day, court3, events = listOf())
    )

    @RequestMapping(value = ["{clubId}/court-schedules"], method = [(RequestMethod.GET)])
    fun getCourtSchedules(@PathVariable clubId: String, @RequestParam day: String): ResponseEntity<List<CourtScheduleDto>> {
        val schedules = stubCourtSchedules.filter {
            it.court.clubId == clubId
                    && it.day == LocalDate.parse(day)
        }
        return ResponseEntity(schedules, HttpStatus.OK)
    }

}
