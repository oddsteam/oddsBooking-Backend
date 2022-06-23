package team.odds.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import team.odds.booking.repository.BookingRepository;

import javax.mail.MessagingException;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Service
public class QueueReceiverService {

    private final BookingRepository bookingRepository;
    private final MailSenderService mailSenderService;
//    private final MailSendinblueService mailSendinblueService;

    @RabbitListener(queues = "odds-booking-message")
    public void receiveMessage(String message) throws IOException, MessagingException {
        var bookingOpt = bookingRepository.findById(message);
        if (bookingOpt.isPresent()) {
            var booking = bookingOpt.get();
            if (Boolean.TRUE.equals(booking.getStatus())) {
//                mailSendinblueService.mailToOdds(booking);
                mailSenderService.mailToOdds(booking);
            } else {
//                mailSendinblueService.mailToUser(booking);
                mailSenderService.mailToUser(booking);
            }
        } else {
            log.warn("Booking not found : message = {}", message);
        }
    }
}
