package pl.piotrek.tenants.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.piotrek.tenants.api.dto.LoginForm;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class SecurityTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    JwtAuthenticationFilter authenticationFilter;

    MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(authenticationFilter)
                .build();
    }

    @Test
    public void shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/house/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void requestWithRightCredentialsShouldReturnOk() throws Exception {
        String token = obtainAccessToken("piotrek@wp.pl", "password");

        mockMvc.perform(
                    get("/api/user/me")
                        .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk());

    }

    private String obtainAccessToken(String username, String password) throws Exception {
        LoginForm loginForm = new LoginForm();
        loginForm.setEmail(username);
        loginForm.setPassword(password);

        String json = getJsonFromObject(loginForm);

        ResultActions result
                = mockMvc.perform(
                        post("/auth/signin")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                        )
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();
        return  resultString;
    }


    private String getJsonFromObject(Object object){
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

}
