package team.odds.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.odds.booking.model.Booking;
import team.odds.booking.service.BookingService;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/v1/booking")
@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping()
    public ResponseEntity<Booking> createBooking(@RequestBody Booking dataRequest) throws Exception {
        return new ResponseEntity<>(bookingService.createBooking(dataRequest), HttpStatus.CREATED);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable(value = "id") String bookingId) throws Exception {
        System.out.println("================== Controller =================");
        System.out.println(bookingId);
        System.out.println("=======================================");
        return new ResponseEntity<>(bookingService.getBookingById(bookingId), HttpStatus.OK);
    }

}

