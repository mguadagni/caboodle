package com.capstone.caboodle.repositories;

import com.capstone.caboodle.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
