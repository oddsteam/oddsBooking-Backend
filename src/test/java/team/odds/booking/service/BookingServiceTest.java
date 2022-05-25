package team.odds.booking.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import team.odds.booking.model.Booking;
import team.odds.booking.repository.BookingRepository;
import team.odds.booking.util.Helpers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class BookingServiceTest {
    BookingRepository bookingRepository = mock(BookingRepository.class);
    Helpers helpers = mock(Helpers.class);
    BookingService bookingService = new BookingService(bookingRepository, helpers);

    @Test
    void addBooking() throws Exception {
        // Arrange
        var dateTime = "2020-08-21T04:21";
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
        // Act
        var addBooking = bookingService.addBooking(bookingRes);
        // Assert
        assertEquals("1234", addBooking.getId());
        assertEquals("Milk", addBooking.getFullName());
        assertEquals("milkeyway124@gmail.com", addBooking.getEmail());
        assertEquals("0640230334", addBooking.getPhoneNumber());
        assertEquals("Neon", addBooking.getRoom());
        assertEquals("Booking Event", addBooking.getReason());
        assertEquals(LocalDateTime.parse(dateTime), addBooking.getStartDate());
        assertEquals(LocalDateTime.parse(dateTime), addBooking.getEndDate());
        assertEquals(false, addBooking.getStatus());
        assertEquals(LocalDateTime.parse(dateTime), addBooking.getCreatedAt());
        assertEquals(LocalDateTime.parse(dateTime), addBooking.getUpdatedAt());
        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    void getBooking() throws Exception {
        // Arrange
        String dateTime = "2022-05-25T22:04";
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
        var bookingId = bookingRes.getId();
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(bookingRes));
        when(helpers.checkBookingDateExpired(bookingRes.getCreatedAt())).thenReturn(Boolean.FALSE);
        // Act
        var getBooking = bookingService.getBooking(bookingId);
        // Assert
        assertEquals("1234", getBooking.getId());
        assertEquals("Milk", getBooking.getFullName());
        assertEquals("milkeyway124@gmail.com", getBooking.getEmail());
        assertEquals("0640230334", getBooking.getPhoneNumber());
        assertEquals("Neon", getBooking.getRoom());
        assertEquals("Booking Event", getBooking.getReason());
        assertEquals(LocalDateTime.parse(dateTime), getBooking.getStartDate());
        assertEquals(LocalDateTime.parse(dateTime), getBooking.getEndDate());
        assertEquals(false, getBooking.getStatus());
        assertEquals(LocalDateTime.parse(dateTime), getBooking.getCreatedAt());
        assertEquals(LocalDateTime.parse(dateTime), getBooking.getUpdatedAt());
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
        assertTrue(exception.getMessage().contains("Booking not found with this id : " + bookingId));
        verify(bookingRepository).findById(bookingId);
    }
}
