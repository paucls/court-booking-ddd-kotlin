package ddd.guild.courtbooking.api.schedule

import ddd.guild.courtbooking.application.schedule.BookingRepository
import ddd.guild.courtbooking.application.schedule.BookingService
import ddd.guild.courtbooking.domain.schedule.Booking
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class BookingController(
        val bookingService: BookingService,
        val bookingRepository: BookingRepository
) {

    // Hardcoded current user id, in a real app it will be come from auth module
    private val CURRENT_USER_ID = "user-id"


    /**
     * Queries
     */

    @RequestMapping(value = ["/bookings"], method = [(RequestMethod.GET)])
    fun getAllBookings(): ResponseEntity<List<Booking>> {
        // Should be split completely queries from commands, and have a queries only repository
        // to be consumed by the API Controllers?
        val bookings = bookingRepository.findAll()

        return ResponseEntity(bookings, HttpStatus.OK)
    }

}