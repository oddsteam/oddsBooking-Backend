package team.odds.booking.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.odds.booking.model.Booking;
import team.odds.booking.model.dto.BookingDto;
import team.odds.booking.service.BookingService;

@AllArgsConstructor
@RequestMapping("/v1/booking")
@RestController
public class BookingController {

    final BookingService bookingService;

    @GetMapping(value = "/{bookingId}")
    public Booking getBooking(@PathVariable(value = "bookingId") String bookingId) {
        return bookingService.getBooking(bookingId);
    }

    @PostMapping()
    public Booking addBooking(@RequestBody BookingDto dataRequest) {
        return bookingService.addBooking(dataRequest);
    }

    @PutMapping(value = "/{bookingId}")
    public Booking updateBooking(@PathVariable(value = "bookingId") String bookingId, @RequestBody BookingDto dataRequest) {
        return bookingService.updateBooking(bookingId, dataRequest);
    }

}

