package com.elec5619.meetfit.service.impl;

import com.elec5619.meetfit.service.ProfileService;
import com.elec5619.meetfit.domain.Profile;
import com.elec5619.meetfit.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Profile.
 */
@Service
@Transactional
public class ProfileServiceImpl implements ProfileService{

    private final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class);
    
    @Inject
    private ProfileRepository profileRepository;

    /**
     * Save a profile.
     *
     * @param profile the entity to save
     * @return the persisted entity
     */
    public Profile save(Profile profile) {
        log.debug("Request to save Profile : {}", profile);
        Profile result = profileRepository.save(profile);
        return result;
    }

    /**
     *  Get all the profiles.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Profile> findAll() {
        log.debug("Request to get all Profiles");
        List<Profile> result = profileRepository.findAll();

        return result;
    }

    /**
     *  Get one profile by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Profile findOne(Long id) {
        log.debug("Request to get Profile : {}", id);
        Profile profile = profileRepository.findOne(id);
        return profile;
    }

    /**
     *  Delete the  profile by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Profile : {}", id);
        profileRepository.delete(id);
    }
}
