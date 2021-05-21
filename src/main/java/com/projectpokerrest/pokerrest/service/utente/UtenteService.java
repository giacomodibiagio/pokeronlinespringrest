package com.projectpokerrest.pokerrest.service.utente;

import com.projectpokerrest.pokerrest.model.Utente;

import java.util.List;

public interface UtenteService {

	public List<Utente> listAllUtenti() ;

	public Utente caricaSingoloUtente(Long id);
	
	public Utente caricaUtenteConRuoli(Long id);

	public void aggiorna(Utente utenteInstance);

	public void inserisciNuovo(Utente utenteInstance);

	public void rimuovi(Utente utenteInstance);

	public List<Utente> findByExample(Utente example);
	
	public Utente findByUsernameAndPassword(String username, String password);
	
	public Utente eseguiAccesso(String username, String password);
	
	public void invertUserAbilitation(Long utenteInstanceId);
	
	public Utente findByUsername(String username);

	public void sottraiCredito(Utente utente, Double costoAnnuncio);
	
}
