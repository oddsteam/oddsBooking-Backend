package team.odds.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.odds.booking.exception.DataNotFound;
import team.odds.booking.model.Booking;
import team.odds.booking.model.dto.BookingDto;
import team.odds.booking.model.mapper.BookingMapper;
import team.odds.booking.repository.BookingRepository;
import team.odds.booking.util.HelpersUtil;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final QueueProducerService queueProducerService;

    public Booking getBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DataNotFound("Booking not found with this id : " + bookingId));

        if (!booking.getStatus() && HelpersUtil.checkBookingDateExpired(booking.getCreatedAt())) {
            bookingRepository.deleteById(bookingId);
            throw new DataNotFound("Booking is expired");
        }
        return booking;
    }

    public Booking addBooking(BookingDto dataRequest) {
        var booking = bookingMapper.toBooking(dataRequest);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());

        var bookingRes = bookingRepository.save(booking);
        queueProducerService.sendMessage(bookingRes.getId());
        return bookingRes;
    }

    public Booking updateBooking(String bookingId, BookingDto dataRequest) {
        Booking bookingById = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new DataNotFound("Booking not found with this id : " + bookingId));

        var booking = bookingMapper.toBooking(dataRequest);
        booking.setId(bookingById.getId());
        booking.setCreatedAt(bookingById.getCreatedAt());
        booking.setUpdatedAt(LocalDateTime.now());

        var bookingRes = bookingRepository.save(booking);
        queueProducerService.sendMessage(bookingRes.getId());
        return bookingRes;
    }
}
