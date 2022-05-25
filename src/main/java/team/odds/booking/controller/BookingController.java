package team.odds.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.odds.booking.model.Booking;
import team.odds.booking.service.BookingService;

import java.util.List;

@RequestMapping("/v1/booking")
@RestController
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping(value = "/{bookingId}")
    public ResponseEntity<Booking> getBooking(@PathVariable(value = "bookingId") String bookingId) throws Exception {
        return new ResponseEntity<>(bookingService.getBooking(bookingId), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Booking>> getBookingList() throws Exception {
        return new ResponseEntity<>(bookingService.getBookingList(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Booking> addBooking(@RequestBody Booking dataRequest) throws Exception {
        return new ResponseEntity<>(bookingService.addBooking(dataRequest), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{bookingId}")
    public ResponseEntity<Booking> updateBooking(@PathVariable(value = "bookingId") String bookingId, @RequestBody Booking dataRequest) throws Exception {
        return new ResponseEntity<>(bookingService.updateBooking(bookingId, dataRequest), HttpStatus.OK);
    }

}

