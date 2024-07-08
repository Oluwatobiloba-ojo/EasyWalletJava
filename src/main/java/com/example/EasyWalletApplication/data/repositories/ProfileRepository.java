package com.example.EasyWalletApplication.data.repositories;

import com.example.EasyWalletApplication.data.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findProfileByEmailOrPhoneNumber(String email, String phoneNumber);
}
