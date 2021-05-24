package com.projectpokerrest.pokerrest.web.api;

import com.projectpokerrest.pokerrest.model.Tavolo;
import com.projectpokerrest.pokerrest.model.Utente;
import com.projectpokerrest.pokerrest.service.ruolo.RuoloService;
import com.projectpokerrest.pokerrest.service.utente.UtenteService;
import com.projectpokerrest.pokerrest.web.api.exception.UnouthorizedException;
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
}
