package pl.piotrek.tenants.api.dto;

import lombok.Data;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

@Data
public class InhabitantsList extends ResourceSupport {
    List<Resource<UserDTO>> inhabitants = new ArrayList<>();

    public void addInhabitant(Resource<UserDTO> inhabitant){
        inhabitants.add(inhabitant);
    }
}
