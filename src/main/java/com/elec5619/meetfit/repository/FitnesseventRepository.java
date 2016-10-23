package com.elec5619.meetfit.repository;

import com.elec5619.meetfit.domain.Fitnessevent;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Fitnessevent entity.
 */
@SuppressWarnings("unused")
public interface FitnesseventRepository extends JpaRepository<Fitnessevent,Long> {

    @Query("select fitnessevent from Fitnessevent fitnessevent where fitnessevent.organiser.login = ?#{principal.username}")
    List<Fitnessevent> findByOrganiserIsCurrentUser();

    @Query("select distinct fitnessevent from Fitnessevent fitnessevent left join fetch fitnessevent.attendings")
    List<Fitnessevent> findAllWithEagerRelationships();

    @Query("select fitnessevent from Fitnessevent fitnessevent left join fetch fitnessevent.attendings where fitnessevent.id =:id")
    Fitnessevent findOneWithEagerRelationships(@Param("id") Long id);

}
