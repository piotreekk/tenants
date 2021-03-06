package pl.piotrek.tenants.service.impl;

import org.springframework.stereotype.Service;
import pl.piotrek.tenants.exception.AppException;
import pl.piotrek.tenants.exception.ResourceNotFoundException;
import pl.piotrek.tenants.model.HouseworkStatus;
import pl.piotrek.tenants.model.entity.House;
import pl.piotrek.tenants.model.entity.Housework;
import pl.piotrek.tenants.model.entity.HouseworkRating;
import pl.piotrek.tenants.model.entity.User;
import pl.piotrek.tenants.repository.HouseRepository;
import pl.piotrek.tenants.repository.HouseworkRepository;
import pl.piotrek.tenants.repository.UserRepository;
import pl.piotrek.tenants.service.HouseworkService;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Set;

@Service
public class HouseworkServiceImpl implements HouseworkService {
    private HouseworkRepository houseworkRepository;
    private UserRepository userRepository;
    private HouseRepository houseRepository;

    public HouseworkServiceImpl(HouseworkRepository houseworkRepository, UserRepository userRepository, HouseRepository houseRepository) {
        this.houseworkRepository = houseworkRepository;
        this.userRepository = userRepository;
        this.houseRepository = houseRepository;
    }

    @Override
    public Housework getHousework(Long id) {
        return houseworkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Housework", "id", id));
    }

    @Override
    public Collection<Housework> getHouseworksOf(Long houseId) {
        return houseworkRepository.findAllByHouseId(houseId);
    }

    @Transactional
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
        Housework housework = houseworkRepository.findById(houseworkId)
                .orElseThrow(() -> new ResourceNotFoundException("Housework", "id", houseworkId));

        if(housework.getStatus() != HouseworkStatus.IN_PROGRESS)
            throw new AppException("Can't finish housework not in progress!");

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

    @Override
    public Double getAvgRatingForHousework(Long houseworkId) {
        return houseworkRepository.getRatingAvg(houseworkId);
    }

    @Transactional
    @Override
    public Collection<Housework> getUserHouseworks(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return user.getHouseworks();
    }

    @Override
    public Housework addHousework(Housework housework) {
        return houseworkRepository.save(housework);
    }

    @Override
    public Housework addHousework(Housework housework, Long houseId) {
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new ResourceNotFoundException("House", "id", houseId));

        housework.setStatus(HouseworkStatus.TO_DO);
        housework.setHouse(house);

        return houseworkRepository.save(housework);
    }

    @Override
    public Collection<User> getHouseworkUsers(Long houseworkId) {
        return houseworkRepository.findUsersAssignedToHousework(houseworkId);
    }
}
