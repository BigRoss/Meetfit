package com.elec5619.meetfit.web.rest;

import com.elec5619.meetfit.MeetFitApp;

import com.elec5619.meetfit.domain.Badges;
import com.elec5619.meetfit.repository.BadgesRepository;

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
 * Test class for the BadgesResource REST controller.
 *
 * @see BadgesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MeetFitApp.class)
public class BadgesResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_POINTS = 1;
    private static final Integer UPDATED_POINTS = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private BadgesRepository badgesRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restBadgesMockMvc;

    private Badges badges;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BadgesResource badgesResource = new BadgesResource();
        ReflectionTestUtils.setField(badgesResource, "badgesRepository", badgesRepository);
        this.restBadgesMockMvc = MockMvcBuilders.standaloneSetup(badgesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Badges createEntity(EntityManager em) {
        Badges badges = new Badges()
                .name(DEFAULT_NAME)
                .points(DEFAULT_POINTS)
                .description(DEFAULT_DESCRIPTION);
        return badges;
    }

    @Before
    public void initTest() {
        badges = createEntity(em);
    }

    @Test
    @Transactional
    public void createBadges() throws Exception {
        int databaseSizeBeforeCreate = badgesRepository.findAll().size();

        // Create the Badges

        restBadgesMockMvc.perform(post("/api/badges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(badges)))
                .andExpect(status().isCreated());

        // Validate the Badges in the database
        List<Badges> badges = badgesRepository.findAll();
        assertThat(badges).hasSize(databaseSizeBeforeCreate + 1);
        Badges testBadges = badges.get(badges.size() - 1);
        assertThat(testBadges.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBadges.getPoints()).isEqualTo(DEFAULT_POINTS);
        assertThat(testBadges.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBadges() throws Exception {
        // Initialize the database
        badgesRepository.saveAndFlush(badges);

        // Get all the badges
        restBadgesMockMvc.perform(get("/api/badges?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(badges.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getBadges() throws Exception {
        // Initialize the database
        badgesRepository.saveAndFlush(badges);

        // Get the badges
        restBadgesMockMvc.perform(get("/api/badges/{id}", badges.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(badges.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBadges() throws Exception {
        // Get the badges
        restBadgesMockMvc.perform(get("/api/badges/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBadges() throws Exception {
        // Initialize the database
        badgesRepository.saveAndFlush(badges);
        int databaseSizeBeforeUpdate = badgesRepository.findAll().size();

        // Update the badges
        Badges updatedBadges = badgesRepository.findOne(badges.getId());
        updatedBadges
                .name(UPDATED_NAME)
                .points(UPDATED_POINTS)
                .description(UPDATED_DESCRIPTION);

        restBadgesMockMvc.perform(put("/api/badges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedBadges)))
                .andExpect(status().isOk());

        // Validate the Badges in the database
        List<Badges> badges = badgesRepository.findAll();
        assertThat(badges).hasSize(databaseSizeBeforeUpdate);
        Badges testBadges = badges.get(badges.size() - 1);
        assertThat(testBadges.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBadges.getPoints()).isEqualTo(UPDATED_POINTS);
        assertThat(testBadges.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteBadges() throws Exception {
        // Initialize the database
        badgesRepository.saveAndFlush(badges);
        int databaseSizeBeforeDelete = badgesRepository.findAll().size();

        // Get the badges
        restBadgesMockMvc.perform(delete("/api/badges/{id}", badges.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Badges> badges = badgesRepository.findAll();
        assertThat(badges).hasSize(databaseSizeBeforeDelete - 1);
    }
}
