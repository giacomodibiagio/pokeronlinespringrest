package com.projectpokerrest.pokerrest.repository.ruolo;

import com.projectpokerrest.pokerrest.model.Authority;
import org.springframework.data.repository.CrudRepository;

public interface RuoloRepository extends CrudRepository<Authority, Long> {

	Authority findByDescrizioneAndCodice(String descrizione, String codice);
	
}
