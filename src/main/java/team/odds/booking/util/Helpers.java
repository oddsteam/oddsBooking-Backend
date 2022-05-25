package team.odds.booking.util;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class Helpers {
    public boolean checkBookingDateExpired(LocalDateTime bookingDate) {
        var duration = Duration.between(bookingDate, LocalDateTime.now());
        var durationDays = duration.toDays();
        return durationDays >= 1;
    }
}
