package com.discovery.spring.test;

import com.discovery.spring.test.model.Tutorial;
import com.discovery.spring.test.repository.TutorialRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TutorialRepositoryTests {



    @Autowired
    TutorialRepository tutorialRepository;

    @Test
    void shouldCreateTutorial() {
        Tutorial tutorial = new Tutorial(1, "Spring Boot @WebMvcTest", "Description", true);
        Tutorial savedTutorial = tutorialRepository.save(tutorial);
        Long id = 1L;

        Tutorial fetchedTutorial = tutorialRepository.findById(id).get();

        assertNotNull(savedTutorial);
        assertEquals(tutorial,fetchedTutorial);
    }

    @Test
    void shouldReturnTutorial() {
        //Data setup
        Tutorial tutorial = new Tutorial(1, "Spring Boot @WebMvcTest", "Description", true);
        long id = 1L;
        tutorialRepository.save(tutorial);
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
        Tutorial fetchedTutorial = tutorialData.get();

        assertNotNull(tutorialData.isPresent(), "Tutorial should be present");
        //checking for null
        assertNotNull(fetchedTutorial);
        //checking if the same entity is returned
        assertEquals(tutorial, fetchedTutorial);
    }


    @Test
    void saveAllTutorials(){

        Tutorial tutorial1 = new Tutorial(1, "Spring Boot @WebMvcTest-1", "Description1", true);
        Tutorial tutorial2 = new Tutorial(2, "Spring Boot @WebMvcTest-2", "Description2", true);
        Tutorial tutorial3 = new Tutorial(3, "Spring Boot @WebMvcTest-3", "Description3", true);
        Tutorial tutorial4 = new Tutorial(4, "Spring Boot @WebMvcTest-4", "Description4", true);
        var tutorials = List.of(tutorial1,tutorial2,tutorial3,tutorial4);
        var savedTutorials = tutorialRepository.saveAll(tutorials);
        var fetchedTutorials = tutorialRepository.findAll();

        assertNotNull(savedTutorials);
        assertNotNull(savedTutorials);

    }

    @Test
    void findByPublished(){
        Tutorial tutorial1 = new Tutorial(1, "Spring Boot @WebMvcTest-1", "Description1", true);
        Tutorial tutorial2 = new Tutorial(2, "Spring Boot @WebMvcTest-2", "Description2", false);
        Tutorial tutorial3 = new Tutorial(3, "Spring Boot @WebMvcTest-3", "Description3", true);
        Tutorial tutorial4 = new Tutorial(4, "Spring Boot @WebMvcTest-4", "Description4", false);
        var tutorials = List.of(tutorial1,tutorial2,tutorial3,tutorial4);
        var savedTutorials = tutorialRepository.saveAll(tutorials);
        var fetchedTutorials = tutorialRepository.findByPublished(true);


        assertNotNull(savedTutorials);
        assertEquals(fetchedTutorials.size(),2);




    }
}
