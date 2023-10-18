package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.config.SecurityConfiguration;
import cal.projeteq3.glucose.dto.SemesterDTO;
import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.dto.user.UserDTO;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.user.Manager;
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

;import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private final String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaWNoZWxAbWljaGF1ZC5jb20iLCJpYXQiOjE2OTc1ODY0MzIsImV4cCI6MTY5NzY3MjgzMiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6Ik1BTkFHRVIifV19.DRv0ToANfs2zVAZJCBIGmJOWOyOKT4lI6bFJCual6n4";

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
                .andExpect(status().isAccepted());
    }


    @Test
    void Login_InvalidPassword() throws Exception {
//        Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPassword("qweq");
        loginDTO.setEmail("janedoe@exemple.com");

//        Act
        mockMvc.perform(MockMvcRequestBuilders.post("/user/login"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void Login_InvalidEmail() throws Exception {
//        Arrange
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPassword("qweq");
        loginDTO.setEmail("janedoe@exemple.com");

//        Act
        mockMvc.perform(MockMvcRequestBuilders.post("/user/login"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getMe_ValidUser() throws Exception {
        // Arrange
        Student student = Student.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("janedoe@exemple.com")
                .password("Test1234")
                .build();
        when(userService.getMe(token)).thenReturn(new UserDTO(student));
        when(userRepository.findUserByCredentialsEmail(anyString())).thenReturn(Optional.of(student));

        // Act and Assert
        mockMvc.perform(get("/user/me")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("janedoe@exemple.com"))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
        ;
    }

    @Test
    void getMe_InvalidUser() throws Exception {
        // Arrange
        when(userService.getMe(token)).thenReturn(null);

        // Act and Assert
        mockMvc.perform(get("/user/me")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetSemestersSuccess() throws Exception {
        when(userService.getSemesters()).thenReturn(List.of(
                new SemesterDTO(Semester.builder().session(Semester.Session.SUMMER).year(2021).build()),
                new SemesterDTO(Semester.builder().session(Semester.Session.FALL).year(2020).build())
        ));

        mockMvc.perform(get("/user/semesters"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
        ;
    }
}

