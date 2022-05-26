package team.odds.booking.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "booking")
public class Booking {

    @Id
    private String id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String room;
    private String reason;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    private Boolean status;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedAt;

}
