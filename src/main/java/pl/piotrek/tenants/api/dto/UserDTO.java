package pl.piotrek.tenants.api.dto;

import lombok.Data;

@Data
public class UserDTO {
    private long id;
    private String firstName;
    private String lastName;
}
