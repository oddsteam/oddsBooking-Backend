package team.odds.booking.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Booking> getBooking(@PathVariable(value = "bookingId") String bookingId) {
        return ResponseEntity.ok().body(bookingService.getBooking(bookingId));
    }

    @PostMapping()
    public ResponseEntity<Booking> addBooking(@RequestBody BookingDto dataRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.addBooking(dataRequest));
    }

    @PutMapping(value = "/{bookingId}")
    public ResponseEntity<Booking> updateBooking(@PathVariable(value = "bookingId") String bookingId, @RequestBody BookingDto dataRequest) throws Exception {
        return ResponseEntity.ok().body(bookingService.updateBooking(bookingId, dataRequest));
    }

}

