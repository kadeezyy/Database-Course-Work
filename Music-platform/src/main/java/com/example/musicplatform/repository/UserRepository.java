package com.example.musicplatform.repository;

import com.example.musicplatform.model.pojos.CustomUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    public Optional<CustomUser> findUserByUsername(String username){
        return null;
    }
}
