package com.elec5619.meetfit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.elec5619.meetfit.domain.Badges;

import com.elec5619.meetfit.repository.BadgesRepository;
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
 * REST controller for managing Badges.
 */
@RestController
@RequestMapping("/api")
public class BadgesResource {

    private final Logger log = LoggerFactory.getLogger(BadgesResource.class);
        
    @Inject
    private BadgesRepository badgesRepository;

    /**
     * POST  /badges : Create a new badges.
     *
     * @param badges the badges to create
     * @return the ResponseEntity with status 201 (Created) and with body the new badges, or with status 400 (Bad Request) if the badges has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/badges",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Badges> createBadges(@RequestBody Badges badges) throws URISyntaxException {
        log.debug("REST request to save Badges : {}", badges);
        if (badges.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("badges", "idexists", "A new badges cannot already have an ID")).body(null);
        }
        Badges result = badgesRepository.save(badges);
        return ResponseEntity.created(new URI("/api/badges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("badges", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /badges : Updates an existing badges.
     *
     * @param badges the badges to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated badges,
     * or with status 400 (Bad Request) if the badges is not valid,
     * or with status 500 (Internal Server Error) if the badges couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/badges",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Badges> updateBadges(@RequestBody Badges badges) throws URISyntaxException {
        log.debug("REST request to update Badges : {}", badges);
        if (badges.getId() == null) {
            return createBadges(badges);
        }
        Badges result = badgesRepository.save(badges);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("badges", badges.getId().toString()))
            .body(result);
    }

    /**
     * GET  /badges : get all the badges.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of badges in body
     */
    @RequestMapping(value = "/badges",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Badges> getAllBadges() {
        log.debug("REST request to get all Badges");
        List<Badges> badges = badgesRepository.findAll();
        return badges;
    }

    /**
     * GET  /badges/:id : get the "id" badges.
     *
     * @param id the id of the badges to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the badges, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/badges/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Badges> getBadges(@PathVariable Long id) {
        log.debug("REST request to get Badges : {}", id);
        Badges badges = badgesRepository.findOne(id);
        return Optional.ofNullable(badges)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /badges/:id : delete the "id" badges.
     *
     * @param id the id of the badges to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/badges/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBadges(@PathVariable Long id) {
        log.debug("REST request to delete Badges : {}", id);
        badgesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("badges", id.toString())).build();
    }

}
