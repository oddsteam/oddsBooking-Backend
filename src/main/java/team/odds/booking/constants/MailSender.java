package team.odds.booking.constants;

import team.odds.booking.model.Booking;

public interface MailSender {
    boolean mailToUser(Booking booking);
    boolean mailToOdds(Booking booking);
}
