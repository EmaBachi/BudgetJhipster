package it.consoft.budget.web.rest;

import it.consoft.budget.BudgetApp;

import it.consoft.budget.domain.Conto;
import it.consoft.budget.repository.ContoRepository;
import it.consoft.budget.service.ContoService;
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
 * Test class for the ContoResource REST controller.
 *
 * @see ContoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BudgetApp.class)
public class ContoResourceIntTest {

    private static final String DEFAULT_CODICE = "AAAAAAAAAA";
    private static final String UPDATED_CODICE = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIZIONE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIZIONE = "BBBBBBBBBB";

    @Autowired
    private ContoRepository contoRepository;

    @Autowired
    private ContoService contoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContoMockMvc;

    private Conto conto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContoResource contoResource = new ContoResource(contoService);
        this.restContoMockMvc = MockMvcBuilders.standaloneSetup(contoResource)
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
    public static Conto createEntity(EntityManager em) {
        Conto conto = new Conto()
            .codice(DEFAULT_CODICE)
            .nome(DEFAULT_NOME)
            .descrizione(DEFAULT_DESCRIZIONE);
        return conto;
    }

    @Before
    public void initTest() {
        conto = createEntity(em);
    }

    @Test
    @Transactional
    public void createConto() throws Exception {
        int databaseSizeBeforeCreate = contoRepository.findAll().size();

        // Create the Conto
        restContoMockMvc.perform(post("/api/contos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conto)))
            .andExpect(status().isCreated());

        // Validate the Conto in the database
        List<Conto> contoList = contoRepository.findAll();
        assertThat(contoList).hasSize(databaseSizeBeforeCreate + 1);
        Conto testConto = contoList.get(contoList.size() - 1);
        assertThat(testConto.getCodice()).isEqualTo(DEFAULT_CODICE);
        assertThat(testConto.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testConto.getDescrizione()).isEqualTo(DEFAULT_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void createContoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contoRepository.findAll().size();

        // Create the Conto with an existing ID
        conto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContoMockMvc.perform(post("/api/contos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conto)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Conto> contoList = contoRepository.findAll();
        assertThat(contoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContos() throws Exception {
        // Initialize the database
        contoRepository.saveAndFlush(conto);

        // Get all the contoList
        restContoMockMvc.perform(get("/api/contos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conto.getId().intValue())))
            .andExpect(jsonPath("$.[*].codice").value(hasItem(DEFAULT_CODICE.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descrizione").value(hasItem(DEFAULT_DESCRIZIONE.toString())));
    }

    @Test
    @Transactional
    public void getConto() throws Exception {
        // Initialize the database
        contoRepository.saveAndFlush(conto);

        // Get the conto
        restContoMockMvc.perform(get("/api/contos/{id}", conto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conto.getId().intValue()))
            .andExpect(jsonPath("$.codice").value(DEFAULT_CODICE.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descrizione").value(DEFAULT_DESCRIZIONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConto() throws Exception {
        // Get the conto
        restContoMockMvc.perform(get("/api/contos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConto() throws Exception {
        // Initialize the database
        contoService.save(conto);

        int databaseSizeBeforeUpdate = contoRepository.findAll().size();

        // Update the conto
        Conto updatedConto = contoRepository.findOne(conto.getId());
        updatedConto
            .codice(UPDATED_CODICE)
            .nome(UPDATED_NOME)
            .descrizione(UPDATED_DESCRIZIONE);

        restContoMockMvc.perform(put("/api/contos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConto)))
            .andExpect(status().isOk());

        // Validate the Conto in the database
        List<Conto> contoList = contoRepository.findAll();
        assertThat(contoList).hasSize(databaseSizeBeforeUpdate);
        Conto testConto = contoList.get(contoList.size() - 1);
        assertThat(testConto.getCodice()).isEqualTo(UPDATED_CODICE);
        assertThat(testConto.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testConto.getDescrizione()).isEqualTo(UPDATED_DESCRIZIONE);
    }

    @Test
    @Transactional
    public void updateNonExistingConto() throws Exception {
        int databaseSizeBeforeUpdate = contoRepository.findAll().size();

        // Create the Conto

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContoMockMvc.perform(put("/api/contos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conto)))
            .andExpect(status().isCreated());

        // Validate the Conto in the database
        List<Conto> contoList = contoRepository.findAll();
        assertThat(contoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConto() throws Exception {
        // Initialize the database
        contoService.save(conto);

        int databaseSizeBeforeDelete = contoRepository.findAll().size();

        // Get the conto
        restContoMockMvc.perform(delete("/api/contos/{id}", conto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Conto> contoList = contoRepository.findAll();
        assertThat(contoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Conto.class);
        Conto conto1 = new Conto();
        conto1.setId(1L);
        Conto conto2 = new Conto();
        conto2.setId(conto1.getId());
        assertThat(conto1).isEqualTo(conto2);
        conto2.setId(2L);
        assertThat(conto1).isNotEqualTo(conto2);
        conto1.setId(null);
        assertThat(conto1).isNotEqualTo(conto2);
    }
}
