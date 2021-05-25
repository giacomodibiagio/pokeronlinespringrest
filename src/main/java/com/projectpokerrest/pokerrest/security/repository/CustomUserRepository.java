package com.projectpokerrest.pokerrest.security.repository;

import com.projectpokerrest.pokerrest.model.User;

import java.util.List;

public interface CustomUserRepository {

     List<User> findByExample(User example);

     User disabilitaUtente(User user);
}
