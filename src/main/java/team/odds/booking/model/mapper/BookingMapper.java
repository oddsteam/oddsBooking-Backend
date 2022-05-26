package team.odds.booking.model.mapper;

import org.mapstruct.Mapper;
import team.odds.booking.model.Booking;
import team.odds.booking.model.dto.BookingDto;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    Booking toBooking(BookingDto bookingDto);
}
