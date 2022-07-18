package team.odds.booking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {

    @ApiModelProperty(notes = "status", required = true)
    @JsonProperty("status")
    private Integer status;

    @ApiModelProperty(notes = "data", required = true)
    @JsonProperty("data")
    private T data;
}
