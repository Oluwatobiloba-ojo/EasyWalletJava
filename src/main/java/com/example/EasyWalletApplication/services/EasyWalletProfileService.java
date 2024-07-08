package com.example.EasyWalletApplication.services;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.EasyWalletApplication.data.repositories.ProfileRepository;
import com.example.EasyWalletApplication.dto.request.CreateProfileRequest;
import com.example.EasyWalletApplication.data.models.Profile;
import com.example.EasyWalletApplication.exceptions.AccountAlreadyExist;



import java.nio.channels.AcceptPendingException;

import static com.example.EasyWalletApplication.util.ApiUtil.ACCOUNT_EXIST;

@Service
public class EasyWalletProfileService implements ProfileService{
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ProfileRepository repository;
    @Override
    public Profile createProfile(CreateProfileRequest request) throws AccountAlreadyExist {
        if (profileExist(request.getEmail(), request.getPhoneNumber())) throw new AccountAlreadyExist(ACCOUNT_EXIST);
        Profile profile = mapper.map(request, Profile.class);
        return repository.save(profile);
    }

    private boolean profileExist(String email, String phoneNumber) {
        return repository.findProfileByEmailOrPhoneNumber(email, phoneNumber).isPresent();
    }
}
