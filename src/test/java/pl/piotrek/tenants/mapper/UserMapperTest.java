package pl.piotrek.tenants.mapper;


import org.junit.Test;
import pl.piotrek.tenants.api.UserDto;
import pl.piotrek.tenants.model.User;


import static org.junit.Assert.assertEquals;

public class UserMapperTest {
    private static final String FIRSTNAME = "Jan";
    private static final String LASTNAME = "Kowalski";
    private static final long ID = 1L;

    @Test
    public void userToUserDto(){
        // given
        User user = new User();
        user.setFirstName(FIRSTNAME);
        user.setLastName(LASTNAME);
        user.setId(ID);


        // when
        UserDto userDto = UserMapper.INSTANCE.userToUserDto(user);

        // then
        assertEquals(ID, userDto.getId());
        assertEquals(FIRSTNAME, userDto.getFirstName());
        assertEquals(LASTNAME, userDto.getLastName());
    }
}
