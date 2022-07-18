package team.odds.booking.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse<T> {

    @ApiModelProperty(notes = "status", required = true)
    @JsonProperty("status")
    private Integer status;

    @ApiModelProperty(notes = "error", required = true)
    @JsonProperty("error")
    private T error;
}
