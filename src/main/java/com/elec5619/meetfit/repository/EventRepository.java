package com.elec5619.meetfit.repository;

import com.elec5619.meetfit.domain.Event;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Event entity.
 */
@SuppressWarnings("unused")
public interface EventRepository extends JpaRepository<Event,Long> {

    @Query("select event from Event event where event.organiser.login = ?#{principal.username}")
    List<Event> findByOrganiserIsCurrentUser();

}
