package com.capstone.caboodle.controllers;

import com.capstone.caboodle.models.Category;
import com.capstone.caboodle.models.Profile;
import com.capstone.caboodle.models.User;
import com.capstone.caboodle.repositories.CategoryRepository;
import com.capstone.caboodle.repositories.ListingRepository;
import com.capstone.caboodle.repositories.ProfileRepository;
import com.capstone.caboodle.security.services.UserDetailsImpl;
import com.capstone.caboodle.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

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

        User currentUser = userDetailsService.getCurrentUser();

        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        newProfile.setUser(currentUser);

        Profile profile = profileRepository.save(newProfile);

        return new ResponseEntity<>(profile, HttpStatus.CREATED);
    }

    @GetMapping("/self")
    public @ResponseBody Profile getSelf() {
        User currentUser = userDetailsService.getCurrentUser();

        if (currentUser == null) {
            return null;
        }

        return profileRepository.findByUser_Id(currentUser.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
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

    @PutMapping("/{userId}/addCategoryToUser/{categoryId}")
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

    @DeleteMapping("/{userId}/deleteCategoryFromUser/{categoryId}")
    public ResponseEntity<Profile> deleteCategoryFromUser(@PathVariable Long userId, @PathVariable Long categoryId) {
        Profile profile = profileRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        profile.getCategories().remove(category);

        profileRepository.save(profile);

        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PutMapping("/updateAge/{userId}/{userAge}")
    public ResponseEntity<Profile> updateUserAge(@PathVariable Long userId, @PathVariable Integer userAge) {
        Profile profile = profileRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        profile.setAge(userAge);
        profileRepository.save(profile);

        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long profileId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        profileRepository.deleteById(profileId);
        return new ResponseEntity<>(profile.getName() + " has been deleted", HttpStatus.OK);
    }

}
