package com.projectpokerrest.pokerrest.web.api;

import com.projectpokerrest.pokerrest.model.Utente;
import com.projectpokerrest.pokerrest.service.ruolo.RuoloService;
import com.projectpokerrest.pokerrest.service.utente.UtenteService;
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
    private UtenteService utenteService;

    @Autowired
    private RuoloService ruoloService;

    @GetMapping("/all")
    public ResponseEntity<List<Utente>> getAll (@RequestHeader("authorization") String user) {
        Utente utenteInSessione = utenteService.findByUsername(user);
        if(!utenteInSessione.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("ROLE_ADMIN", "ROLE_ADMIN"))){
            throw new UnouthorizedException("Non autorizzato");
        }
        List<Utente> utenti = utenteService.listAllEager();
        return new ResponseEntity<>(utenti, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Utente> get (@PathVariable("id") Long id, @RequestHeader("authorization") String user) {
        Utente utenteInSessione = utenteService.findByUsername(user);
        if(!utenteInSessione.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("ROLE_ADMIN", "ROLE_ADMIN"))){
            throw new UnouthorizedException("Non autorizzato");
        }
        Utente utenteDaCercare = utenteService.caricaUtenteEager(id);
        if (utenteDaCercare == null)
            throw new UtenteNotFoundException("Utente non trovato");

        Utente utente = utenteService.caricaUtenteEager(id);
        return new ResponseEntity<>(utente, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Utente> add (@Valid @RequestBody Utente utente, @RequestHeader("authorization") String user) {
        Utente utenteInSessione = utenteService.findByUsername(user);
        if(!utenteInSessione.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("ROLE_ADMIN", "ROLE_ADMIN"))){
            throw new UnouthorizedException("Non autorizzato");
        }
        Utente utenteInstance = utenteService.inserisciNuovo(utente);
        return new ResponseEntity<>(utenteInstance, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Utente> update(@Valid @RequestBody Utente utente, @RequestHeader("authorization") String user, @PathVariable("id") Long id) {
        Utente utenteInSessione = utenteService.findByUsername(user);
        if(!utenteInSessione.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("ROLE_ADMIN", "ROLE_ADMIN"))){
            throw new UnouthorizedException("Non autorizzato");
        }
        Utente utenteDaModificare = utenteService.caricaUtenteEager(id);
        if (utenteDaModificare == null)
            throw new UtenteNotFoundException("Utente non trovato");
        utente.setId(id);
        utenteService.aggiorna(utente);
        Utente utenteModificato = utente;
        return new ResponseEntity<>(utenteModificato, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id, @RequestHeader("authorization") String user) throws Exception {
        Utente utenteInSessione = utenteService.findByUsername(user);
        if(!utenteInSessione.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("ROLE_ADMIN", "ROLE_ADMIN"))){
            throw new UnouthorizedException("Non autorizzato");
        }
        Utente utenteDaCancellare = utenteService.caricaUtenteEager(id);
        if (utenteDaCancellare == null)
            throw new UtenteNotFoundException("Utente non trovato");
        Utente utente = utenteService.disabilitaUtente(id);
        utenteService.aggiorna(utente);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Utente>> search(@RequestBody Utente example, @RequestHeader("authorization") String user) {
        Utente utenteInSessione = utenteService.findByUsername(user);
        if(!utenteInSessione.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("ROLE_ADMIN", "ROLE_ADMIN"))){
            throw new UnouthorizedException("Non autorizzato");
        }
        List<Utente> utenteInstance = utenteService.findByExample(example);
        return new ResponseEntity<>(utenteInstance, HttpStatus.OK);
    }

}
