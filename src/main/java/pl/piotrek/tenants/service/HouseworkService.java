package pl.piotrek.tenants.service;


import pl.piotrek.tenants.model.entity.Housework;
import pl.piotrek.tenants.model.entity.HouseworkRating;

import java.util.Collection;
import java.util.List;

public interface HouseworkService {

    Housework getHousework(Long id);

    List<Housework> getHouseworksOf(Long houseId);

    Collection<Housework> getUserHouseworks(Long id);

    Housework assignUserToHousework(Long houseworkId, Long userId);

    Housework finishHousework(Long houseworkId);

    HouseworkRating rateHousework(Long houseworkId, Long ratedById, HouseworkRating rating);

    Collection<HouseworkRating> getRatingsForHouswork(Long houseworkId);

    Housework addHousework(Housework housework);

    Housework addHousework(Housework housework, Long houseId);
}
