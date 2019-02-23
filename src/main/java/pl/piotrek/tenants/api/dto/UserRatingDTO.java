package pl.piotrek.tenants.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserRatingDTO {
    @JsonIgnore
    private Long userId;
    private Double rate;
}
