package pl.piotrek.tenants.service;


import pl.piotrek.tenants.model.entity.Housework;

import java.util.List;

public interface HouseworkService {

    Housework getHousework(Long id);

    List<Housework> getHouseworksOf(Long houseId);

    Housework assignUserToHousework(Long houseworkId, Long userId);

    Housework finishHousework(Long houseworkId);
}
