package com.projectpokerrest.pokerrest.security.service;

import com.projectpokerrest.pokerrest.model.StatoUtente;
import com.projectpokerrest.pokerrest.model.Tavolo;
import com.projectpokerrest.pokerrest.model.User;
import com.projectpokerrest.pokerrest.repository.tavolo.TavoloRepository;
import com.projectpokerrest.pokerrest.security.jwt.dto.JwtUserDetailsImpl;
import com.projectpokerrest.pokerrest.security.repository.UserRepository;
import com.projectpokerrest.pokerrest.web.api.exception.TavoloNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private TavoloRepository tavoloRepository;

    @Override
    public List<User> listAllUtenti() {
        return (List<User>) repository.findAll();
    }

    @Override
    public User caricaSingoloUser(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User aggiorna(User utenteInstance) {
        repository.save(utenteInstance);
        return utenteInstance;
    }

    @Override
    public User inserisciNuovo(User utenteInstance) {
        return repository.save(utenteInstance);
    }

    @Override
    public void rimuovi(User utenteInstance) {
        repository.delete(utenteInstance);
    }

    @Override
    public List<User> findByExample(User example) {
        return repository.findByExample(example);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return repository.findByUsernameAndPassword(username, password);
    }

    @Override
    public User eseguiAccesso(String username, String password) {
        return repository.findByUsernameAndPasswordAndStato(username, password, StatoUtente.ATTIVO);
    }

    @Override
    public void invertUserAbilitation(Long utenteInstanceId) {
        User utenteInstance = caricaSingoloUser(utenteInstanceId);
        if(utenteInstance == null)
            throw new RuntimeException("Elemento non trovato.");

        if(utenteInstance.getStato().equals(StatoUtente.ATTIVO))
            utenteInstance.setStato(StatoUtente.DISABILITATO);
        else if(utenteInstance.getStato().equals(StatoUtente.DISABILITATO) || utenteInstance.getStato().equals(StatoUtente.CREATO))
            utenteInstance.setStato(StatoUtente.ATTIVO);
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    @Override
    public void sottraiCredito(User utente, Double costoAnnuncio) {
        Double creditoResiduo = utente.getCreditoAccumulato();
        creditoResiduo = creditoResiduo - costoAnnuncio;
        utente.setCreditoAccumulato(creditoResiduo);
        repository.save(utente);
    }

    @Override
    public User caricaUserEager(Long id) {
        return repository.findOneEager(id).orElse(null);
    }

    @Override
    public List<User> listAllEager() {
        return repository.findAllEager();
    }

    @Override
    public User disabilitaUser(Long id){
        User utente = caricaSingoloUser(id);
        repository.disabilitaUtente(utente);
        return utente;
    }
    @Override
    public Tavolo trovaTavoloGiocatore(User utenteInSessione) {
        Tavolo tavolo = null;
        if ((tavolo = utenteInSessione.getTavolo()) == null) {
            throw  new TavoloNotFoundException("l utente non è su nessun tavolo");
        }
        return  tavolo;
    }

    @Override
    public List<Tavolo> cercaTavoliPerEsperienza(User utenteInSessione) {

        List<Tavolo> tavoli = tavoloRepository.findByEsperienzaMinLessThan(utenteInSessione.getEsperienzaAccumulata());
        return tavoli;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return JwtUserDetailsImpl.build(user);
    }
}
