package it.consoft.budget.web.rest;

import it.consoft.budget.BudgetApp;

import it.consoft.budget.domain.Responsabile;
import it.consoft.budget.repository.ResponsabileRepository;
import it.consoft.budget.service.ResponsabileService;
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
 * Test class for the ResponsabileResource REST controller.
 *
 * @see ResponsabileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BudgetApp.class)
public class ResponsabileResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_COGNOME = "AAAAAAAAAA";
    private static final String UPDATED_COGNOME = "BBBBBBBBBB";

    @Autowired
    private ResponsabileRepository responsabileRepository;

    @Autowired
    private ResponsabileService responsabileService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restResponsabileMockMvc;

    private Responsabile responsabile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResponsabileResource responsabileResource = new ResponsabileResource(responsabileService);
        this.restResponsabileMockMvc = MockMvcBuilders.standaloneSetup(responsabileResource)
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
    public static Responsabile createEntity(EntityManager em) {
        Responsabile responsabile = new Responsabile()
            .nome(DEFAULT_NOME)
            .cognome(DEFAULT_COGNOME);
        return responsabile;
    }

    @Before
    public void initTest() {
        responsabile = createEntity(em);
    }

    @Test
    @Transactional
    public void createResponsabile() throws Exception {
        int databaseSizeBeforeCreate = responsabileRepository.findAll().size();

        // Create the Responsabile
        restResponsabileMockMvc.perform(post("/api/responsabiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsabile)))
            .andExpect(status().isCreated());

        // Validate the Responsabile in the database
        List<Responsabile> responsabileList = responsabileRepository.findAll();
        assertThat(responsabileList).hasSize(databaseSizeBeforeCreate + 1);
        Responsabile testResponsabile = responsabileList.get(responsabileList.size() - 1);
        assertThat(testResponsabile.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testResponsabile.getCognome()).isEqualTo(DEFAULT_COGNOME);
    }

    @Test
    @Transactional
    public void createResponsabileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = responsabileRepository.findAll().size();

        // Create the Responsabile with an existing ID
        responsabile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsabileMockMvc.perform(post("/api/responsabiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsabile)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Responsabile> responsabileList = responsabileRepository.findAll();
        assertThat(responsabileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsabileRepository.findAll().size();
        // set the field null
        responsabile.setNome(null);

        // Create the Responsabile, which fails.

        restResponsabileMockMvc.perform(post("/api/responsabiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsabile)))
            .andExpect(status().isBadRequest());

        List<Responsabile> responsabileList = responsabileRepository.findAll();
        assertThat(responsabileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCognomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsabileRepository.findAll().size();
        // set the field null
        responsabile.setCognome(null);

        // Create the Responsabile, which fails.

        restResponsabileMockMvc.perform(post("/api/responsabiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsabile)))
            .andExpect(status().isBadRequest());

        List<Responsabile> responsabileList = responsabileRepository.findAll();
        assertThat(responsabileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResponsabiles() throws Exception {
        // Initialize the database
        responsabileRepository.saveAndFlush(responsabile);

        // Get all the responsabileList
        restResponsabileMockMvc.perform(get("/api/responsabiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsabile.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].cognome").value(hasItem(DEFAULT_COGNOME.toString())));
    }

    @Test
    @Transactional
    public void getResponsabile() throws Exception {
        // Initialize the database
        responsabileRepository.saveAndFlush(responsabile);

        // Get the responsabile
        restResponsabileMockMvc.perform(get("/api/responsabiles/{id}", responsabile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(responsabile.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.cognome").value(DEFAULT_COGNOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResponsabile() throws Exception {
        // Get the responsabile
        restResponsabileMockMvc.perform(get("/api/responsabiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResponsabile() throws Exception {
        // Initialize the database
        responsabileService.save(responsabile);

        int databaseSizeBeforeUpdate = responsabileRepository.findAll().size();

        // Update the responsabile
        Responsabile updatedResponsabile = responsabileRepository.findOne(responsabile.getId());
        updatedResponsabile
            .nome(UPDATED_NOME)
            .cognome(UPDATED_COGNOME);

        restResponsabileMockMvc.perform(put("/api/responsabiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResponsabile)))
            .andExpect(status().isOk());

        // Validate the Responsabile in the database
        List<Responsabile> responsabileList = responsabileRepository.findAll();
        assertThat(responsabileList).hasSize(databaseSizeBeforeUpdate);
        Responsabile testResponsabile = responsabileList.get(responsabileList.size() - 1);
        assertThat(testResponsabile.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testResponsabile.getCognome()).isEqualTo(UPDATED_COGNOME);
    }

    @Test
    @Transactional
    public void updateNonExistingResponsabile() throws Exception {
        int databaseSizeBeforeUpdate = responsabileRepository.findAll().size();

        // Create the Responsabile

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restResponsabileMockMvc.perform(put("/api/responsabiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsabile)))
            .andExpect(status().isCreated());

        // Validate the Responsabile in the database
        List<Responsabile> responsabileList = responsabileRepository.findAll();
        assertThat(responsabileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteResponsabile() throws Exception {
        // Initialize the database
        responsabileService.save(responsabile);

        int databaseSizeBeforeDelete = responsabileRepository.findAll().size();

        // Get the responsabile
        restResponsabileMockMvc.perform(delete("/api/responsabiles/{id}", responsabile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Responsabile> responsabileList = responsabileRepository.findAll();
        assertThat(responsabileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Responsabile.class);
        Responsabile responsabile1 = new Responsabile();
        responsabile1.setId(1L);
        Responsabile responsabile2 = new Responsabile();
        responsabile2.setId(responsabile1.getId());
        assertThat(responsabile1).isEqualTo(responsabile2);
        responsabile2.setId(2L);
        assertThat(responsabile1).isNotEqualTo(responsabile2);
        responsabile1.setId(null);
        assertThat(responsabile1).isNotEqualTo(responsabile2);
    }
}
