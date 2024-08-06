package rs.ac.uns.ftn.bachelor_thesis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import rs.ac.uns.ftn.bachelor_thesis.dto.AppointmentDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.LoginInfoDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.NewAppointmentDTO;
import rs.ac.uns.ftn.bachelor_thesis.dto.TokensDTO;
import rs.ac.uns.ftn.bachelor_thesis.model.Appointment;
import rs.ac.uns.ftn.bachelor_thesis.repository.AppointmentRepository;

import java.util.Optional;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentControllerIntegrationTest {
    private final String BASE_URL = "/appointment";

    private final MediaType CONTENT_TYPE_JSON = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype()
    );
    private final String BEARER_FORMAT_STRING = "Bearer %s";

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Nested
    @DisplayName("Method: createAppointment")
    class createAppointmentTests {
        @Test
        @Transactional
        @Rollback
//        @WithMockUser(username = "player@example.com", roles = { "PLAYER" }) // using @WithMockUser is an alternative to logging in with mockMvc
        void shouldReturn200AndSuccessfullyCreateANewAppointmentWhenRequestDtoIsValid() throws Exception {
            TokensDTO tokensDTO = loginAsPlayer();

            NewAppointmentDTO dto = buildValidNewAppointmentDtoObject();
            MvcResult result = mockMvc.perform(post(BASE_URL)
                            .contentType(CONTENT_TYPE_JSON)
                            .content(objectMapper.writeValueAsString(dto))
                            .header(AUTHORIZATION, format(BEARER_FORMAT_STRING, tokensDTO.getAccessToken())))
                    .andExpect(status().isOk())
                    .andReturn();

            AppointmentDTO appointmentDTO = objectMapper.readValue(result.getResponse().getContentAsString(), AppointmentDTO.class);
            Optional<Appointment> appointment = appointmentRepository.findById(appointmentDTO.getId());
            assertTrue(appointment.isPresent());
        }

        // TODO: add tests when request dto is invalid
        // TODO: add a test when authentication is unsuccessful
    }

    private NewAppointmentDTO buildValidNewAppointmentDtoObject() {
        NewAppointmentDTO dto = new NewAppointmentDTO();
        dto.setGroupId(1L);
        dto.setDate(1722727800000L); // Aug 03 2024 23:30
        dto.setPrivacy("private");
        dto.setAddress("whatever");
        dto.setCapacity(5);
        dto.setPrice(1500.00);

        return dto;
    }

    private TokensDTO loginAsPlayer() throws Exception {
        LoginInfoDTO loginInfoDTO = new LoginInfoDTO("andric8@gmail.com", "1234");
        String response = mockMvc.perform(post("/user/login")
                        .contentType(CONTENT_TYPE_JSON)
                        .content(objectMapper.writeValueAsString(loginInfoDTO)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(response, TokensDTO.class);
    }
}
