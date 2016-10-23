package com.elec5619.meetfit.repository;

import com.elec5619.meetfit.domain.Achievedbadges;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Achievedbadges entity.
 */
@SuppressWarnings("unused")
public interface AchievedbadgesRepository extends JpaRepository<Achievedbadges,Long> {

}
