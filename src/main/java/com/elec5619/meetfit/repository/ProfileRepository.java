package com.elec5619.meetfit.repository;

import com.elec5619.meetfit.domain.Profile;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Profile entity.
 */
@SuppressWarnings("unused")
public interface ProfileRepository extends JpaRepository<Profile,Long> {

}
