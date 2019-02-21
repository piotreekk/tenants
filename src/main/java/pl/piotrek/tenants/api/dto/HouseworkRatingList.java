package pl.piotrek.tenants.api.dto;

import lombok.Data;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

@Data
public class HouseworkRatingList extends ResourceSupport {
    private List<Resource<HouseworkRatingDTO>> ratings;

    public HouseworkRatingList() {
        ratings = new ArrayList<>();
    }

    public void addRating(Resource<HouseworkRatingDTO> rating){
        ratings.add(rating);
    }
}
