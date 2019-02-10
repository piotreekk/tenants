package pl.piotrek.tenants.api.dto;

import lombok.Data;

@Data
public class HouseDTO {
    private long id;
    private String city;
    private String address;
}
