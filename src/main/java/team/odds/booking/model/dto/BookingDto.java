package team.odds.booking.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingDto {

    private String fullName;
    private String email;
    private String phoneNumber;
    private String room;
    private String reason;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private String startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private String endDate;

    private Boolean status;

}
