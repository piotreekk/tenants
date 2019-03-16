package pl.piotrek.tenants.api.dto;

import lombok.Data;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserList extends ResourceSupport {
    List<Resource<UserDTO>> users = new ArrayList<>();

    public void addUser(Resource<UserDTO> userDTOResource){
        users.add(userDTOResource);
    }

}
