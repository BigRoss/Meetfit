package com.elec5619.meetfit.repository;

import com.elec5619.meetfit.domain.Badges;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Badges entity.
 */
@SuppressWarnings("unused")
public interface BadgesRepository extends JpaRepository<Badges,Long> {

}
