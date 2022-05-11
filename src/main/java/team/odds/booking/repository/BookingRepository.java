package team.odds.booking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import team.odds.booking.model.Booking;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {

}
