package com.projectpokerrest.pokerrest.security.service;

import com.projectpokerrest.pokerrest.model.Authority;
import com.projectpokerrest.pokerrest.model.AuthorityName;
import com.projectpokerrest.pokerrest.security.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService{
    @Autowired
    private AuthorityRepository repository;

    @Override
    public List<Authority> listAll() {
        return (List<Authority>) repository.findAll();
    }

    @Override
    public Authority caricaSingoloElemento(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void aggiorna(Authority AuthorityInstance) {
        repository.save(AuthorityInstance);
    }

    @Override
    public void inserisciNuovo(Authority AuthorityInstance) {
        repository.save(AuthorityInstance);
    }

    @Override
    public void rimuovi(Authority AuthorityInstance) {
        repository.delete(AuthorityInstance);
    }

    @Override
    public Authority cercaPerNome(AuthorityName name) {
        return repository.findByName(name);
    }

}
