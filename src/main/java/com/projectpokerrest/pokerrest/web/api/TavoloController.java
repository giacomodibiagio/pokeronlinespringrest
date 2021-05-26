package com.projectpokerrest.pokerrest.web.api;

import com.projectpokerrest.pokerrest.model.Tavolo;
import com.projectpokerrest.pokerrest.model.User;
import com.projectpokerrest.pokerrest.security.service.AuthorityService;
import com.projectpokerrest.pokerrest.security.service.UserService;
import com.projectpokerrest.pokerrest.service.tavolo.TavoloService;
import com.projectpokerrest.pokerrest.web.api.exception.TavoloNotFoundException;
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
    private UserService utenteService;

    @Autowired
    private AuthorityService ruoloService;

    @GetMapping("/all")
    public ResponseEntity<List<Tavolo>> getAll (@RequestHeader("authorization") String user) {

        List<Tavolo> tavoli = tavoloService.findByUtenteCreazione(utenteService.findByUsername(user));
        return new ResponseEntity<>(tavoli, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Tavolo> get (@PathVariable("id") Long id, @RequestHeader("authorization") String user) {

        Tavolo tavoloDaCercare = null;


        if((tavoloDaCercare = tavoloService.controllaTavoloPerUsernameUtenteCreazione(id, user)) == null){
            throw new TavoloNotFoundException("Tavolo non trovato");
        }
         return new ResponseEntity<>(tavoloDaCercare, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Tavolo> add (@Valid @RequestBody Tavolo tavolo, @RequestHeader("authorization") String user) {

        User userCreazioneDaAssegnare = utenteService.caricaUserEager(tavolo.getUtenteCreazione().getId());
        tavolo.setUtenteCreazione(userCreazioneDaAssegnare);
        Tavolo tavoloInstance = tavoloService.inserisciNuovo(tavolo);

        return new ResponseEntity<>(tavoloInstance, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Tavolo> update(@Valid @RequestBody Tavolo tavolo, @RequestHeader("authorization") String user, @PathVariable("id") Long id) {

        Tavolo tavoloDaModificare = tavoloService.caricaSingoloTavolo(id);
        if(tavoloDaModificare == null){
            throw new TavoloNotFoundException("Tavolo non trovato");
        }
        Tavolo tavoloInstance = tavoloService.aggiorna(tavolo);
        return new ResponseEntity<>(tavoloInstance, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id, @RequestHeader("authorization") String user) throws Exception {

        Tavolo tavoloDaCancellare = tavoloService.caricaSingoloTavolo(id);
        if(tavoloDaCancellare == null){
            throw new TavoloNotFoundException("Tavolo non trovato");
        }
        if(!tavoloDaCancellare.getUtenti().isEmpty()){
            throw new RuntimeException("Tavolo non eliminabile. Ci sono ancora utenti assegnati");
        }
        tavoloService.rimuovi(tavoloService.caricaSingoloTavolo(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Tavolo>> search(@RequestBody Tavolo example, @RequestHeader("authorization") String user) {

//        if(userInSessione.getRuoli().contains(ruoloService.cercaPerDescrizioneECodice("ROLE_SPECIAL_PLAYER", "ROLE_SPECIAL_PLAYER"))){
//            example.setUtenteCreazione(utenteService.findByUsername(user));
//            List<Tavolo> tavoloInstance = tavoloService.findByExample(example);
//            return new ResponseEntity<>(tavoloInstance, HttpStatus.OK);
//        }
        List<Tavolo> tavoloInstance = tavoloService.findByExample(example);
        return new ResponseEntity<>(tavoloInstance, HttpStatus.OK);
    }

}
