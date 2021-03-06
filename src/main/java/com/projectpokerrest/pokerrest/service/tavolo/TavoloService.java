package com.projectpokerrest.pokerrest.service.tavolo;

import com.projectpokerrest.pokerrest.model.Tavolo;
import com.projectpokerrest.pokerrest.model.Utente;

import java.util.List;

public interface TavoloService {

    List<Tavolo> listAllTavolo() ;

    List<Tavolo> listAllEager();

    Tavolo caricaSingoloTavolo(Long id);

    Tavolo caricaSingoloTavoloConUtenti(Long id);

    Tavolo aggiorna(Tavolo tavoloInstance);

    Tavolo inserisciNuovo(Tavolo tavoloInstance);

    void rimuovi(Tavolo tavoloInstance) throws Exception;

    List<Tavolo> findByExample(Tavolo tavoloInstance);

    List<Tavolo> findByUtenteCreazione(Utente utente);

    Tavolo controllaTavoloPerUsernameUtenteCreazione(Long id, String username);

}
