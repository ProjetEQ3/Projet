package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

;

@SpringJUnitConfig(classes = {UserController.class, UserService.class})
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    void Login_Valid() throws Exception {
//        Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPassword("Test1234");
        loginDTO.setEmail("janedoe@exemple.com");

        String content = (new ObjectMapper()).writeValueAsString(loginDTO);

//        Act
        mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    void Login_InvalidPassword() throws Exception {
//        Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPassword("qweq");
        loginDTO.setEmail("janedoe@exemple.com");

//        Act
        mockMvc.perform(MockMvcRequestBuilders.post("/user/login"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void Login_InvalidEmail() throws Exception {
//        Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPassword("qweq");
        loginDTO.setEmail("janedoe@exemple.com");

//        Act
        mockMvc.perform(MockMvcRequestBuilders.post("/user/login"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}

