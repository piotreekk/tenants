package pl.piotrek.tenants.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class HouseworkRatingDTO {
    @JsonIgnore
    private Long id;
    private String comment;
    private Integer rate;
    @JsonIgnore
    private Long houseworkId;
}
