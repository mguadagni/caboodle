package com.capstone.caboodle.controllers;

import com.capstone.caboodle.models.Category;
import com.capstone.caboodle.models.Listing;
import com.capstone.caboodle.models.Profile;
import com.capstone.caboodle.repositories.ListingRepository;
import com.capstone.caboodle.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/listings")
public class ListingController {

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping("/test")
    public ResponseEntity<?> testRoute() {
        return new ResponseEntity<>("Test Route", HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createListing(@RequestBody Listing newListing) {
        Listing listing = listingRepository.save(newListing);

        return new ResponseEntity<>(listing, HttpStatus.CREATED);
    }

    @PostMapping("/{profileId}")
    public ResponseEntity<?> createListing(@PathVariable Long profileId, @RequestBody Listing newListing) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        newListing.setProfile(profile);

        Listing listing = listingRepository.save(newListing);
        return new ResponseEntity<>(listing, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Listing>> getAllListings() {
        List<Listing> listings = listingRepository.findAll();
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Listing> getOneListing(@PathVariable Long id) {
        Listing listing = listingRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        return new ResponseEntity<>(listing, HttpStatus.OK);
    }

    @GetMapping("/profile/{profileId}")
    public ResponseEntity<List<Listing>> getNotesByProfile(@PathVariable Long profileId) {
        List<Listing> listings = listingRepository.findAllByProfile_id(profileId);

        return new ResponseEntity<>(listings, HttpStatus.OK);
    }
}
