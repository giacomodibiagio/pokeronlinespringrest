package com.projectpokerrest.pokerrest.web.api;

import com.projectpokerrest.pokerrest.model.Tavolo;
import com.projectpokerrest.pokerrest.model.Utente;
import com.projectpokerrest.pokerrest.service.ruolo.RuoloService;
import com.projectpokerrest.pokerrest.service.tavolo.TavoloService;
import com.projectpokerrest.pokerrest.service.utente.UtenteService;
import com.projectpokerrest.pokerrest.web.api.exception.TavoloNotFoundException;
import com.projectpokerrest.pokerrest.web.api.exception.UnouthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tavolo")
public class TavoloController {

    @Autowired
    private TavoloService tavoloService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private RuoloService ruoloService;

    @GetMapping("/all")
    public ResponseEntity<List<Tavolo>> getAll (@RequestHeader("authorization") String user) {
        Utente utenteInSessione = utenteService.findByUsername(user);
        if(!utenteInSessione.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("ROLE_ADMIN", "ROLE_ADMIN")) && !utenteInSessione.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("ROLE_SPECIAL_PLAYER", "ROLE_SPECIAL_PLAYER"))){
            throw new UnouthorizedException("Non autorizzato");
        }
        List<Tavolo> tavoli = tavoloService.listAllEager();
        return new ResponseEntity<>(tavoli, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Tavolo> get (@PathVariable("id") Long id, @RequestHeader("authorization") String user) {
        Utente utenteInSessione = utenteService.findByUsername(user);
        if(!utenteInSessione.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("ROLE_ADMIN", "ROLE_ADMIN")) && !utenteInSessione.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("ROLE_SPECIAL_PLAYER", "ROLE_SPECIAL_PLAYER"))){
            throw new UnouthorizedException("Non autorizzato");
        }
        Tavolo tavoloDaCercare = tavoloService.caricaSingoloTavolo(id);
        if(tavoloDaCercare == null){
            throw new TavoloNotFoundException("Tavolo non trovato");
        }

        Tavolo tavolo = tavoloService.caricaSingoloTavoloConUtenti(id);
        return new ResponseEntity<>(tavolo, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Tavolo> add (@Valid @RequestBody Tavolo tavolo, @RequestHeader("authorization") String user) {
        Utente utenteInSessione = utenteService.findByUsername(user);
        if(!utenteInSessione.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("ROLE_ADMIN", "ROLE_ADMIN")) && !utenteInSessione.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("ROLE_SPECIAL_PLAYER", "ROLE_SPECIAL_PLAYER"))){
            throw new UnouthorizedException("Non autorizzato");
        }
        Utente utenteCreazioneDaAssegnare = utenteService.caricaUtenteEager(tavolo.getUtenteCreazione().getId());
        tavolo.setUtenteCreazione(utenteCreazioneDaAssegnare);
        Tavolo tavoloInstance = tavoloService.inserisciNuovo(tavolo);

        return new ResponseEntity<>(tavoloInstance, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Tavolo> update(@Valid @RequestBody Tavolo tavolo, @RequestHeader("authorization") String user, @PathVariable("id") Long id) {
        Utente utenteInSessione = utenteService.findByUsername(user);
        if(!utenteInSessione.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("ROLE_ADMIN", "ROLE_ADMIN")) && !utenteInSessione.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("ROLE_SPECIAL_PLAYER", "ROLE_SPECIAL_PLAYER"))){
            throw new UnouthorizedException("Non autorizzato");
        }
        Tavolo tavoloDaModificare = tavoloService.caricaSingoloTavolo(id);
        if(tavoloDaModificare == null){
            throw new TavoloNotFoundException("Tavolo non trovato");
        }
        Tavolo tavoloInstance = tavoloService.aggiorna(tavolo);
        return new ResponseEntity<>(tavoloInstance, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id, @RequestHeader("authorization") String user) throws Exception {
        Utente utenteInSessione = utenteService.findByUsername(user);
        if(!utenteInSessione.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("ROLE_ADMIN", "ROLE_ADMIN")) && !utenteInSessione.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("ROLE_SPECIAL_PLAYER", "ROLE_SPECIAL_PLAYER"))){
            throw new UnouthorizedException("Non autorizzato");
        }
        Tavolo tavoloDaCancellare = tavoloService.caricaSingoloTavolo(id);
        if(tavoloDaCancellare == null){
            throw new TavoloNotFoundException("Tavolo non trovato");
        }
        tavoloService.rimuovi(tavoloService.caricaSingoloTavolo(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Tavolo>> search(@RequestBody Tavolo example, @RequestHeader("authorization") String user) {
        Utente utenteInSessione = utenteService.findByUsername(user);
        if(!utenteInSessione.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("ROLE_ADMIN", "ROLE_ADMIN")) && !utenteInSessione.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("ROLE_SPECIAL_PLAYER", "ROLE_SPECIAL_PLAYER"))){
            throw new UnouthorizedException("Non autorizzato");
        }
        List<Tavolo> tavoloInstance = tavoloService.findByExample(example);
        return new ResponseEntity<>(tavoloInstance, HttpStatus.OK);
    }

}
