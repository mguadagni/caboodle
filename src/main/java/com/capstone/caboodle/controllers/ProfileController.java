package com.capstone.caboodle.controllers;

import com.capstone.caboodle.models.Category;
import com.capstone.caboodle.models.Profile;
import com.capstone.caboodle.repositories.CategoryRepository;
import com.capstone.caboodle.repositories.ListingRepository;
import com.capstone.caboodle.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ListingRepository listingRepository;

    @GetMapping("/test")
    public ResponseEntity<?> testRoute() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createProfile(@RequestBody Profile newProfile){
        Profile profile = profileRepository.save(newProfile);

        return new ResponseEntity<>(profile, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Profile>> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getOneProfile(@PathVariable Long id){
        Profile profile = profileRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PutMapping("/{userId}/addCategory/{categoryId}")
    public ResponseEntity<Profile> addCategoryToUser(@PathVariable Long userId, @PathVariable Long categoryId) {
        Profile profile = profileRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        profile.getCategories().add(category);

        profileRepository.save(profile);

        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

}
