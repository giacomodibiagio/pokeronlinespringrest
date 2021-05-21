package com.projectpokerrest.pokerrest.repository.utente;

import com.projectpokerrest.pokerrest.model.StatoUtente;
import com.projectpokerrest.pokerrest.model.Utente;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UtenteRepository extends CrudRepository<Utente, Long>, CustomUtenteRepository {

	Optional<Utente> findByUsername(String username);
	
	Utente findByUsernameAndPassword(String username, String password);
	
	@EntityGraph(attributePaths = { "ruoli" })
	Utente findByUsernameAndPasswordAndStato(String username, String password, StatoUtente stato);
	
	@Query("from Utente u left join fetch u.ruoli r left join fetch u.tavolo t where u.id = ?1")
	Optional<Utente> findOneEager(Long id);

	@Query("select u from Utente u join fetch u.tavolo t join fetch u.ruoli r")
	List<Utente> findAllEager();




}
