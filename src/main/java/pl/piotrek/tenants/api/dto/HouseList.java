package pl.piotrek.tenants.api.dto;

import lombok.Data;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

@Data
public class HouseList extends ResourceSupport {
    List<Resource<HouseDTO>> houses = new ArrayList<>();
}
