package pl.piotrek.tenants.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import pl.piotrek.tenants.util.HouseworkStatus;

@Data
public class HouseworkDTO {
    @JsonIgnore
    private long id;
    private String name;
    private String description;
    private String date;
    @JsonIgnore
    private long houseId;
    private HouseworkStatus status;
}
