package ddd.guild.courtbooking.api.schedule

import ddd.guild.courtbooking.application.schedule.BookingCommands
import ddd.guild.courtbooking.application.schedule.BookingRepository
import ddd.guild.courtbooking.application.schedule.BookingService
import ddd.guild.courtbooking.domain.schedule.Booking
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
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
     * Commands
     */

    @RequestMapping(value = ["/bookings"], method = [(RequestMethod.POST)])
    fun createBooking(@RequestBody dto: BookingDto): ResponseEntity<BookingDto> {
        val bookingId = bookingService.createBooking(
                BookingCommands.CreateBooking(dto.courtId, dto.day, dto.startTime, dto.endTime, CURRENT_USER_ID))

        // Should the Service return the Entity? Should a DTO be used for the API layer or the Entity?
        val booking = bookingRepository.findById(bookingId).get()

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

    @RequestMapping(value = ["/bookings"], method = [(RequestMethod.GET)])
    fun getAllBookings(): ResponseEntity<List<BookingDto>> {
        // Should be split completely queries from commands, and have a queries only repository
        // to be consumed by the API Controllers?
        val bookings = bookingRepository.findAll().map(this::mapToDto)

        return ResponseEntity(bookings, HttpStatus.OK)
    }

    private fun mapToDto(b: Booking): BookingDto {
        return BookingDto(b.id, b.courtId, b.day,
                b.timeSlot.startTime, b.timeSlot.endTime,
                b.memberId, b.status.toString()
        )
    }

}