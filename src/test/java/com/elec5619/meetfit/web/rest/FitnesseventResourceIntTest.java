package com.elec5619.meetfit.web.rest;

import com.elec5619.meetfit.MeetFitApp;

import com.elec5619.meetfit.domain.Fitnessevent;
import com.elec5619.meetfit.domain.User;
import com.elec5619.meetfit.repository.FitnesseventRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FitnesseventResource REST controller.
 *
 * @see FitnesseventResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MeetFitApp.class)
public class FitnesseventResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final String DEFAULT_LOCATION = "AAAAA";
    private static final String UPDATED_LOCATION = "BBBBB";

    private static final ZonedDateTime DEFAULT_STARTTIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_STARTTIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_STARTTIME_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_STARTTIME);

    private static final ZonedDateTime DEFAULT_ENDTIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_ENDTIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_ENDTIME_STR = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(DEFAULT_ENDTIME);

    @Inject
    private FitnesseventRepository fitnesseventRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restFitnesseventMockMvc;

    private Fitnessevent fitnessevent;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FitnesseventResource fitnesseventResource = new FitnesseventResource();
        ReflectionTestUtils.setField(fitnesseventResource, "fitnesseventRepository", fitnesseventRepository);
        this.restFitnesseventMockMvc = MockMvcBuilders.standaloneSetup(fitnesseventResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fitnessevent createEntity(EntityManager em) {
        Fitnessevent fitnessevent = new Fitnessevent()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .location(DEFAULT_LOCATION)
                .starttime(DEFAULT_STARTTIME)
                .endtime(DEFAULT_ENDTIME);
        // Add required entity
        User organiser = UserResourceIntTest.createEntity(em);
        em.persist(organiser);
        em.flush();
        fitnessevent.setOrganiser(organiser);
        return fitnessevent;
    }

    @Before
    public void initTest() {
        fitnessevent = createEntity(em);
    }

    @Test
    @Transactional
    public void createFitnessevent() throws Exception {
        int databaseSizeBeforeCreate = fitnesseventRepository.findAll().size();

        // Create the Fitnessevent

        restFitnesseventMockMvc.perform(post("/api/fitnessevents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fitnessevent)))
                .andExpect(status().isCreated());

        // Validate the Fitnessevent in the database
        List<Fitnessevent> fitnessevents = fitnesseventRepository.findAll();
        assertThat(fitnessevents).hasSize(databaseSizeBeforeCreate + 1);
        Fitnessevent testFitnessevent = fitnessevents.get(fitnessevents.size() - 1);
        assertThat(testFitnessevent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFitnessevent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFitnessevent.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testFitnessevent.getStarttime()).isEqualTo(DEFAULT_STARTTIME);
        assertThat(testFitnessevent.getEndtime()).isEqualTo(DEFAULT_ENDTIME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fitnesseventRepository.findAll().size();
        // set the field null
        fitnessevent.setName(null);

        // Create the Fitnessevent, which fails.

        restFitnesseventMockMvc.perform(post("/api/fitnessevents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fitnessevent)))
                .andExpect(status().isBadRequest());

        List<Fitnessevent> fitnessevents = fitnesseventRepository.findAll();
        assertThat(fitnessevents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = fitnesseventRepository.findAll().size();
        // set the field null
        fitnessevent.setLocation(null);

        // Create the Fitnessevent, which fails.

        restFitnesseventMockMvc.perform(post("/api/fitnessevents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fitnessevent)))
                .andExpect(status().isBadRequest());

        List<Fitnessevent> fitnessevents = fitnesseventRepository.findAll();
        assertThat(fitnessevents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStarttimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fitnesseventRepository.findAll().size();
        // set the field null
        fitnessevent.setStarttime(null);

        // Create the Fitnessevent, which fails.

        restFitnesseventMockMvc.perform(post("/api/fitnessevents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fitnessevent)))
                .andExpect(status().isBadRequest());

        List<Fitnessevent> fitnessevents = fitnesseventRepository.findAll();
        assertThat(fitnessevents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndtimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fitnesseventRepository.findAll().size();
        // set the field null
        fitnessevent.setEndtime(null);

        // Create the Fitnessevent, which fails.

        restFitnesseventMockMvc.perform(post("/api/fitnessevents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fitnessevent)))
                .andExpect(status().isBadRequest());

        List<Fitnessevent> fitnessevents = fitnesseventRepository.findAll();
        assertThat(fitnessevents).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFitnessevents() throws Exception {
        // Initialize the database
        fitnesseventRepository.saveAndFlush(fitnessevent);

        // Get all the fitnessevents
        restFitnesseventMockMvc.perform(get("/api/fitnessevents?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(fitnessevent.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].starttime").value(hasItem(DEFAULT_STARTTIME_STR)))
                .andExpect(jsonPath("$.[*].endtime").value(hasItem(DEFAULT_ENDTIME_STR)));
    }

    @Test
    @Transactional
    public void getFitnessevent() throws Exception {
        // Initialize the database
        fitnesseventRepository.saveAndFlush(fitnessevent);

        // Get the fitnessevent
        restFitnesseventMockMvc.perform(get("/api/fitnessevents/{id}", fitnessevent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fitnessevent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.starttime").value(DEFAULT_STARTTIME_STR))
            .andExpect(jsonPath("$.endtime").value(DEFAULT_ENDTIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingFitnessevent() throws Exception {
        // Get the fitnessevent
        restFitnesseventMockMvc.perform(get("/api/fitnessevents/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFitnessevent() throws Exception {
        // Initialize the database
        fitnesseventRepository.saveAndFlush(fitnessevent);
        int databaseSizeBeforeUpdate = fitnesseventRepository.findAll().size();

        // Update the fitnessevent
        Fitnessevent updatedFitnessevent = fitnesseventRepository.findOne(fitnessevent.getId());
        updatedFitnessevent
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .location(UPDATED_LOCATION)
                .starttime(UPDATED_STARTTIME)
                .endtime(UPDATED_ENDTIME);

        restFitnesseventMockMvc.perform(put("/api/fitnessevents")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFitnessevent)))
                .andExpect(status().isOk());

        // Validate the Fitnessevent in the database
        List<Fitnessevent> fitnessevents = fitnesseventRepository.findAll();
        assertThat(fitnessevents).hasSize(databaseSizeBeforeUpdate);
        Fitnessevent testFitnessevent = fitnessevents.get(fitnessevents.size() - 1);
        assertThat(testFitnessevent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFitnessevent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFitnessevent.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testFitnessevent.getStarttime()).isEqualTo(UPDATED_STARTTIME);
        assertThat(testFitnessevent.getEndtime()).isEqualTo(UPDATED_ENDTIME);
    }

    @Test
    @Transactional
    public void deleteFitnessevent() throws Exception {
        // Initialize the database
        fitnesseventRepository.saveAndFlush(fitnessevent);
        int databaseSizeBeforeDelete = fitnesseventRepository.findAll().size();

        // Get the fitnessevent
        restFitnesseventMockMvc.perform(delete("/api/fitnessevents/{id}", fitnessevent.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Fitnessevent> fitnessevents = fitnesseventRepository.findAll();
        assertThat(fitnessevents).hasSize(databaseSizeBeforeDelete - 1);
    }
}
