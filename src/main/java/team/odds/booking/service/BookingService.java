package team.odds.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.odds.booking.model.Booking;
import team.odds.booking.repository.BookingRepository;


@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(Booking dataRequest) throws Exception {
        try{
            return bookingRepository.save(dataRequest);
        } catch (Exception e) {
            throw new Exception("Error while creating booking", e);
        }
    }

    public Booking getBookingById(String bookingId) throws Exception {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new Exception("Error while retrieving Booking"));
    }
}
