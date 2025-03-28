package com.discovery.spring.test.service;

import com.discovery.spring.test.Exception.ResourceNotFoundException;
import com.discovery.spring.test.model.Tutorial;
import com.discovery.spring.test.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TutorialService {

    @Autowired
    private TutorialRepository tutorialRepository;

    public Tutorial createTutorial(Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }

    public List<Tutorial> getAllTutorials() {
        return tutorialRepository.findAll();
    }

    public Optional<Tutorial> getTutorialById(Long id) {
        return tutorialRepository.findById(id);
    }

    public List<Tutorial> getPublishedTutorials() {
        return tutorialRepository.findByPublished(true);
    }

    public Tutorial updateTutorial(Long id, Tutorial tutorialDetails) {
        return tutorialRepository.findById(id).map(tutorial -> {
            tutorial.setTitle(tutorialDetails.getTitle());
            tutorial.setDescription(tutorialDetails.getDescription());
            tutorial.setPublished(tutorialDetails.isPublished());
            return tutorialRepository.save(tutorial);
        }).orElseThrow(() -> new ResourceNotFoundException("Tutorial not found with id " + id));
    }

    public void deleteTutorial(Long id) {
        tutorialRepository.deleteById(id);
    }

    public void deleteAllTutorials() {
        tutorialRepository.deleteAll();
    }
}

