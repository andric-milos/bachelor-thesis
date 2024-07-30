package rs.ac.uns.ftn.bachelor_thesis.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerIntegrationTest {
    private final String BASE_URL = "/player";
    private final String GET_PLAYER_BY_EMAIL_URL = BASE_URL + "/email/";
    private final String GET_PLAYER_BY_ID_URL = BASE_URL + "/id/";

    private final String EXISTING_PLAYER_EMAIL = "johndoe@gmail.com";
    private final String NON_EXISTING_PLAYER_EMAIL = "nonExistingPlayer@email.com";
    private final Long EXISTING_PLAYER_ID = 1L;
    private final Long NON_EXISTING_PLAYER_ID = 1000L;

    private final MediaType CONTENT_TYPE_JSON = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype()
    );

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Nested
    @DisplayName("Method: getAllPlayers")
    class getAllPlayersTests {
        @Test
        void shouldReturn200() throws Exception {
            mockMvc.perform(get(BASE_URL)).andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("Method: getPlayerByEmail")
    class getPlayerByEmailTests {
        @Test
        void shouldReturn200WhenPlayerWithGivenEmailExists() throws Exception {
            mockMvc.perform(get(GET_PLAYER_BY_EMAIL_URL + EXISTING_PLAYER_EMAIL))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(CONTENT_TYPE_JSON));
        }

        @Test
        void shouldReturn404WhenPlayerWithGivenEmailDoesNotExist() throws Exception {
            mockMvc.perform(get(GET_PLAYER_BY_EMAIL_URL + NON_EXISTING_PLAYER_EMAIL))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Method: getPlayerById")
    class getPlayerByIdTests {
        @Test
        void shouldReturn200WhenPlayerWithGivenIdExists() throws Exception {
            mockMvc.perform(get(GET_PLAYER_BY_ID_URL + EXISTING_PLAYER_ID))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(CONTENT_TYPE_JSON));
        }

        @Test
        void shouldReturn404WhenPlayerWithGivenIdDoesNotExist() throws Exception {
            mockMvc.perform(get(GET_PLAYER_BY_ID_URL + NON_EXISTING_PLAYER_ID))
                    .andExpect(status().isNotFound());
        }
    }
}