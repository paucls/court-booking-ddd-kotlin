package ddd.guild.courtbooking.api.courts

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class CourtController {

    val stubCourts = listOf(
            CourtDto("club-1", "court-1", "Court 1", 9, 20),
            CourtDto("club-1", "court-2", "Court 2", 9, 20),
            CourtDto("club-1", "court-3", "Court 3", 9, 16),
            CourtDto("club-2", "court-1", "Court 1", 12, 21),
            CourtDto("club-2", "court-2", "Court 2", 12, 21)
    )

    @RequestMapping(value = ["{clubId}/courts"], method = [(RequestMethod.GET)])
    fun getClubCourts(@PathVariable clubId: String): ResponseEntity<List<CourtDto>> {
        val courts = stubCourts.filter { it.clubId == clubId }
        return ResponseEntity(courts, HttpStatus.OK)
    }

}
