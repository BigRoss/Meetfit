package com.elec5619.meetfit.web.rest;

import com.elec5619.meetfit.MeetFitApp;

import com.elec5619.meetfit.domain.Profile;
import com.elec5619.meetfit.repository.ProfileRepository;
import com.elec5619.meetfit.service.ProfileService;

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
 * Test class for the ProfileResource REST controller.
 *
 * @see ProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MeetFitApp.class)
public class ProfileResourceIntTest {

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final Float DEFAULT_WEIGHT = 1F;
    private static final Float UPDATED_WEIGHT = 2F;

    private static final String DEFAULT_LOCATION = "AAAAA";
    private static final String UPDATED_LOCATION = "BBBBB";

    private static final Float DEFAULT_GOALWEIGHT = 1F;
    private static final Float UPDATED_GOALWEIGHT = 2F;

    private static final String DEFAULT_GENDER = "AAAAA";
    private static final String UPDATED_GENDER = "BBBBB";

    private static final String DEFAULT_BIO = "AAAAA";
    private static final String UPDATED_BIO = "BBBBB";

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private ProfileService profileService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restProfileMockMvc;

    private Profile profile;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProfileResource profileResource = new ProfileResource();
        ReflectionTestUtils.setField(profileResource, "profileService", profileService);
        this.restProfileMockMvc = MockMvcBuilders.standaloneSetup(profileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createEntity(EntityManager em) {
        Profile profile = new Profile()
                .height(DEFAULT_HEIGHT)
                .weight(DEFAULT_WEIGHT)
                .location(DEFAULT_LOCATION)
                .goalweight(DEFAULT_GOALWEIGHT)
                .gender(DEFAULT_GENDER)
                .bio(DEFAULT_BIO);
        return profile;
    }

    @Before
    public void initTest() {
        profile = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile

        restProfileMockMvc.perform(post("/api/profiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(profile)))
                .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profiles = profileRepository.findAll();
        assertThat(profiles).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profiles.get(profiles.size() - 1);
        assertThat(testProfile.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testProfile.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testProfile.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testProfile.getGoalweight()).isEqualTo(DEFAULT_GOALWEIGHT);
        assertThat(testProfile.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testProfile.getBio()).isEqualTo(DEFAULT_BIO);
    }

    @Test
    @Transactional
    public void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get all the profiles
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId().intValue())))
                .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
                .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].goalweight").value(hasItem(DEFAULT_GOALWEIGHT.doubleValue())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].bio").value(hasItem(DEFAULT_BIO.toString())));
    }

    @Test
    @Transactional
    public void getProfile() throws Exception {
        // Initialize the database
        profileRepository.saveAndFlush(profile);

        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId().intValue()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.goalweight").value(DEFAULT_GOALWEIGHT.doubleValue()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.bio").value(DEFAULT_BIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfile() throws Exception {
        // Initialize the database
        profileService.save(profile);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findOne(profile.getId());
        updatedProfile
                .height(UPDATED_HEIGHT)
                .weight(UPDATED_WEIGHT)
                .location(UPDATED_LOCATION)
                .goalweight(UPDATED_GOALWEIGHT)
                .gender(UPDATED_GENDER)
                .bio(UPDATED_BIO);

        restProfileMockMvc.perform(put("/api/profiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedProfile)))
                .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profiles = profileRepository.findAll();
        assertThat(profiles).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profiles.get(profiles.size() - 1);
        assertThat(testProfile.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testProfile.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testProfile.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testProfile.getGoalweight()).isEqualTo(UPDATED_GOALWEIGHT);
        assertThat(testProfile.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testProfile.getBio()).isEqualTo(UPDATED_BIO);
    }

    @Test
    @Transactional
    public void deleteProfile() throws Exception {
        // Initialize the database
        profileService.save(profile);

        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Get the profile
        restProfileMockMvc.perform(delete("/api/profiles/{id}", profile.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Profile> profiles = profileRepository.findAll();
        assertThat(profiles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
