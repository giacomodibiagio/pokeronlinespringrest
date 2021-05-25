package com.projectpokerrest.pokerrest.security.service;

import com.projectpokerrest.pokerrest.model.Authority;
import com.projectpokerrest.pokerrest.model.AuthorityName;

import java.util.List;

public interface AuthorityService {
    public List<Authority> listAll() ;

    public Authority caricaSingoloElemento(Long id) ;

    public void aggiorna(Authority AuthorityInstance) ;

    public void inserisciNuovo(Authority AuthorityInstance) ;

    public void rimuovi(Authority AuthorityInstance) ;

    Authority cercaPerNome(AuthorityName name);
}
