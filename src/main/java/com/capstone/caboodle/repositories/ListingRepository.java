package com.capstone.caboodle.repositories;

import com.capstone.caboodle.models.Category;
import com.capstone.caboodle.models.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findAllByProfile_id (Long profile_id);
    List<Listing> findAllByCategory_id (Long category_id);
    List<Listing> findByPriceGreaterThanEqual (int price);
    List<Listing> findByPriceLessThanEqual (int price);
    List<Listing> findAllByCategory_category (String category);
}
