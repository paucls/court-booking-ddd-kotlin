package ddd.guild.courtbooking.api.booking

import ddd.guild.courtbooking.application.booking.BookingCommands
import ddd.guild.courtbooking.application.booking.BookingRepository
import ddd.guild.courtbooking.application.booking.BookingService
import ddd.guild.courtbooking.domain.booking.Booking
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.time.LocalDateTime

@Controller
class BookingController(
        val bookingService: BookingService,
        val bookingRepository: BookingRepository
) {

    // Hardcoded current user id, in a real app it will be come from auth module
    private val CURRENT_USER_ID = "user-id"

    /**
     * Commands
     */

    @RequestMapping(value = ["/{clubId}/bookings"], method = [(RequestMethod.POST)])
    fun makeBooking(@PathVariable clubId: String, @RequestBody dto: BookingDto): ResponseEntity<BookingDto> {
        val booking = bookingService.makeBooking(BookingCommands.MakeBooking(
                clubId,
                dto.courtId,
                dto.start.toLocalDate(),
                dto.start.toLocalTime(),
                dto.end.toLocalTime(),
                CURRENT_USER_ID))

        return ResponseEntity(mapToDto(booking), HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/bookings/{bookingId}/cancel"], method = [(RequestMethod.POST)])
    fun cancelBooking(@PathVariable bookingId: String): ResponseEntity<BookingDto> {
        bookingService.cancelBooking(BookingCommands.CancelBooking(bookingId, CURRENT_USER_ID))

        val booking = bookingRepository.findById(bookingId).get()

        return ResponseEntity(mapToDto(booking), HttpStatus.OK)
    }

    @RequestMapping(value = ["/bookings/{bookingId}/confirm"], method = [(RequestMethod.POST)])
    fun confirmBooking(@PathVariable bookingId: String): ResponseEntity<BookingDto> {
        bookingService.confirmBooking(BookingCommands.ConfirmBooking(bookingId, CURRENT_USER_ID))

        val booking = bookingRepository.findById(bookingId).get()

        return ResponseEntity(mapToDto(booking), HttpStatus.OK)
    }

    /**
     * Queries
     */

    @RequestMapping(value = ["/{clubId}/bookings"], method = [(RequestMethod.GET)])
    fun getAllBookings(@PathVariable clubId: String): ResponseEntity<List<BookingDto>> {
        // Should be split completely queries from commands, and have a queries only repository
        // to be consumed by the API Controllers?
        val bookings = bookingRepository
                .findAllByClubId(clubId)
                .map(this::mapToDto).sortedBy { it.start }

        return ResponseEntity(bookings, HttpStatus.OK)
    }

    private fun mapToDto(b: Booking): BookingDto {
        return BookingDto(
                b.id,
                b.courtId,
                LocalDateTime.of(b.day, b.time.start),
                LocalDateTime.of(b.day, b.time.end),
                b.memberId,
                b.status.toString()
        )
    }

}