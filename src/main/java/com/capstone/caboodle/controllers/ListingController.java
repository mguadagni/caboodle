package com.capstone.caboodle.controllers;

import CSVreader.CSVReader2;
import com.capstone.caboodle.models.Category;
import com.capstone.caboodle.models.Listing;
import com.capstone.caboodle.models.Profile;
import com.capstone.caboodle.models.User;
import com.capstone.caboodle.repositories.CategoryRepository;
import com.capstone.caboodle.repositories.ListingRepository;
import com.capstone.caboodle.repositories.ProfileRepository;
import com.capstone.caboodle.security.services.UserDetailsServiceImpl;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/test")
    public ResponseEntity<?> testRoute() {
        return new ResponseEntity<>("Test Route", HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createListing(/*@RequestBody*/ Listing newListing,
                                           @RequestParam("picture") MultipartFile multipartFile) throws IOException {

        User currentUser = userDetailsService.getCurrentUser();

        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Profile profile = profileRepository.findByUser_Id(currentUser.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        newListing.setProfile(profile);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        newListing.setPicture(fileName);

        Listing listing = listingRepository.save(newListing);

        String uploadDir = "/listing-pic/" + newListing.getId();

        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("Could not save uploaded file: " + fileName);
        }

        return new ResponseEntity<>(listing, HttpStatus.CREATED);
    }

//    @PostMapping("/{profileId}")
//    public ResponseEntity<?> createListing(@PathVariable Long profileId, @RequestBody Listing newListing) {
//        Profile profile = profileRepository.findById(profileId).orElseThrow(
//                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
//        );
//
//        newListing.setProfile(profile);
//
//        Listing listing = listingRepository.save(newListing);
//        return new ResponseEntity<>(listing, HttpStatus.CREATED);
//    }

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

    @PutMapping("updateDescription/{listingId}")
    public ResponseEntity<Listing> updateListingDescription(@PathVariable Long listingId, @RequestBody String description) {
        Listing listing = listingRepository.findById(listingId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        listing.setDescription(description);
        listingRepository.save(listing);

        return new ResponseEntity<>(listing, HttpStatus.OK);
    }

    @PutMapping("updatePicture/{listingId}")
    public ResponseEntity<Listing> updateListingPicture(@PathVariable Long listingId, @RequestBody String listingFile) {
        Listing listing = listingRepository.findById(listingId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        listing.setPicture(listingFile);
        listingRepository.save(listing);

        return new ResponseEntity<>(listing, HttpStatus.OK);
    }

//    @PostMapping("/uploadDataSet")
//    public ResponseEntity<?> getDataSet() {
//        try {
//            Listing listing = listingRepository.save(CSVReader2.CSVToJson());
//            return new ResponseEntity<>(listing, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND);
//        }
//
//    }

}
