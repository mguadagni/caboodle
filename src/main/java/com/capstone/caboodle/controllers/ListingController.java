package com.capstone.caboodle.controllers;

import CSVreader.CSVReader2;
import com.capstone.caboodle.models.Category;
import com.capstone.caboodle.models.Listing;
import com.capstone.caboodle.models.Profile;
import com.capstone.caboodle.repositories.CategoryRepository;
import com.capstone.caboodle.repositories.ListingRepository;
import com.capstone.caboodle.repositories.ProfileRepository;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/listings")
public class ListingController {

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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
    public ResponseEntity<List<Listing>> getListingsByProfile(@PathVariable Long profileId) {
        List<Listing> listings = listingRepository.findAllByProfile_id(profileId);

        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @GetMapping("/categoryId/{categoryId}")
    public ResponseEntity<List<Listing>> getListingsByCategoryId(@PathVariable Long categoryId) {
        List<Listing> listings = listingRepository.findAllByCategory_id(categoryId);

        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @GetMapping("/categoryName/{category}")
    public ResponseEntity<List<Listing>> getListingsByCategoryName(@PathVariable String category) {
        List<Listing> listings = listingRepository.findAllByCategory_category(category);

        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @GetMapping("/getListingGTE/{price}")
    public ResponseEntity<List<Listing>> getListingsGTE(@PathVariable int price) {
        List<Listing> listings = listingRepository.findByPriceGreaterThanEqual(price);

        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @GetMapping("/getListingLTE/{price}")
    public ResponseEntity<List<Listing>> getListingsLTE(@PathVariable int price) {
        List<Listing> listings = listingRepository.findByPriceLessThanEqual(price);

        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    @PutMapping("/{listingId}/addCategoryToListing/{categoryId}")
    public ResponseEntity<Listing> addCategoryToListing(@PathVariable Long listingId, @PathVariable Long categoryId) {
        Listing listing = listingRepository.findById(listingId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        listing.setCategory(category);

        listingRepository.save(listing);

        return new ResponseEntity<>(listing, HttpStatus.OK);
    }

    //Not sure if I want users to be able to add a profile to the listing
    //A profile should be required to upload listing
    @PutMapping("/{listingId}/addProfileToListing/{profileId}")
    public ResponseEntity<Listing> addProfileToListing(@PathVariable Long listingId, @PathVariable Long profileId) {
        Listing listing = listingRepository.findById(listingId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        Profile profile = profileRepository.findById(profileId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        listing.setProfile(profile);

        listingRepository.save(listing);

        return new ResponseEntity<>(listing, HttpStatus.OK);
    }

    @PutMapping("/updatePrice/{listingId}/{listingPrice}")
    public ResponseEntity<Listing> updateListingPricePath(@PathVariable Long listingId, @PathVariable int listingPrice) {
        Listing listing = listingRepository.findById(listingId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        listing.setPrice(listingPrice);
        listingRepository.save(listing);

        return new ResponseEntity<>(listing, HttpStatus.OK);
    }

    @DeleteMapping("/{listingId}")
    public ResponseEntity<?> deleteListing(@PathVariable Long listingId) {
        Listing listing = listingRepository.findById(listingId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        listingRepository.deleteById(listingId);
        return new ResponseEntity<>(listing.getItem() + " Listing has been deleted", HttpStatus.OK);
    }

//    @PostMapping("/uploadDataSet")
//    public ResponseEntity<?> getDataSet() {
//        try {
//            Listing listing = listingRepository.save(CSVReader2.CSVToJson());
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);
//        }
//
//    }

}
