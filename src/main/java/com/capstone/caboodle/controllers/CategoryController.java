package com.capstone.caboodle.controllers;

import com.capstone.caboodle.models.Category;
import com.capstone.caboodle.models.Listing;
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
import java.util.Optional;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ListingRepository listingRepository;

    @GetMapping("/test")
    public ResponseEntity<?> testRoute() {
        return new ResponseEntity<>("TEST", HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createCategory(@RequestBody Category newCategory) {
        Category category = categoryRepository.save(newCategory);

        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

//    @PostMapping("/{profileId}")
//    public ResponseEntity<?> addCategoryToProfile(@PathVariable Long profileId, @RequestBody Category newCategory) {
//        Profile profile = profileRepository.findById(profileId).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
//        );
//
//        newCategory.getProfiles().add(profile);
//
//        Category category = categoryRepository.save(newCategory);
//        return new ResponseEntity<>(category, HttpStatus.CREATED);
//    }

    @GetMapping("/")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getOneCategory(@PathVariable Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteOneCategory(@PathVariable Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        categoryRepository.deleteById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
