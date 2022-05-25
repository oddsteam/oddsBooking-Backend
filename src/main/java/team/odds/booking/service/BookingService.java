package team.odds.booking.service;

import org.springframework.stereotype.Service;
import team.odds.booking.model.Booking;
import team.odds.booking.repository.BookingRepository;
import team.odds.booking.util.Helpers;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class BookingService {

    BookingRepository bookingRepository;
    Helpers helpers;

    public BookingService(BookingRepository bookingRepository, Helpers helpers) {
        this.bookingRepository = bookingRepository;
        this.helpers = helpers;
    }

    public Booking getBooking(String bookingId) throws Exception {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new Exception("Booking not found with this id : " + bookingId));
//        mailSenderService.mailToUser("https://odds-booking.odds.team/booking_detail", booking);

        if (helpers.checkBookingDateExpired(booking.getCreatedAt())) {
            bookingRepository.deleteById(bookingId);
            throw new Exception("Booking is expired");
        }
        return booking;
    }

    public List<Booking> getBookingList() throws Exception {
        try {
            List<Booking> bookingList = bookingRepository.findAll();
            if (bookingList.isEmpty()) {
                throw new Exception("Booking not found in database");
            } else return bookingList;
        } catch (Exception e) {
            throw new Exception("Some error occurred while retrieving the Bookings", e);
        }
    }

    public Booking addBooking(Booking dataRequest) throws Exception {
        Booking booking = new Booking();
        booking.setFullName(dataRequest.getFullName());
        booking.setEmail(dataRequest.getEmail());
        booking.setPhoneNumber(dataRequest.getPhoneNumber());
        booking.setRoom(dataRequest.getRoom());
        booking.setReason(dataRequest.getReason());
        booking.setStartDate(dataRequest.getStartDate());
        booking.setEndDate(dataRequest.getEndDate());
        booking.setStatus(false);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());

        try {
            return bookingRepository.save(booking);
        } catch (Exception e) {
            throw new Exception("Some error occurred while creating booking", e);
        }
    }

    public Booking updateBooking(String bookingId, Booking dataRequest) throws Exception {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new Exception("Booking not found with this id : " + bookingId));

        booking.setFullName(dataRequest.getFullName());
        booking.setEmail(dataRequest.getEmail());
        booking.setPhoneNumber(dataRequest.getPhoneNumber());
        booking.setRoom(dataRequest.getRoom());
        booking.setReason(dataRequest.getReason());
        booking.setStartDate(dataRequest.getStartDate());
        booking.setEndDate(dataRequest.getEndDate());
        booking.setStatus(dataRequest.getStatus());
        booking.setCreatedAt(booking.getCreatedAt());
        booking.setUpdatedAt(LocalDateTime.now());

        try {
            return bookingRepository.save(booking);
        } catch (Exception e) {
            throw new Exception("Some error occurred while updating booking", e);
        }
    }
}
