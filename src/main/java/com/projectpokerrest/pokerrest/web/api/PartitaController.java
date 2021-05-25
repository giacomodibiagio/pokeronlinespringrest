package com.projectpokerrest.pokerrest.web.api;

import com.projectpokerrest.pokerrest.model.Tavolo;
import com.projectpokerrest.pokerrest.model.Utente;
import com.projectpokerrest.pokerrest.service.tavolo.TavoloService;
import com.projectpokerrest.pokerrest.service.utente.UtenteService;
import com.projectpokerrest.pokerrest.web.api.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/partita")
public class PartitaController {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private TavoloService tavoloService;

    @PutMapping("/buy")
    public ResponseEntity< Utente> buy (@RequestBody Double credito,@RequestHeader("authorization") String user) {
        Utente utenteInSessione = utenteService.findByUsername(user);
        utenteInSessione.setCreditoResiduo(utenteInSessione.getCreditoResiduo() + credito);
        utenteService.aggiorna(utenteInSessione);
        return new ResponseEntity<>(utenteInSessione, HttpStatus.OK);
    }

    @GetMapping("/lastgame")
    public ResponseEntity<Tavolo> buy ( @RequestHeader("authorization") String user) {
        Utente utenteInSessione = utenteService.findByUsername(user);
        return new ResponseEntity<>(utenteService.trovaTavoloGiocatore(utenteInSessione), HttpStatus.OK);
    }
    @PutMapping("/abbandona")
    public ResponseEntity<Utente> abbandona ( @RequestHeader("authorization") String user) {
        Utente utenteInSessione = utenteService.findByUsername(user);
        utenteInSessione.setTavolo(null);
        utenteInSessione.setEsperienzaAccumulata(utenteInSessione.getEsperienzaAccumulata()+1);
        utenteService.aggiorna(utenteInSessione);
        return new ResponseEntity<>(utenteInSessione, HttpStatus.OK);
    }

    @GetMapping("/ricerca")
    public ResponseEntity<List<Tavolo>> ricercaTavoliInBaseAllEsperienza ( @RequestHeader("authorization") String user) {
        Utente utenteInSessione = utenteService.findByUsername(user);
        return new ResponseEntity<>( utenteService.cercaTavoliPerEsperienza(utenteInSessione), HttpStatus.OK);
    }

    @PutMapping("/giocapartita")
    public ResponseEntity<Tavolo> giocaPartitaSuTavolo (  @RequestBody Tavolo tavolo ,@RequestHeader("authorization") String user) {
        Utente utenteInSessione = utenteService.findByUsername(user);
        Tavolo tavolo1 = tavoloService.caricaSingoloTavolo(tavolo.getId());
        if ( utenteInSessione.getCreditoResiduo() < tavolo1.getCifraMinima()){
            throw  new BusinessException("non hai credito sufficiente per giocare alla partita");
        }

        utenteInSessione.setTavolo(tavolo1);
        utenteService.aggiorna(utenteInSessione);
        return new ResponseEntity<>(tavolo1, HttpStatus.OK);
    }

}
