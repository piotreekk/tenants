package pl.piotrek.tenants.api.dto;

import lombok.Data;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

@Data
public class HouseworkList extends ResourceSupport {
    private List<Resource<HouseworkDTO>> houseworks = new ArrayList<>();

    public void addHousework(Resource<HouseworkDTO> housework){
        houseworks.add(housework);
    }
}
