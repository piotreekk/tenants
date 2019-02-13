package pl.piotrek.tenants.api.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class HouseDTO {
    private long id;
    @NotNull
    private String city;
    @NotNull
    private String address;
}
