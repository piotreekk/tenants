package pl.piotrek.tenants.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserDTO {
    @JsonIgnore
    private long id;
    private String firstName;
    private String lastName;
}
