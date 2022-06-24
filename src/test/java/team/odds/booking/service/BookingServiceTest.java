package team.odds.booking.service;

import org.junit.jupiter.api.Test;
import team.odds.booking.exception.DataNotFound;
import team.odds.booking.model.Booking;
import team.odds.booking.model.dto.BookingDto;
import team.odds.booking.model.mapper.BookingMapper;
import team.odds.booking.repository.BookingRepository;
import team.odds.booking.util.HelpersUtil;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class BookingServiceTest {
    BookingRepository bookingRepository = mock(BookingRepository.class);
    BookingMapper bookingMapper = mock(BookingMapper.class);
    QueueProducerService queueProducerService = mock(QueueProducerService.class);
    BookingService bookingService = new BookingService(bookingRepository, bookingMapper, queueProducerService);

    @Test
    void getBooking() {
        // Arrange
        var localDateTimeNow = LocalDateTime.now();
        var booking = new Booking(
                "1234", "Taliw", "taliw78@gmail.com",
                "0640230334", "Neon", "Organize Event",
                localDateTimeNow, localDateTimeNow, false,
                localDateTimeNow, localDateTimeNow
        );
        String bookingId = booking.getId();
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        // Act
        var bookingRes = bookingService.getBooking(bookingId);
        // Assert
        assertThat(bookingRes.getId()).isEqualTo("1234");
        assertThat(bookingRes.getStatus()).isEqualTo(false);
        verify(bookingRepository).findById(bookingId);
    }

    @Test
    public void getBookingThrowException() {
        // Arrange
        var bookingId = "1-jfd234";
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());
        // Act
        var exception = assertThrows(DataNotFound.class, () -> bookingService.getBooking(bookingId));
        // Assert
        assertThat(exception.getMessage()).isEqualTo("Booking not found with this id : " + bookingId);
        verify(bookingRepository).findById(bookingId);
    }

    @Test
    void addBooking() {
        // Arrange
        var startDate = LocalDateTime.of(2022, 8, 9, 10, 34);
        var endDate = LocalDateTime.of(2022, 8, 10, 10, 34);
        var bookingReq = new BookingDto(
                "Taliw", "taliw78@gmail.com",
                "0640230334", "Neon", "Organize Event",
                HelpersUtil.dateTimeFormatGeneral(startDate),
                HelpersUtil.dateTimeFormatGeneral(endDate),
                false
        );
        var bookingRes = new Booking(
                "1234", "Taliw", "taliw78@gmail.com",
                "0640230334", "Neon", "Organize Event",
                startDate, endDate, false,
                LocalDateTime.now(), LocalDateTime.now()
        );
        when(bookingMapper.toBooking(bookingReq)).thenReturn(bookingRes);
        when(bookingRepository.save(any(Booking.class))).thenReturn(bookingRes);
        // Act
        var addBooking = bookingService.addBooking(bookingReq);
        // Assert
        assertThat(addBooking.getId()).isEqualTo("1234");
        assertThat(addBooking.getStatus()).isEqualTo(false);
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    void updateBooking() {
        // Arrange
        var bookingReqId = "1234";
        var startDate = LocalDateTime.of(2022, 8, 9, 10, 34);
        var endDate = LocalDateTime.of(2022, 8, 10, 10, 34);
        var bookingReq = new BookingDto(
                "Taliw", "taliw78@gmail.com",
                "0640230334", "Neon", "Organize Event",
                HelpersUtil.dateTimeFormatGeneral(startDate),
                HelpersUtil.dateTimeFormatGeneral(endDate), true
        );
        var bookingRes = new Booking(
                "1234", "Pahn", "pahn78@gmail.com",
                "0640230334", "Neon", "Organize Event",
                startDate, endDate, true,
                LocalDateTime.now(), LocalDateTime.now()
        );
        when(bookingRepository.findById(bookingReqId)).thenReturn(Optional.of(bookingRes));
        when(bookingMapper.toBooking(bookingReq)).thenReturn(bookingRes);
        when(bookingRepository.save(any(Booking.class))).thenReturn(bookingRes);
        // Act
        var addBooking = bookingService.updateBooking(bookingReqId, bookingReq);
        // Assert
        assertThat(addBooking.getId()).isEqualTo("1234");
        assertThat(addBooking.getStatus()).isEqualTo(true);
        verify(bookingRepository).save(any(Booking.class));
    }
}
