package com.projectpokerrest.pokerrest.web.api;

import com.projectpokerrest.pokerrest.model.Tavolo;
import com.projectpokerrest.pokerrest.model.User;
import com.projectpokerrest.pokerrest.security.service.UserService;
import com.projectpokerrest.pokerrest.service.tavolo.TavoloService;
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
    private UserService utenteService;
    @Autowired
    private TavoloService tavoloService;

    @PutMapping("/buy")
    public ResponseEntity<User> buy (@RequestBody Double credito, @RequestHeader("authorization") String user) {
        User userInSessione = utenteService.findByUsername(user);
        userInSessione.setCreditoAccumulato(userInSessione.getCreditoAccumulato() + credito);
        utenteService.aggiorna(userInSessione);
        return new ResponseEntity<>(userInSessione, HttpStatus.OK);
    }

    @GetMapping("/lastgame")
    public ResponseEntity<Tavolo> buy ( @RequestHeader("authorization") String user) {
        User userInSessione = utenteService.findByUsername(user);
        return new ResponseEntity<>(utenteService.trovaTavoloGiocatore(userInSessione), HttpStatus.OK);
    }
    @PutMapping("/abbandona")
    public ResponseEntity<User> abbandona (@RequestHeader("authorization") String user) {
        User userInSessione = utenteService.findByUsername(user);
        userInSessione.setTavolo(null);
        userInSessione.setEsperienzaAccumulata(userInSessione.getEsperienzaAccumulata()+1);
        utenteService.aggiorna(userInSessione);
        return new ResponseEntity<>(userInSessione, HttpStatus.OK);
    }

    @GetMapping("/ricerca")
    public ResponseEntity<List<Tavolo>> ricercaTavoliInBaseAllEsperienza ( @RequestHeader("authorization") String user) {
        User userInSessione = utenteService.findByUsername(user);
        return new ResponseEntity<>( utenteService.cercaTavoliPerEsperienza(userInSessione), HttpStatus.OK);
    }

    @PutMapping("/giocapartita")
    public ResponseEntity<Tavolo> giocaPartitaSuTavolo (  @RequestBody Tavolo tavolo ,@RequestHeader("authorization") String user) {
        User userInSessione = utenteService.findByUsername(user);
        Tavolo tavolo1 = tavoloService.caricaSingoloTavolo(tavolo.getId());
        if ( userInSessione.getCreditoAccumulato() < tavolo1.getCifraMinima()){
            throw  new BusinessException("non hai credito sufficiente per giocare alla partita");
        }

        userInSessione.setTavolo(tavolo1);
        utenteService.aggiorna(userInSessione);
        return new ResponseEntity<>(tavolo1, HttpStatus.OK);
    }

}
