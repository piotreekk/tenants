package pl.piotrek.tenants.service.impl;

import org.springframework.stereotype.Service;
import pl.piotrek.tenants.exception.ResourceNotFoundException;
import pl.piotrek.tenants.model.entity.Housework;
import pl.piotrek.tenants.model.entity.HouseworkRating;
import pl.piotrek.tenants.model.entity.User;
import pl.piotrek.tenants.repository.HouseworkRepository;
import pl.piotrek.tenants.repository.UserRepository;
import pl.piotrek.tenants.service.HouseworkService;
import pl.piotrek.tenants.util.HouseworkStatus;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class HouseworkServiceImpl implements HouseworkService {
    private HouseworkRepository houseworkRepository;
    private UserRepository userRepository;


    public HouseworkServiceImpl(HouseworkRepository houseworkRepository, UserRepository userRepository) {
        this.houseworkRepository = houseworkRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Housework getHousework(Long id) {
        return houseworkRepository.findById(id).get();
    }

    @Override
    public List<Housework> getHouseworksOf(Long houseId) {
        return houseworkRepository.findAllByHouseId(houseId);
    }

    @Override
    public Housework assignUserToHousework(Long houseworkId, Long userId) {
        Housework housework = houseworkRepository.findById(houseworkId).get();
        User user = userRepository.findById(userId).get();
        housework.addUserToHousework(user);
        housework.setStatus(HouseworkStatus.IN_PROGRESS);

        return houseworkRepository.save(housework);
    }

    @Override
    public Housework finishHousework(Long houseworkId) {
        Housework housework = houseworkRepository.findById(houseworkId).get();
        if(housework.getStatus() != HouseworkStatus.IN_PROGRESS)
            throw new RuntimeException("Can't finish housework not in progress!");

        housework.setStatus(HouseworkStatus.FINISHED);

        return houseworkRepository.save(housework);
    }

    @Transactional
    @Override
    public HouseworkRating rateHousework(Long houseworkId, Long ratedById, HouseworkRating rating) {
        Housework housework = houseworkRepository.findById(houseworkId)
                .orElseThrow(() -> new ResourceNotFoundException("Housework", "id", houseworkId));

        User user = userRepository.findById(ratedById)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", ratedById));

        rating.setUser(user);

        housework.addRateToHousework(rating);

        return rating;
    }

    @Transactional
    @Override
    public Set<HouseworkRating> getRatingsForHouswork(Long houseworkId) {
        Housework housework = houseworkRepository.findById(houseworkId)
                .orElseThrow(() -> new ResourceNotFoundException("Housework", "id", houseworkId));

        return housework.getRatings();
    }
}
