package team.odds.booking.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import team.odds.booking.model.Booking;
import team.odds.booking.service.QueueProducerService;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class QueueProducerAspect {
    private final QueueProducerService queueProducerService;

    @AfterReturning(pointcut = "execution(* team.odds.booking.service.BookingService.addBooking(..))", returning = "booking")
    public void mailToUser(Booking booking) {
        queueProducerService.sendMessage(booking.getId());
    }

    @AfterReturning(pointcut = "execution(* team.odds.booking.service.BookingService.updateBooking(..))", returning = "booking")
    public void mailToOdds(Booking booking) {
        queueProducerService.sendMessage(booking.getId());
    }

}
