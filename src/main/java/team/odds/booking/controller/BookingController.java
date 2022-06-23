package team.odds.booking.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.odds.booking.model.Booking;
import team.odds.booking.model.BookingResponse;
import team.odds.booking.model.dto.BookingDto;
import team.odds.booking.service.BookingService;

@AllArgsConstructor
@RequestMapping("/v1/booking")
@RestController
public class BookingController {

    final BookingService bookingService;

    @GetMapping(value = "/{bookingId}")
    public ResponseEntity<BookingResponse<Booking>> getBooking(@PathVariable(value = "bookingId") String bookingId) {
        var res = new BookingResponse<>(HttpStatus.OK.value(), bookingService.getBooking(bookingId));
        return ResponseEntity.ok().body(res);
    }

    @PostMapping()
    public ResponseEntity<BookingResponse<Booking>> addBooking(@RequestBody BookingDto dataRequest) {
        var res = new BookingResponse<>(HttpStatus.CREATED.value(), bookingService.addBooking(dataRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping(value = "/{bookingId}")
    public ResponseEntity<BookingResponse<Booking>> updateBooking(
            @PathVariable(value = "bookingId") String bookingId,
            @RequestBody BookingDto dataRequest) {
        var res = new BookingResponse<>(HttpStatus.OK.value(), bookingService.updateBooking(bookingId, dataRequest));
        return ResponseEntity.ok().body(res);
    }

}

