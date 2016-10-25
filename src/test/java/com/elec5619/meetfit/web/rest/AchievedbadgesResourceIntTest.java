package com.elec5619.meetfit.web.rest;

import com.elec5619.meetfit.MeetFitApp;

import com.elec5619.meetfit.domain.Achievedbadges;
import com.elec5619.meetfit.repository.AchievedbadgesRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AchievedbadgesResource REST controller.
 *
 * @see AchievedbadgesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MeetFitApp.class)
public class AchievedbadgesResourceIntTest {

    private static final Integer DEFAULT_POINTS = 1;
    private static final Integer UPDATED_POINTS = 2;

    private static final String DEFAULT_TYPE = "AAAAA";
    private static final String UPDATED_TYPE = "BBBBB";

    @Inject
    private AchievedbadgesRepository achievedbadgesRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAchievedbadgesMockMvc;

    private Achievedbadges achievedbadges;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AchievedbadgesResource achievedbadgesResource = new AchievedbadgesResource();
        ReflectionTestUtils.setField(achievedbadgesResource, "achievedbadgesRepository", achievedbadgesRepository);
        this.restAchievedbadgesMockMvc = MockMvcBuilders.standaloneSetup(achievedbadgesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Achievedbadges createEntity(EntityManager em) {
        Achievedbadges achievedbadges = new Achievedbadges()
                .points(DEFAULT_POINTS)
                .type(DEFAULT_TYPE);
        return achievedbadges;
    }

    @Before
    public void initTest() {
        achievedbadges = createEntity(em);
    }

    @Test
    @Transactional
    public void createAchievedbadges() throws Exception {
        int databaseSizeBeforeCreate = achievedbadgesRepository.findAll().size();

        // Create the Achievedbadges

        restAchievedbadgesMockMvc.perform(post("/api/achievedbadges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(achievedbadges)))
                .andExpect(status().isCreated());

        // Validate the Achievedbadges in the database
        List<Achievedbadges> achievedbadges = achievedbadgesRepository.findAll();
        assertThat(achievedbadges).hasSize(databaseSizeBeforeCreate + 1);
        // Achievedbadges testAchievedbadges = achievedbadges.get(achievedbadges.size() - 1);
        // assertThat(testAchievedbadges.getPoints()).isEqualTo(DEFAULT_POINTS);
        // assertThat(testAchievedbadges.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void getAllAchievedbadges() throws Exception {
        // Initialize the database
        achievedbadgesRepository.saveAndFlush(achievedbadges);

        // Get all the achievedbadges
        restAchievedbadgesMockMvc.perform(get("/api/achievedbadges?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(achievedbadges.getId().intValue())))
                .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS)))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getAchievedbadges() throws Exception {
        // Initialize the database
        achievedbadgesRepository.saveAndFlush(achievedbadges);

        // Get the achievedbadges
        restAchievedbadgesMockMvc.perform(get("/api/achievedbadges/{id}", achievedbadges.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(achievedbadges.getId().intValue()))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAchievedbadges() throws Exception {
        // Get the achievedbadges
        restAchievedbadgesMockMvc.perform(get("/api/achievedbadges/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAchievedbadges() throws Exception {
        // Initialize the database
        achievedbadgesRepository.saveAndFlush(achievedbadges);
        int databaseSizeBeforeUpdate = achievedbadgesRepository.findAll().size();

        // Update the achievedbadges
        Achievedbadges updatedAchievedbadges = achievedbadgesRepository.findOne(achievedbadges.getId());
        updatedAchievedbadges
                .points(UPDATED_POINTS)
                .type(UPDATED_TYPE);

        restAchievedbadgesMockMvc.perform(put("/api/achievedbadges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAchievedbadges)))
                .andExpect(status().isOk());

        // Validate the Achievedbadges in the database
        List<Achievedbadges> achievedbadges = achievedbadgesRepository.findAll();
        // assertThat(achievedbadges).hasSize(databaseSizeBeforeUpdate);
        // Achievedbadges testAchievedbadges = achievedbadges.get(achievedbadges.size() - 1);
        // assertThat(testAchievedbadges.getPoints()).isEqualTo(UPDATED_POINTS);
        // assertThat(testAchievedbadges.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deleteAchievedbadges() throws Exception {
        // Initialize the database
        achievedbadgesRepository.saveAndFlush(achievedbadges);
        int databaseSizeBeforeDelete = achievedbadgesRepository.findAll().size();

        // Get the achievedbadges
        restAchievedbadgesMockMvc.perform(delete("/api/achievedbadges/{id}", achievedbadges.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Achievedbadges> achievedbadges = achievedbadgesRepository.findAll();
        assertThat(achievedbadges).hasSize(databaseSizeBeforeDelete - 1);
    }
}
