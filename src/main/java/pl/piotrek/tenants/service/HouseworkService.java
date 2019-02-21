package pl.piotrek.tenants.service;


import pl.piotrek.tenants.model.entity.Housework;
import pl.piotrek.tenants.model.entity.HouseworkRating;

import java.util.List;
import java.util.Set;

public interface HouseworkService {

    Housework getHousework(Long id);

    List<Housework> getHouseworksOf(Long houseId);

    Housework assignUserToHousework(Long houseworkId, Long userId);

    Housework finishHousework(Long houseworkId);

    HouseworkRating rateHousework(Long houseworkId, Long ratedById, HouseworkRating rating);

    Set<HouseworkRating> getRatingsForHouswork(Long houseworkId);
}
