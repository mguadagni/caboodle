package com.capstone.caboodle.repositories;

import com.capstone.caboodle.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    List<Profile> findAllByAge(Integer age);
}
