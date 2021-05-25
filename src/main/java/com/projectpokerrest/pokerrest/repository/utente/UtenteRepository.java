package com.projectpokerrest.pokerrest.repository.utente;

import com.projectpokerrest.pokerrest.model.StatoUtente;
import com.projectpokerrest.pokerrest.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UtenteRepository extends CrudRepository<User, Long>, CustomUtenteRepository {

	Optional<User> findByUsername(String username);
	
	User findByUsernameAndPassword(String username, String password);
	
	@EntityGraph(attributePaths = { "ruoli" })
    User findByUsernameAndPasswordAndStato(String username, String password, StatoUtente stato);
	
	@Query("from User u left join fetch u.ruoli r left join fetch u.tavolo t where u.id = ?1")
	Optional<User> findOneEager(Long id);

	@Query("select u from User u left join fetch u.tavolo t left join fetch u.ruoli r")
	List<User> findAllEager();






}
