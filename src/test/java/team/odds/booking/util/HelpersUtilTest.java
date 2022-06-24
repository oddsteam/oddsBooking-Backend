package team.odds.booking.util;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class HelpersUtilTest {

    @Test
    void bookingDateExpired() {
        try (var mockedStatic = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            // Arrange
            var bookingDate = LocalDateTime.of(2022, 8, 10, 8, 0);
            // Mock LocalDateTime.now()
            var dateTimeNow = LocalDateTime.of(2022, 8, 11, 8, 0);
            mockedStatic.when(LocalDateTime::now).thenReturn(dateTimeNow);
            // Act
            boolean isExpired = HelpersUtil.checkBookingDateExpired(bookingDate);
            // Assert
            assertThat(isExpired).isTrue();
        }
    }

    @Test
    void bookingDateNotExpired() {
        try (var mockedStatic = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            // Arrange
            var bookingDate = LocalDateTime.of(2022, 8, 10, 8, 0);
            // Mock LocalDateTime.now()
            var dateTimeNow = LocalDateTime.of(2022, 8, 10, 8, 0);
            mockedStatic.when(LocalDateTime::now).thenReturn(dateTimeNow);
            // Act
            boolean isExpired = HelpersUtil.checkBookingDateExpired(bookingDate);
            // Assert
            assertThat(isExpired).isFalse();
        }
    }

    @Test
    void bookingDateGeneralFormat() {
        // Arrange
        var bookingDate = LocalDateTime.of(2022, 8, 10, 8, 0);
        var bookingDateRes = "2022-08-10 08:00";
        // Act
        String bookingDateFormat = HelpersUtil.dateTimeFormatGeneral(bookingDate);
        // Assert
        assert (bookingDateFormat).equals(bookingDateRes);
    }
}
