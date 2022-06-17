package team.odds.booking;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"team.odds.booking.service",
        "team.odds.booking.controller",
        "team.odds.booking.model",
        "team.odds.booking.config",
        "team.odds.booking.util",
        "team.odds.booking.exception"})
@EnableRabbit
@SpringBootApplication
public class BookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingApplication.class, args);
    }

}
