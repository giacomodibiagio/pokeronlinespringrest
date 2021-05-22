package com.projectpokerrest.pokerrest.web.api;

import com.projectpokerrest.pokerrest.model.Tavolo;
import com.projectpokerrest.pokerrest.model.Utente;
import com.projectpokerrest.pokerrest.service.utente.UtenteService;
import com.projectpokerrest.pokerrest.web.api.exception.UnouthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UtenteService utenteService;

    @GetMapping("/all")
    public ResponseEntity<List<Utente>> getAll (@RequestHeader("authorization") String user) {
        if(!user.equals("admin")){
            throw new UnouthorizedException("Non autorizzato");
        }
        List<Utente> utenti = utenteService.listAllEager();
        return new ResponseEntity<>(utenti, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Utente> get (@PathVariable("id") Long id, @RequestHeader("authorization") String user) {
        if(!user.equals("admin")){
            throw new UnouthorizedException("Non autorizzato");
        }
        Utente utente = utenteService.caricaUtenteEager(id);
        return new ResponseEntity<>(utente, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Utente> add (@Valid @RequestBody Utente utente, @RequestHeader("authorization") String user) {
        if(!user.equals("admin")){
            throw new UnouthorizedException("Non autorizzato");
        }
        Utente utenteInstance = utenteService.inserisciNuovo(utente);
        return new ResponseEntity<>(utenteInstance, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Utente> update(@Valid @RequestBody Utente utente, @RequestHeader("authorization") String user) {
        if(!user.equals("admin")){
            throw new UnouthorizedException("Non autorizzato");
        }
        Utente utenteInstance = utenteService.aggiorna(utente);
        return new ResponseEntity<>(utenteInstance, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id, @RequestHeader("authorization") String user) throws Exception {
        if(!user.equals("admin")){
            throw new UnouthorizedException("Non autorizzato");
        }
        Utente utente = utenteService.disabilitaUtente(id);
        utenteService.aggiorna(utente);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Utente>> search(@RequestBody Utente example, @RequestHeader("authorization") String user) {
        if(!user.equals("admin")){
            throw new UnouthorizedException("Non autorizzato");
        }
        List<Utente> utenteInstance = utenteService.findByExample(example);
        return new ResponseEntity<>(utenteInstance, HttpStatus.OK);
    }

}
