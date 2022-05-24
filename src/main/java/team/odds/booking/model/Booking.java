package team.odds.booking.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "booking")
public class Booking {
    
    @Id
    public String id;
    public String name;
    public String email;
    public String phoneNumber;
    public String room;
    public String reason;

    private String startDate;

    private String endDate;
    public Boolean status;

    public LocalDateTime createAt;

    public LocalDateTime updateAt;

    @Override
    public String toString() {
        return "Booking {" +
                "id=" + id +
                ", name='" + name +
                ", email='" + email +
                ", phoneNumber='" + phoneNumber +
                ", room=" + room +
                ", reason='" + reason +
                ", startDate='" + startDate +
                ", endDate='" + endDate +
                ", status='" + status +
                ", createAt" + createAt +
                ", updateAt" + updateAt +
                '}';
    }
}
