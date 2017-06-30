package it.consoft.budget.web.rest;

import it.consoft.budget.BudgetApp;

import it.consoft.budget.domain.ContoContabile;
import it.consoft.budget.repository.ContoContabileRepository;
import it.consoft.budget.service.ContoContabileService;
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
 * Test class for the ContoContabileResource REST controller.
 *
 * @see ContoContabileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BudgetApp.class)
public class ContoContabileResourceIntTest {

    private static final String DEFAULT_CODICE = "AAAAAAAAAA";
    private static final String UPDATED_CODICE = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE = "BBBBBBBBBB";

    @Autowired
    private ContoContabileRepository contoContabileRepository;

    @Autowired
    private ContoContabileService contoContabileService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContoContabileMockMvc;

    private ContoContabile contoContabile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContoContabileResource contoContabileResource = new ContoContabileResource(contoContabileService);
        this.restContoContabileMockMvc = MockMvcBuilders.standaloneSetup(contoContabileResource)
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
    public static ContoContabile createEntity(EntityManager em) {
        ContoContabile contoContabile = new ContoContabile()
            .codice(DEFAULT_CODICE)
            .nome(DEFAULT_NOME)
            .descrizione(DEFAULT_DESCRIZIONE);
        return contoContabile;
    }

    @Before
    public void initTest() {
        contoContabile = createEntity(em);
    }

    @Test
    @Transactional
    public void createContoContabile() throws Exception {
        int databaseSizeBeforeCreate = contoContabileRepository.findAll().size();

        // Create the ContoContabile
        restContoContabileMockMvc.perform(post("/api/conto-contabiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contoContabile)))
            .andExpect(status().isCreated());

        // Validate the ContoContabile in the database
        List<ContoContabile> contoContabileList = contoContabileRepository.findAll();
        assertThat(contoContabileList).hasSize(databaseSizeBeforeCreate + 1);
        ContoContabile testContoContabile = contoContabileList.get(contoContabileList.size() - 1);
        assertThat(testContoContabile.getCodice()).isEqualTo(DEFAULT_CODICE);
        assertThat(testContoContabile.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testContoContabile.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void createContoContabileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contoContabileRepository.findAll().size();

        // Create the ContoContabile with an existing ID
        contoContabile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContoContabileMockMvc.perform(post("/api/conto-contabiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contoContabile)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ContoContabile> contoContabileList = contoContabileRepository.findAll();
        assertThat(contoContabileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContoContabiles() throws Exception {
        // Initialize the database
        contoContabileRepository.saveAndFlush(contoContabile);

        // Get all the contoContabileList
        restContoContabileMockMvc.perform(get("/api/conto-contabiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contoContabile.getId().intValue())))
            .andExpect(jsonPath("$.[*].codice").value(hasItem(DEFAULT_CODICE.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE.toString())));
    }

    @Test
    @Transactional
    public void getContoContabile() throws Exception {
        // Initialize the database
        contoContabileRepository.saveAndFlush(contoContabile);

        // Get the contoContabile
        restContoContabileMockMvc.perform(get("/api/conto-contabiles/{id}", contoContabile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contoContabile.getId().intValue()))
            .andExpect(jsonPath("$.codice").value(DEFAULT_CODICE.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descrizione").value(DEFAULT_DESCRIZIONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContoContabile() throws Exception {
        // Get the contoContabile
        restContoContabileMockMvc.perform(get("/api/conto-contabiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContoContabile() throws Exception {
        // Initialize the database
        contoContabileService.save(contoContabile);

        int databaseSizeBeforeUpdate = contoContabileRepository.findAll().size();

        // Update the contoContabile
        ContoContabile updatedContoContabile = contoContabileRepository.findOne(contoContabile.getId());
        updatedContoContabile
            .codice(UPDATED_CODICE)
            .nome(UPDATED_NOME)
            .descrizione(UPDATED_DESCRIZIONE);

        restContoContabileMockMvc.perform(put("/api/conto-contabiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContoContabile)))
            .andExpect(status().isOk());

        // Validate the ContoContabile in the database
        List<ContoContabile> contoContabileList = contoContabileRepository.findAll();
        assertThat(contoContabileList).hasSize(databaseSizeBeforeUpdate);
        ContoContabile testContoContabile = contoContabileList.get(contoContabileList.size() - 1);
        assertThat(testContoContabile.getCodice()).isEqualTo(UPDATED_CODICE);
        assertThat(testContoContabile.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testContoContabile.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void updateNonExistingContoContabile() throws Exception {
        int databaseSizeBeforeUpdate = contoContabileRepository.findAll().size();

        // Create the ContoContabile

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContoContabileMockMvc.perform(put("/api/conto-contabiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contoContabile)))
            .andExpect(status().isCreated());

        // Validate the ContoContabile in the database
        List<ContoContabile> contoContabileList = contoContabileRepository.findAll();
        assertThat(contoContabileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContoContabile() throws Exception {
        // Initialize the database
        contoContabileService.save(contoContabile);

        int databaseSizeBeforeDelete = contoContabileRepository.findAll().size();

        // Get the contoContabile
        restContoContabileMockMvc.perform(delete("/api/conto-contabiles/{id}", contoContabile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContoContabile> contoContabileList = contoContabileRepository.findAll();
        assertThat(contoContabileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContoContabile.class);
        ContoContabile contoContabile1 = new ContoContabile();
        contoContabile1.setId(1L);
        ContoContabile contoContabile2 = new ContoContabile();
        contoContabile2.setId(contoContabile1.getId());
        assertThat(contoContabile1).isEqualTo(contoContabile2);
        contoContabile2.setId(2L);
        assertThat(contoContabile1).isNotEqualTo(contoContabile2);
        contoContabile1.setId(null);
        assertThat(contoContabile1).isNotEqualTo(contoContabile2);
    }
}
