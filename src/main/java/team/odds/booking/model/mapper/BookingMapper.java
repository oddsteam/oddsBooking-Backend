package team.odds.booking.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import team.odds.booking.model.Booking;
import team.odds.booking.model.dto.BookingDto;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {
    Booking toBooking(BookingDto bookingDto);
}
