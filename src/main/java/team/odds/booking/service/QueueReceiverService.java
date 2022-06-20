package team.odds.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sendinblue.ApiException;
import team.odds.booking.repository.BookingRepository;

import javax.mail.MessagingException;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Service
public class QueueReceiverService {

    private final BookingRepository bookingRepository;
    private final MailSenderService mailSenderService;

    private final MailSendinblueService mailSendinblueService;

    @RabbitListener(queues = "odds-booking-message")
    public void receiveMessage(String message) throws IOException, MessagingException, IllegalAccessException {
        var bookingOpt = bookingRepository.findById(message);
        if (bookingOpt.isPresent()) {
            var booking = bookingOpt.get();
            var confirmUrl = "https://odds-booking.odds.team/detail/" + message;
            if (booking.getStatus())
                //mailSenderService.mailToOdds(confirmUrl, booking);
                mailSendinblueService.mailToOdds(booking);
            else
                //mailSenderService.mailToUser(confirmUrl, booking);
                mailSendinblueService.mailToUser(booking);
        } else {
            log.warn("Booking not found : message = {}", message);
        }
    }
}
