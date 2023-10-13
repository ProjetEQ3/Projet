package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.config.SecurityConfiguration;
import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.repository.UserRepository;
import cal.projeteq3.glucose.security.JwtAuthenticationEntryPoint;
import cal.projeteq3.glucose.security.JwtTokenProvider;
import cal.projeteq3.glucose.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

;import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = {UserController.class, UserService.class, CustomExceptionHandler.class,
        SecurityConfiguration.class, JwtTokenProvider.class, JwtAuthenticationEntryPoint.class})
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @Test
    void Login_Valid() throws Exception {
//        Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPassword("Test1234");
        loginDTO.setEmail("janedoe@exemple.com");

        when(userService.authenticateUser(loginDTO)).thenReturn("token");
        when(userRepository.findUserByCredentialsEmail("janedoe@exemple.com")).thenReturn(Optional.of(
                Student.builder()
                        .email("janedoe@exemple.com")
                        .password("Test1234")
                        .build()));

        String content = (new ObjectMapper()).writeValueAsString(loginDTO);

//        Act
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/login")
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

