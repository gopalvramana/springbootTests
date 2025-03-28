package com.discovery.spring.test;

import com.discovery.spring.test.controller.TutorialController;
import com.discovery.spring.test.model.Tutorial;
import com.discovery.spring.test.service.TutorialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TutorialController.class)
//This annotation load only required SpringMVC components without loading complete application context
public class TutorialControllerTest {

    @MockBean
    private TutorialService tutorialService;

    @Autowired
    private MockMvc mockMvc; //this annotation is used for simulating HTTP calls

    private Tutorial tutorial;

    @Autowired
    private ObjectMapper objectMapper; // Auto-configured by Spring Boot

    @BeforeEach
    public void setUp() {
        tutorial = new Tutorial(1L, "Spring Boot @WebMvcTest", "Description", true);
    }

    @Test
    public void shouldCreateTutorial() throws Exception {
        // Mock the service method to return a tutorial
        given(tutorialService.createTutorial(any(Tutorial.class))).willReturn(tutorial);

        // Perform the HTTP POST request and check the result
        mockMvc.perform(post("/api/tutorials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tutorial))) // Convert object to JSON
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Spring Boot @WebMvcTest"));

    }

    @Test
    public void getAllTutorials() throws Exception {
        //Mocked the method returning list of Tutorials.
        given(tutorialService.getAllTutorials()).willReturn(List.of(tutorial));

        // Perform the HTTP GET request and check the result
        mockMvc.perform(get("/api/tutorials"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Spring Boot @WebMvcTest"));
    }

    @Test
    public void getTutorialById() throws Exception {
        long id = 1L;
        given(tutorialService.getTutorialById(id)).willReturn(Optional.of(tutorial));

        //Perform the HTTP GET request and check the result
        mockMvc.perform(get("/api/tutorials/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Spring Boot @WebMvcTest"));

    }



}
