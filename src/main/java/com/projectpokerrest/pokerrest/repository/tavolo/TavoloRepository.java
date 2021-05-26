package com.projectpokerrest.pokerrest.repository.tavolo;

import com.projectpokerrest.pokerrest.model.Tavolo;
import com.projectpokerrest.pokerrest.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TavoloRepository extends CrudRepository<Tavolo, Long>, CustomTavoloRepository {

    @Query("from Tavolo t left join fetch t.utenti u left join fetch t.userCreazione c where t.id = :id")
    Tavolo findOneEager(Long id);

    @Query("select t from Tavolo t left join fetch t.utenti u left join fetch t.userCreazione c")
    List<Tavolo> findAllEager();

    List<Tavolo> findByUserCreazione(User user);

    List<Tavolo> findByEsperienzaMinLessThan(Double esperienzaAccumulata);
}
