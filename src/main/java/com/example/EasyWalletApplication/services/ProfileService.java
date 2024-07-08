package com.example.EasyWalletApplication.services;

import com.example.EasyWalletApplication.data.models.Profile;
import com.example.EasyWalletApplication.dto.request.CreateProfileRequest;
import com.example.EasyWalletApplication.exceptions.AccountAlreadyExist;

public interface ProfileService {
    Profile createProfile(CreateProfileRequest request) throws AccountAlreadyExist;
}
