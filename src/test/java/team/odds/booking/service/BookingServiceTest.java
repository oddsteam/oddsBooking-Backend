package team.odds.booking.service;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import team.odds.booking.model.Booking;
import team.odds.booking.repository.BookingRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookingServiceTest {
    //indigration test
//    @Autowired
//    private BookingService bookingService;
//    @Test
//    void createBooking() throws Exception {
//        //var bookingService = new BookingService();
//        var dataRequest = new Booking();
//        var createdBooking = bookingService.createBooking(dataRequest);
//        System.out.println(createdBooking);
//    }

    @Test
    void createBooking() throws Exception {
        var bookingService = new BookingService();
        var bookingRepository = mock(BookingRepository.class);
        ReflectionTestUtils.setField(bookingService, "bookingRepository", bookingRepository);
        var bookingFromSave = new Booking();
        bookingFromSave.setId("1234");
        var dataRequest = new Booking();
        when(bookingRepository.save(dataRequest)).thenReturn(bookingFromSave);
        var createdBooking = bookingService.createBooking(dataRequest);
        System.out.println(createdBooking);
        assertThat(createdBooking.getId()).isEqualTo("1234");
    }

}
