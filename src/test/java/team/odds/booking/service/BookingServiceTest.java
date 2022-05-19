package team.odds.booking.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import team.odds.booking.model.Booking;
import team.odds.booking.repository.BookingRepository;

import java.util.Optional;

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

    BookingRepository bookingRepository = mock(BookingRepository.class);
    BookingService bookingService = new BookingService(bookingRepository);

//    @BeforeEach
//    void setUp() {
//        ReflectionTestUtils.setField(bookingService, "bookingRepository", bookingRepository);
//    }

    @Test
    void createBooking() throws Exception {
        // Arrange
        var bookingFromSave = new Booking();
        bookingFromSave.setId("1234");
        var dataRequest = new Booking();
        when(bookingRepository.save(dataRequest)).thenReturn(bookingFromSave);
        // Act
        var createdBooking = bookingService.createBooking(dataRequest);
        // Assert
        System.out.println(createdBooking);
        assertThat(createdBooking.getId()).isEqualTo("1234");
    }

    @Test
    void getBookingById() throws Exception {
        // Arrange
        var bookingFromFindById = new Booking();
        bookingFromFindById.setId("5678");
        String bookingId = bookingFromFindById.getId();
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(bookingFromFindById));
        // Act
        var findBooking = bookingService.getBookingById(bookingId);
        // Assert
        System.out.println(findBooking);
        assertThat(findBooking.getId()).isEqualTo("5678");
    }

    

}
