package com.projectpokerrest.pokerrest.repository.utente;

import com.projectpokerrest.pokerrest.model.User;

import java.util.List;

public interface CustomUtenteRepository {

	List<User> findByExample(User example);

	public User disabilitaUtente(User user);
	
}
