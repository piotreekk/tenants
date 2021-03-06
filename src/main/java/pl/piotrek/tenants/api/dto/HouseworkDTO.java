package pl.piotrek.tenants.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import pl.piotrek.tenants.model.HouseworkStatus;

@Data
public class HouseworkDTO {
    @JsonIgnore
    private long id;
    private String name;
    private String description;
    private String scheduledDate;
    private String createdDate;
    private String updatedDate;
    private Double avgRate;
    @JsonIgnore
    private long houseId;
    private HouseworkStatus status;
}
