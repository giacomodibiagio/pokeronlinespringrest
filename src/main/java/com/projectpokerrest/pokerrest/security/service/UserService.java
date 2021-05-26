package com.projectpokerrest.pokerrest.security.service;

import com.projectpokerrest.pokerrest.model.Tavolo;
import com.projectpokerrest.pokerrest.model.User;
import com.projectpokerrest.pokerrest.security.jwt.dto.JwtUserDetailsImpl;
import com.projectpokerrest.pokerrest.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

	public List<User> listAllEager();

	public List<User> listAllUtenti();

	public User caricaSingoloUser(Long id);

	public User caricaUserEager(Long id);

	public User aggiorna(User UserInstance);

	public User inserisciNuovo(User UserInstance);

	public void rimuovi(User UserInstance);

	public List<User> findByExample(User example);

	public User findByUsernameAndPassword(String username, String password);

	public User eseguiAccesso(String username, String password);

	public void invertUserAbilitation(Long UserInstanceId);

	public User findByUsername(String username);

	public void sottraiCredito(User User, Double costoAnnuncio);

	public User disabilitaUser(Long id);

	Tavolo trovaTavoloGiocatore(User UserInSessione);

	List<Tavolo> cercaTavoliPerEsperienza(User UserInSessione);

}
