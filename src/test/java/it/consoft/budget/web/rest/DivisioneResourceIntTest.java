package it.consoft.budget.web.rest;

import it.consoft.budget.BudgetApp;

import it.consoft.budget.domain.Divisione;
import it.consoft.budget.repository.DivisioneRepository;
import it.consoft.budget.service.DivisioneService;
import it.consoft.budget.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DivisioneResource REST controller.
 *
 * @see DivisioneResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BudgetApp.class)
public class DivisioneResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private DivisioneRepository divisioneRepository;

    @Autowired
    private DivisioneService divisioneService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDivisioneMockMvc;

    private Divisione divisione;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DivisioneResource divisioneResource = new DivisioneResource(divisioneService);
        this.restDivisioneMockMvc = MockMvcBuilders.standaloneSetup(divisioneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Divisione createEntity(EntityManager em) {
        Divisione divisione = new Divisione()
            .nome(DEFAULT_NOME);
        return divisione;
    }

    @Before
    public void initTest() {
        divisione = createEntity(em);
    }

    @Test
    @Transactional
    public void createDivisione() throws Exception {
        int databaseSizeBeforeCreate = divisioneRepository.findAll().size();

        // Create the Divisione
        restDivisioneMockMvc.perform(post("/api/divisiones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisione)))
            .andExpect(status().isCreated());

        // Validate the Divisione in the database
        List<Divisione> divisioneList = divisioneRepository.findAll();
        assertThat(divisioneList).hasSize(databaseSizeBeforeCreate + 1);
        Divisione testDivisione = divisioneList.get(divisioneList.size() - 1);
        assertThat(testDivisione.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createDivisioneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = divisioneRepository.findAll().size();

        // Create the Divisione with an existing ID
        divisione.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDivisioneMockMvc.perform(post("/api/divisiones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisione)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Divisione> divisioneList = divisioneRepository.findAll();
        assertThat(divisioneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = divisioneRepository.findAll().size();
        // set the field null
        divisione.setNome(null);

        // Create the Divisione, which fails.

        restDivisioneMockMvc.perform(post("/api/divisiones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisione)))
            .andExpect(status().isBadRequest());

        List<Divisione> divisioneList = divisioneRepository.findAll();
        assertThat(divisioneList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDivisiones() throws Exception {
        // Initialize the database
        divisioneRepository.saveAndFlush(divisione);

        // Get all the divisioneList
        restDivisioneMockMvc.perform(get("/api/divisiones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(divisione.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }

    @Test
    @Transactional
    public void getDivisione() throws Exception {
        // Initialize the database
        divisioneRepository.saveAndFlush(divisione);

        // Get the divisione
        restDivisioneMockMvc.perform(get("/api/divisiones/{id}", divisione.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(divisione.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDivisione() throws Exception {
        // Get the divisione
        restDivisioneMockMvc.perform(get("/api/divisiones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDivisione() throws Exception {
        // Initialize the database
        divisioneService.save(divisione);

        int databaseSizeBeforeUpdate = divisioneRepository.findAll().size();

        // Update the divisione
        Divisione updatedDivisione = divisioneRepository.findOne(divisione.getId());
        updatedDivisione
            .nome(UPDATED_NOME);

        restDivisioneMockMvc.perform(put("/api/divisiones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDivisione)))
            .andExpect(status().isOk());

        // Validate the Divisione in the database
        List<Divisione> divisioneList = divisioneRepository.findAll();
        assertThat(divisioneList).hasSize(databaseSizeBeforeUpdate);
        Divisione testDivisione = divisioneList.get(divisioneList.size() - 1);
        assertThat(testDivisione.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingDivisione() throws Exception {
        int databaseSizeBeforeUpdate = divisioneRepository.findAll().size();

        // Create the Divisione

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDivisioneMockMvc.perform(put("/api/divisiones")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisione)))
            .andExpect(status().isCreated());

        // Validate the Divisione in the database
        List<Divisione> divisioneList = divisioneRepository.findAll();
        assertThat(divisioneList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDivisione() throws Exception {
        // Initialize the database
        divisioneService.save(divisione);

        int databaseSizeBeforeDelete = divisioneRepository.findAll().size();

        // Get the divisione
        restDivisioneMockMvc.perform(delete("/api/divisiones/{id}", divisione.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Divisione> divisioneList = divisioneRepository.findAll();
        assertThat(divisioneList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Divisione.class);
        Divisione divisione1 = new Divisione();
        divisione1.setId(1L);
        Divisione divisione2 = new Divisione();
        divisione2.setId(divisione1.getId());
        assertThat(divisione1).isEqualTo(divisione2);
        divisione2.setId(2L);
        assertThat(divisione1).isNotEqualTo(divisione2);
        divisione1.setId(null);
        assertThat(divisione1).isNotEqualTo(divisione2);
    }
}
