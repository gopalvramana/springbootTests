package com.discovery.spring.test.controller;

import com.discovery.spring.test.model.Tutorial;
import com.discovery.spring.test.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tutorials")
public class TutorialController {

    @Autowired
    private TutorialService tutorialService;

    @PostMapping
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        return ResponseEntity.ok(tutorialService.createTutorial(tutorial));
    }

    @GetMapping
    public ResponseEntity<List<Tutorial>> getAllTutorials() {
        return ResponseEntity.ok(tutorialService.getAllTutorials());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable Long id) {
        Optional<Tutorial> tutorial = tutorialService.getTutorialById(id);
        return tutorial.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/published")
    public ResponseEntity<List<Tutorial>> getPublishedTutorials() {
        return ResponseEntity.ok(tutorialService.getPublishedTutorials());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable Long id, @RequestBody Tutorial tutorialDetails) {
            return ResponseEntity.ok(tutorialService.updateTutorial(id, tutorialDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTutorial(@PathVariable Long id) {
        tutorialService.deleteTutorial(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllTutorials() {
        tutorialService.deleteAllTutorials();
        return ResponseEntity.noContent().build();
    }
}

