package com.elec5619.meetfit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.elec5619.meetfit.domain.Fitnessevent;

import com.elec5619.meetfit.domain.User;
import com.elec5619.meetfit.repository.FitnesseventRepository;
import com.elec5619.meetfit.repository.UserRepository;
import com.elec5619.meetfit.security.SecurityUtils;
import com.elec5619.meetfit.service.FitnesseventService;
import com.elec5619.meetfit.web.rest.util.HeaderUtil;
import com.elec5619.meetfit.web.rest.vm.ManagedUserVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Fitnessevent.
 */
@RestController
@RequestMapping("/api")
public class FitnesseventResource {

    private final Logger log = LoggerFactory.getLogger(FitnesseventResource.class);

    @Inject
    private FitnesseventRepository fitnesseventRepository;

    @Inject
    private FitnesseventService fitnesseventService;

    /**
     * POST  /fitnessevents : Create a new fitnessevent.
     *
     * @param fitnessevent the fitnessevent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fitnessevent, or with status 400 (Bad Request) if the fitnessevent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/fitnessevents",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fitnessevent> createFitnessevent(@Valid @RequestBody Fitnessevent fitnessevent) throws URISyntaxException {
        log.debug("REST request to save Fitnessevent : {}", fitnessevent);
        if (fitnessevent.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("fitnessevent", "idexists", "A new fitnessevent cannot already have an ID")).body(null);
        }

        Fitnessevent result = fitnesseventService.CreatorJoinEvent(fitnessevent);

        return ResponseEntity.created(new URI("/api/fitnessevents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("fitnessevent", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fitnessevents : Updates an existing fitnessevent.
     *
     * @param fitnessevent the fitnessevent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fitnessevent,
     * or with status 400 (Bad Request) if the fitnessevent is not valid,
     * or with status 500 (Internal Server Error) if the fitnessevent couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/fitnessevents",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fitnessevent> updateFitnessevent(@Valid @RequestBody Fitnessevent fitnessevent) throws URISyntaxException {
        log.debug("REST request to update Fitnessevent : {}", fitnessevent);
        if (fitnessevent.getId() == null) {
            return createFitnessevent(fitnessevent);
        }
        Fitnessevent result = fitnesseventRepository.save(fitnessevent);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("fitnessevent", fitnessevent.getId().toString())).body(result);
    }

    /**
     * GET  /fitnessevents : get all the fitnessevents.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fitnessevents in body
     */
    @RequestMapping(value = "/fitnessevents",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Fitnessevent> getAllFitnessevents() {
        log.debug("REST request to get all Fitnessevents");
        List<Fitnessevent> fitnessevents = fitnesseventRepository.findAll();
        return fitnessevents;
    }

    /**
     * GET  /fitnessevents/:id : get the "id" fitnessevent.
     *
     * @param id the id of the fitnessevent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fitnessevent, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/fitnessevents/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fitnessevent> getFitnessevent(@PathVariable Long id) {
        log.debug("REST request to get Fitnessevent : {}", id);
        Fitnessevent fitnessevent = fitnesseventRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(fitnessevent)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fitnessevents/:id : delete the "id" fitnessevent.
     *
     * @param id the id of the fitnessevent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/fitnessevents/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFitnessevent(@PathVariable Long id) {
        log.debug("REST request to delete Fitnessevent : {}", id);
        fitnesseventRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fitnessevent", id.toString())).build();
    }

}
