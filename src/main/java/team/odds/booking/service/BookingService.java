package team.odds.booking.service;

import org.springframework.stereotype.Service;
import team.odds.booking.exception.DataNotFound;
import team.odds.booking.model.Booking;
import team.odds.booking.model.dto.BookingDto;
import team.odds.booking.model.mapper.BookingMapper;
import team.odds.booking.repository.BookingRepository;
import team.odds.booking.util.HelperUtils;

import java.time.LocalDateTime;

@Service
public record BookingService(BookingRepository bookingRepository,
                             BookingMapper bookingMapper,
                             MailSenderService mailSenderService) {

    public Booking getBooking(String bookingId) throws RuntimeException {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DataNotFound("Booking not found with this id : " + bookingId));

        if (HelperUtils.checkBookingDateExpired(booking.getCreatedAt())) {
            bookingRepository.deleteById(bookingId);
            throw new DataNotFound("Booking is expired");
        }
        return booking;
    }

    public Booking addBooking(BookingDto dataRequest) throws Exception {
        var booking = bookingMapper.toBooking(dataRequest);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());

        var bookingRes = new Booking();
        try {
            bookingRes = bookingRepository.save(booking);
        } catch (RuntimeException e) {
            throw new RuntimeException("Some error occurred while creating booking", e);
        }
        var confirmUrl = "https://odds-booking.odds.team/booking_detail/" + bookingRes.getId();
        mailSenderService.mailToUser(confirmUrl, bookingRes);
        return bookingRes;
    }

    public Booking updateBooking(String bookingId, BookingDto dataRequest) throws Exception {
        Booking bookingById = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DataNotFound("Booking not found with this id : " + bookingId));

        var booking = bookingMapper.toBooking(dataRequest);
        booking.setId(bookingById.getId());
        booking.setCreatedAt(bookingById.getCreatedAt());
        booking.setUpdatedAt(LocalDateTime.now());

        var bookingRes = new Booking();
        try {
            bookingRes = bookingRepository.save(booking);
        } catch (RuntimeException e) {
            throw new RuntimeException("Some error occurred while updating booking", e);
        }
        var bookingDetailsUrl = "https://odds-booking.odds.team/booking_detail/" + bookingRes.getId();
        mailSenderService.mailToOdds(bookingDetailsUrl, bookingRes);
        return bookingRes;
    }
}
