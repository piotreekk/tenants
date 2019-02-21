package pl.piotrek.tenants.service.impl;

import org.springframework.stereotype.Service;
import pl.piotrek.tenants.exception.ResourceNotFoundException;
import pl.piotrek.tenants.model.entity.HouseworkRating;
import pl.piotrek.tenants.repository.HouseworkRatingRepository;
import pl.piotrek.tenants.service.HouseworkRatingService;

@Service
public class HouseworkRatingServiceImpl implements HouseworkRatingService {
    private HouseworkRatingRepository houseworkRatingRepository;

    public HouseworkRatingServiceImpl(HouseworkRatingRepository houseworkRatingRepository) {
        this.houseworkRatingRepository = houseworkRatingRepository;
    }

    @Override
    public HouseworkRating getRatingById(Long id) {
        return houseworkRatingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HouseworkRating", "id", id));
    }
}
