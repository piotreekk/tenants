package pl.piotrek.tenants.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.piotrek.tenants.repository.UserRepository;
import pl.piotrek.tenants.service.impl.UserServiceImpl;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void userRateTest(){

    }
}
