package com.capstone.caboodle.repositories;

import com.capstone.caboodle.models.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {
    List<Listing> findAllByProfile_id(Long profile_id);
}
