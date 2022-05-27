package team.odds.booking.service;

import org.junit.jupiter.api.Test;
import team.odds.booking.model.Booking;
import team.odds.booking.model.dto.BookingDto;
import team.odds.booking.model.mapper.BookingMapper;
import team.odds.booking.repository.BookingRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class BookingServiceTest {
    BookingRepository bookingRepository = mock(BookingRepository.class);
    MailSenderService mailSenderService = mock(MailSenderService.class);
    BookingMapper bookingMapper = mock(BookingMapper.class);
    BookingService bookingService = new BookingService(bookingRepository, bookingMapper, mailSenderService);

    @Test
    void getBooking() {
        // Arrange
        var dateTime = LocalDateTime.now().toString();
        var booking = new Booking(
                "1234",
                "Milk",
                "milkeyway124@gmail.com",
                "0640230334",
                "Neon",
                "Booking Event",
                LocalDateTime.parse(dateTime),
                LocalDateTime.parse(dateTime),
                false,
                LocalDateTime.parse(dateTime),
                LocalDateTime.parse(dateTime)
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
        var exception = assertThrows(Exception.class, () -> bookingService.getBooking(bookingId));
        // Assert
        assertThat(exception.getMessage()).isEqualTo("Booking not found with this id : " + bookingId);
        verify(bookingRepository).findById(bookingId);
    }

    @Test
    void addBooking() throws Exception {
        // Arrange
        var dateTime = "2020-08-21T04:21";
        var bookingPostDto = new BookingDto(
                "Milk",
                "milkeyway124@gmail.com",
                "0640230334",
                "Neon",
                "Booking Event",
                dateTime,
                dateTime,
                false
        );
        var bookingRes = new Booking(
                "1234",
                "Milk",
                "milkeyway124@gmail.com",
                "0640230334",
                "Neon",
                "Booking Event",
                LocalDateTime.parse(dateTime),
                LocalDateTime.parse(dateTime),
                false,
                LocalDateTime.parse(dateTime),
                LocalDateTime.parse(dateTime)
        );
        when(bookingRepository.save(any(Booking.class))).thenReturn(bookingRes);
        when(bookingMapper.toBooking(any(BookingDto.class))).thenReturn(bookingRes);
        // Act
        var addBooking = bookingService.addBooking(bookingPostDto);
        // Assert
        assertThat(addBooking.getId()).isEqualTo("1234");
        assertThat(addBooking.getStatus()).isEqualTo(false);
        verify(bookingRepository).save(any(Booking.class));
    }
}
