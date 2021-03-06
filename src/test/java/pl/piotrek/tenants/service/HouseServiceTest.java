package pl.piotrek.tenants.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.piotrek.tenants.model.entity.House;
import pl.piotrek.tenants.repository.HouseRepository;
import pl.piotrek.tenants.service.impl.HouseServiceImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class HouseServiceTest {
    private static final Long ID = 1L;
    private static final String CITY = "Lublin";
    private static final String ADDRESS = "Akademicka 9";

    private HouseService houseService;

    @Mock
    private HouseRepository houseRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        houseService = new HouseServiceImpl(houseRepository);
    }

    @Test
    public void getAllHouses(){
        // given
        List<House> houseList = Arrays.asList(new House(), new  House(), new House());

        when(houseRepository.findAll()).thenReturn(houseList);
        // when
        Collection<House> houseListFromService = houseService.getAllHouses();

        // then
        assertEquals(3, houseListFromService.size());
    }

    @Test
    public void getHouseById(){
        // given
        House house = new House();
        house.setId(ID);
        house.setCity(CITY);
        house.setAddress(ADDRESS);

        // when
        when(houseRepository.findByAddress(anyString())).thenReturn(Optional.of(house));
        House houseFromService = houseService.getHouseByAddress(ADDRESS);

        // then
        assertEquals(ID, houseFromService.getId());
        assertEquals(CITY, houseFromService.getCity());
        assertEquals(ADDRESS, houseFromService.getAddress());

    }

}
