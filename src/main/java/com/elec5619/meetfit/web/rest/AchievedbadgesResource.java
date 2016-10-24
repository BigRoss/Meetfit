package com.elec5619.meetfit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.elec5619.meetfit.domain.Achievedbadges;

import com.elec5619.meetfit.repository.AchievedbadgesRepository;
import com.elec5619.meetfit.service.CalcBadgeService;
import com.elec5619.meetfit.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Achievedbadges.
 */
@RestController
@RequestMapping("/api")
public class AchievedbadgesResource {

    private final Logger log = LoggerFactory.getLogger(AchievedbadgesResource.class);

    @Inject
    private AchievedbadgesRepository achievedbadgesRepository;

    @Inject
    private CalcBadgeService badgeService;
    /**
     * POST  /achievedbadges : Create a new achievedbadges.
     *
     * @param achievedbadges the achievedbadges to create
     * @return the ResponseEntity with status 201 (Created) and with body the new achievedbadges, or with status 400 (Bad Request) if the achievedbadges has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/achievedbadges",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Achievedbadges> createAchievedbadges(@RequestBody Achievedbadges achievedbadges) throws URISyntaxException {
        log.debug("REST request to save Achievedbadges : {}", achievedbadges);
        if (achievedbadges.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("achievedbadges", "idexists", "A new achievedbadges cannot already have an ID")).body(null);
        }
        Achievedbadges result = badgeService.add(achievedbadges);
        return ResponseEntity.created(new URI("/api/achievedbadges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("achievedbadges", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /achievedbadges : Updates an existing achievedbadges.
     *
     * @param achievedbadges the achievedbadges to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated achievedbadges,
     * or with status 400 (Bad Request) if the achievedbadges is not valid,
     * or with status 500 (Internal Server Error) if the achievedbadges couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/achievedbadges",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Achievedbadges> updateAchievedbadges(@RequestBody Achievedbadges achievedbadges) throws URISyntaxException {
        log.debug("REST request to update Achievedbadges : {}", achievedbadges);
        if (achievedbadges.getId() == null) {
            return createAchievedbadges(achievedbadges);
        }
        Achievedbadges result = badgeService.add(achievedbadges);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("achievedbadges", achievedbadges.getId().toString()))
            .body(result);
    }

    /**
     * GET  /achievedbadges : get all the achievedbadges.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of achievedbadges in body
     */
    @RequestMapping(value = "/achievedbadges",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Achievedbadges> getAllAchievedbadges() {
        log.debug("REST request to get all Achievedbadges");
        List<Achievedbadges> achievedbadges = achievedbadgesRepository.findAllByOrderByPointsDesc();
        return achievedbadges;
    }

    /**
     * GET  /achievedbadges/:id : get the "id" achievedbadges.
     *
     * @param id the id of the achievedbadges to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the achievedbadges, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/achievedbadges/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Achievedbadges> getAchievedbadges(@PathVariable Long id) {
        log.debug("REST request to get Achievedbadges : {}", id);
        Achievedbadges achievedbadges = achievedbadgesRepository.findOne(id);
        return Optional.ofNullable(achievedbadges)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /achievedbadges/:id : delete the "id" achievedbadges.
     *
     * @param id the id of the achievedbadges to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/achievedbadges/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAchievedbadges(@PathVariable Long id) {
        log.debug("REST request to delete Achievedbadges : {}", id);
        achievedbadgesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("achievedbadges", id.toString())).build();
    }

}
