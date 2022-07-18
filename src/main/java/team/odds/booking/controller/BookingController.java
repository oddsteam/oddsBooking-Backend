package team.odds.booking.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.odds.booking.model.Booking;
import team.odds.booking.model.BaseResponse;
import team.odds.booking.model.dto.BookingDto;
import team.odds.booking.service.BookingService;

@AllArgsConstructor
@RequestMapping("/v1/booking")
@RestController
public class BookingController {

    private final BookingService bookingService;

    @GetMapping(value = "/{bookingId}")
    public ResponseEntity<BaseResponse<Booking>> getBooking(@PathVariable(value = "bookingId") String bookingId) {
        var res = new BaseResponse<>(HttpStatus.OK.value(), bookingService.getBooking(bookingId));
        return ResponseEntity.ok().body(res);
    }

    @PostMapping()
    public ResponseEntity<BaseResponse<Booking>> addBooking(@RequestBody BookingDto dataRequest) {
        var res = new BaseResponse<>(HttpStatus.CREATED.value(), bookingService.addBooking(dataRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping(value = "/{bookingId}")
    public ResponseEntity<BaseResponse<Booking>> updateBooking(
            @PathVariable(value = "bookingId") String bookingId,
            @RequestBody BookingDto dataRequest) {
        var res = new BaseResponse<>(HttpStatus.OK.value(), bookingService.updateBooking(bookingId, dataRequest));
        return ResponseEntity.ok().body(res);
    }

}

