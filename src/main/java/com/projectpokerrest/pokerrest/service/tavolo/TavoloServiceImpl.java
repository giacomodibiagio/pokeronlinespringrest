package com.projectpokerrest.pokerrest.service.tavolo;

import com.projectpokerrest.pokerrest.model.Tavolo;
import com.projectpokerrest.pokerrest.model.Utente;
import com.projectpokerrest.pokerrest.repository.tavolo.TavoloRepository;
import com.projectpokerrest.pokerrest.repository.utente.UtenteRepository;
import com.projectpokerrest.pokerrest.web.api.exception.TavoloNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TavoloServiceImpl implements TavoloService {

    @Autowired
    private TavoloRepository repository;


    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    public List<Tavolo> listAllTavolo() {
        return (List<Tavolo>) repository.findAll();
    }

    @Override
    public List<Tavolo> listAllEager() {
        return repository.findAllEager();
    }

    @Override
    public Tavolo caricaSingoloTavolo(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Tavolo caricaSingoloTavoloConUtenti(Long id) {
        return repository.findOneEager(id);
    }

    @Override
    public Tavolo aggiorna(Tavolo tavoloInstance) {
        if(tavoloInstance == null){
            throw new TavoloNotFoundException("Tavolo non trovato");
        }
        return repository.save(tavoloInstance);
    }

    @Override
    public Tavolo inserisciNuovo(Tavolo tavoloInstance) {
        return repository.save(tavoloInstance);
    }

    @Override
    public void rimuovi(Tavolo tavoloInstance) throws Exception {
        if(tavoloInstance == null){
            throw new TavoloNotFoundException("Tavolo non trovato");
        }
        if(!repository.findOneEager(tavoloInstance.getId()).getUtenti().isEmpty() && repository.findOneEager(tavoloInstance.getId()).getUtenteCreazione() != null){
            throw new Exception("Tavolo non eliminabile");
        }
        repository.delete(tavoloInstance);
    }

    @Override
    public List<Tavolo> findByExample(Tavolo tavoloInstance) {
        return repository.findByExample(tavoloInstance);
    }

    @Override
    public List<Tavolo> findByUtenteCreazione(Utente utente) {
        return repository.findByUtenteCreazione(utente);
    }

    @Override
    public Tavolo controllaTavoloPerUsernameUtenteCreazione(Long id, String username) {
        Tavolo tavolo = repository.findOneEager(id);
        if(tavolo.getUtenteCreazione().getUsername().equals(username)){
            return tavolo;
        }
        return null;
    }


}
