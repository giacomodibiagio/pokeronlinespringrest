package com.projectpokerrest.pokerrest.web.api;

import com.projectpokerrest.pokerrest.model.User;

import com.projectpokerrest.pokerrest.security.service.UserService;
import com.projectpokerrest.pokerrest.web.api.exception.UnouthorizedException;
import com.projectpokerrest.pokerrest.web.api.exception.UtenteNotFoundException;
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
    private UserService utenteService;



    @GetMapping("/all")
    public ResponseEntity<List<User>> getAll (@RequestHeader("authorization") String user) {



        List<User> utenti = utenteService.listAllEager();
        return new ResponseEntity<>(utenti, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> get (@PathVariable("id") Long id, @RequestHeader("authorization") String user) {



        User utente = utenteService.caricaUtenteEager(id);
        return new ResponseEntity<>(utente, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> add (@Valid @RequestBody User utente, @RequestHeader("authorization") String user) {



        User userInstance = utenteService.inserisciNuovo(utente);
        return new ResponseEntity<>(userInstance, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> update(@Valid @RequestBody User utente, @RequestHeader("authorization") String user, @PathVariable("id") Long id) {


        User userDaModificare = utenteService.caricaUtenteEager(id);
        if (userDaModificare == null)
            throw new UtenteNotFoundException("Utente non trovato");
        utente.setId(id);
        utenteService.aggiorna(utente);
        User userModificato = utente;
        return new ResponseEntity<>(userModificato, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id, @RequestHeader("authorization") String user) throws Exception {



        User utente = utenteService.disabilitaUtente(id);
        utenteService.aggiorna(utente);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<User>> search(@RequestBody User example, @RequestHeader("authorization") String user) {

        List<User> userInstance = utenteService.findByExample(example);
        return new ResponseEntity<>(userInstance, HttpStatus.OK);
    }

}
