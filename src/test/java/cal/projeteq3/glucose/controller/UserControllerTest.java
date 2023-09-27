package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.service.StudentService;
import cal.projeteq3.glucose.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class, UserService.class, StudentService.class, EmployerService.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Autowired
    private UserController userController;
}

