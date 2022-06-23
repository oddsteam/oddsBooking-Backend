package team.odds.booking.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HelpersUtil {
    public static boolean checkBookingDateExpired(LocalDateTime bookingDate) {
        Duration duration = Duration.between(bookingDate, LocalDateTime.now());
        long durationDays = duration.toDays();
        return durationDays >= 1;
    }

    public static String dateTimeFormatGeneral(LocalDateTime value){
        return value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
