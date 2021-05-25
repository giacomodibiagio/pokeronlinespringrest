package com.projectpokerrest.pokerrest.security.repository;

 import com.projectpokerrest.pokerrest.model.StatoUtente;
 import com.projectpokerrest.pokerrest.model.User;
 import com.projectpokerrest.pokerrest.repository.utente.CustomUtenteRepository;
 import org.springframework.data.jpa.repository.EntityGraph;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.jpa.repository.Query;

 import java.util.List;
 import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, CustomUtenteRepository {
	Optional<User> findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    @EntityGraph(attributePaths = { "authorities" })
    User findByUsernameAndPasswordAndStato(String username, String password, StatoUtente stato);

    @Query("from User u left join fetch u.authorities r left join fetch u.tavolo t where u.id = ?1")
    Optional<User> findOneEager(Long id);

    @Query("select u from User u left join fetch u.tavolo t left join fetch u.authorities r")
    List<User> findAllEager();

}
