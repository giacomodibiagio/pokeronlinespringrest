package com.projectpokerrest.pokerrest.service.utente;

import com.projectpokerrest.pokerrest.model.StatoUtente;
import com.projectpokerrest.pokerrest.model.Tavolo;
import com.projectpokerrest.pokerrest.model.Utente;
import com.projectpokerrest.pokerrest.repository.tavolo.TavoloRepository;
import com.projectpokerrest.pokerrest.repository.utente.UtenteRepository;
import com.projectpokerrest.pokerrest.web.api.exception.TavoloNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UtenteServiceImpl implements UtenteService {

	@Autowired
	private UtenteRepository repository;
	@Autowired
	private TavoloRepository tavoloRepository;

	@Override
	public List<Utente> listAllUtenti() {
		return (List<Utente>) repository.findAll();
	}

	@Override
	public Utente caricaSingoloUtente(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Utente aggiorna(Utente utenteInstance) {
		repository.save(utenteInstance);
		return utenteInstance;
	}

	@Override
	public Utente inserisciNuovo(Utente utenteInstance) {
		return repository.save(utenteInstance);
	}

	@Override
	public void rimuovi(Utente utenteInstance) {
		repository.delete(utenteInstance);
	}

	@Override
	public List<Utente> findByExample(Utente example) {
		return repository.findByExample(example);
	}

	@Override
	public Utente findByUsernameAndPassword(String username, String password) {
		return repository.findByUsernameAndPassword(username, password);
	}

	@Override
	public Utente eseguiAccesso(String username, String password) {
		return repository.findByUsernameAndPasswordAndStato(username, password, StatoUtente.ATTIVO);
	}

	@Override
	public void invertUserAbilitation(Long utenteInstanceId) {
		Utente utenteInstance = caricaSingoloUtente(utenteInstanceId);
		if(utenteInstance == null)
			throw new RuntimeException("Elemento non trovato.");
		
		if(utenteInstance.getStato().equals(StatoUtente.ATTIVO))
			utenteInstance.setStato(StatoUtente.DISABILITATO);
		else if(utenteInstance.getStato().equals(StatoUtente.DISABILITATO) || utenteInstance.getStato().equals(StatoUtente.CREATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
	}

	@Override
	public Utente findByUsername(String username) {
		return repository.findByUsername(username).orElse(null);
	}

	@Override
	public void sottraiCredito(Utente utente, Double costoAnnuncio) {
		Double creditoResiduo = utente.getCreditoResiduo();
		creditoResiduo = creditoResiduo - costoAnnuncio;
		utente.setCreditoResiduo(creditoResiduo);
		repository.save(utente);
	}

	@Override
	public Utente caricaUtenteEager(Long id) {
		return repository.findOneEager(id).orElse(null);
	}

	@Override
	public List<Utente> listAllEager() {
		return repository.findAllEager();
	}

	@Override
	public Utente disabilitaUtente(Long id){
		Utente utente = caricaSingoloUtente(id);
		repository.disabilitaUtente(utente);
		return utente;
	}
	@Override
	public Tavolo trovaTavoloGiocatore( Utente utenteInSessione) {
		Tavolo tavolo = null;
		if ((tavolo = utenteInSessione.getTavolo()) == null) {
			throw  new TavoloNotFoundException("l utente non è su nessun tavolo");
		}
		return  tavolo;
	}

	@Override
	public List<Tavolo> cercaTavoliPerEsperienza(Utente utenteInSessione) {

		List<Tavolo> tavoli = tavoloRepository.findByEsperienzaMinLessThan(utenteInSessione.getEsperienzaAccumulata());
		return tavoli;
	}
}
