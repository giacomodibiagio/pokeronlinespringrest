package com.projectpokerrest.pokerrest.web.api;

import com.projectpokerrest.pokerrest.model.Tavolo;
import com.projectpokerrest.pokerrest.service.tavolo.TavoloService;
import com.projectpokerrest.pokerrest.web.api.exception.UnouthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tavolo")
public class TavoloController {

    @Autowired
    private TavoloService tavoloService;

    @GetMapping("/all")
    public ResponseEntity<List<Tavolo>> getAll (@RequestHeader("authorization") String user) {
        if(!user.equals("admin") && !user.equals("special")){
            throw new UnouthorizedException("Non autorizzato");
        }
        List<Tavolo> tavoli = tavoloService.listAllEager();
        return new ResponseEntity<>(tavoli, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Tavolo> get (@PathVariable("id") Long id) {
        Tavolo tavolo = tavoloService.caricaSingoloTavoloConUtenti(id);
        return new ResponseEntity<>(tavolo, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Tavolo> add (@RequestBody Tavolo tavolo) {
        Tavolo tavoloInstance = tavoloService.inserisciNuovo(tavolo);
        return new ResponseEntity<>(tavoloInstance, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Tavolo> update(@RequestBody Tavolo tavolo) {
        Tavolo tavoloInstance = tavoloService.aggiorna(tavolo);
        return new ResponseEntity<>(tavoloInstance, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        tavoloService.rimuovi(tavoloService.caricaSingoloTavolo(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Tavolo>> search(@RequestBody Tavolo example) {
        List<Tavolo> tavoloInstance = tavoloService.findByExample(example);
        return new ResponseEntity<>(tavoloInstance, HttpStatus.OK);
    }

}
