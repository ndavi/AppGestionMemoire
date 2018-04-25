package fr.sciencesu.memoire.web.rest;

import fr.sciencesu.memoire.AppGestionMemoireApp;

import fr.sciencesu.memoire.domain.Memoire;
import fr.sciencesu.memoire.repository.MemoireRepository;
import fr.sciencesu.memoire.service.dto.MemoireDTO;
import fr.sciencesu.memoire.service.mapper.MemoireMapper;
import fr.sciencesu.memoire.web.rest.errors.ExceptionTranslator;

import fr.sciencesu.memoire.web.rest.jhipster.MemoireResource;
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

import static fr.sciencesu.memoire.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.sciencesu.memoire.domain.enumeration.Langue;
/**
 * Test class for the MemoireResource REST controller.
 *
 * @see MemoireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppGestionMemoireApp.class)
public class MemoireResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_SUJET = "AAAAAAAAAA";
    private static final String UPDATED_SUJET = "BBBBBBBBBB";

    private static final Langue DEFAULT_LANGUE = Langue.FRANCAIS;
    private static final Langue UPDATED_LANGUE = Langue.ANGLAIS;

    private static final Boolean DEFAULT_CONFIDENTIEL = false;
    private static final Boolean UPDATED_CONFIDENTIEL = true;

    @Autowired
    private MemoireRepository memoireRepository;

    @Autowired
    private MemoireMapper memoireMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMemoireMockMvc;

    private Memoire memoire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MemoireResource memoireResource = new MemoireResource(memoireRepository, memoireMapper);
        this.restMemoireMockMvc = MockMvcBuilders.standaloneSetup(memoireResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Memoire createEntity(EntityManager em) {
        Memoire memoire = new Memoire()
            .nom(DEFAULT_NOM)
            .sujet(DEFAULT_SUJET)
            .langue(DEFAULT_LANGUE)
            .confidentiel(DEFAULT_CONFIDENTIEL);
        return memoire;
    }

    @Before
    public void initTest() {
        memoire = createEntity(em);
    }

    @Test
    @Transactional
    public void createMemoire() throws Exception {
        int databaseSizeBeforeCreate = memoireRepository.findAll().size();

        // Create the Memoire
        MemoireDTO memoireDTO = memoireMapper.toDto(memoire);
        restMemoireMockMvc.perform(post("/api/memoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memoireDTO)))
            .andExpect(status().isCreated());

        // Validate the Memoire in the database
        List<Memoire> memoireList = memoireRepository.findAll();
        assertThat(memoireList).hasSize(databaseSizeBeforeCreate + 1);
        Memoire testMemoire = memoireList.get(memoireList.size() - 1);
        assertThat(testMemoire.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMemoire.getSujet()).isEqualTo(DEFAULT_SUJET);
        assertThat(testMemoire.getLangue()).isEqualTo(DEFAULT_LANGUE);
        assertThat(testMemoire.isConfidentiel()).isEqualTo(DEFAULT_CONFIDENTIEL);
    }

    @Test
    @Transactional
    public void createMemoireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = memoireRepository.findAll().size();

        // Create the Memoire with an existing ID
        memoire.setId(1L);
        MemoireDTO memoireDTO = memoireMapper.toDto(memoire);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemoireMockMvc.perform(post("/api/memoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memoireDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Memoire in the database
        List<Memoire> memoireList = memoireRepository.findAll();
        assertThat(memoireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = memoireRepository.findAll().size();
        // set the field null
        memoire.setNom(null);

        // Create the Memoire, which fails.
        MemoireDTO memoireDTO = memoireMapper.toDto(memoire);

        restMemoireMockMvc.perform(post("/api/memoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memoireDTO)))
            .andExpect(status().isBadRequest());

        List<Memoire> memoireList = memoireRepository.findAll();
        assertThat(memoireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSujetIsRequired() throws Exception {
        int databaseSizeBeforeTest = memoireRepository.findAll().size();
        // set the field null
        memoire.setSujet(null);

        // Create the Memoire, which fails.
        MemoireDTO memoireDTO = memoireMapper.toDto(memoire);

        restMemoireMockMvc.perform(post("/api/memoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memoireDTO)))
            .andExpect(status().isBadRequest());

        List<Memoire> memoireList = memoireRepository.findAll();
        assertThat(memoireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLangueIsRequired() throws Exception {
        int databaseSizeBeforeTest = memoireRepository.findAll().size();
        // set the field null
        memoire.setLangue(null);

        // Create the Memoire, which fails.
        MemoireDTO memoireDTO = memoireMapper.toDto(memoire);

        restMemoireMockMvc.perform(post("/api/memoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memoireDTO)))
            .andExpect(status().isBadRequest());

        List<Memoire> memoireList = memoireRepository.findAll();
        assertThat(memoireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMemoires() throws Exception {
        // Initialize the database
        memoireRepository.saveAndFlush(memoire);

        // Get all the memoireList
        restMemoireMockMvc.perform(get("/api/memoires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memoire.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].sujet").value(hasItem(DEFAULT_SUJET.toString())))
            .andExpect(jsonPath("$.[*].langue").value(hasItem(DEFAULT_LANGUE.toString())))
            .andExpect(jsonPath("$.[*].confidentiel").value(hasItem(DEFAULT_CONFIDENTIEL.booleanValue())));
    }

    @Test
    @Transactional
    public void getMemoire() throws Exception {
        // Initialize the database
        memoireRepository.saveAndFlush(memoire);

        // Get the memoire
        restMemoireMockMvc.perform(get("/api/memoires/{id}", memoire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(memoire.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.sujet").value(DEFAULT_SUJET.toString()))
            .andExpect(jsonPath("$.langue").value(DEFAULT_LANGUE.toString()))
            .andExpect(jsonPath("$.confidentiel").value(DEFAULT_CONFIDENTIEL.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMemoire() throws Exception {
        // Get the memoire
        restMemoireMockMvc.perform(get("/api/memoires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMemoire() throws Exception {
        // Initialize the database
        memoireRepository.saveAndFlush(memoire);
        int databaseSizeBeforeUpdate = memoireRepository.findAll().size();

        // Update the memoire
        Memoire updatedMemoire = memoireRepository.findOne(memoire.getId());
        // Disconnect from session so that the updates on updatedMemoire are not directly saved in db
        em.detach(updatedMemoire);
        updatedMemoire
            .nom(UPDATED_NOM)
            .sujet(UPDATED_SUJET)
            .langue(UPDATED_LANGUE)
            .confidentiel(UPDATED_CONFIDENTIEL);
        MemoireDTO memoireDTO = memoireMapper.toDto(updatedMemoire);

        restMemoireMockMvc.perform(put("/api/memoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memoireDTO)))
            .andExpect(status().isOk());

        // Validate the Memoire in the database
        List<Memoire> memoireList = memoireRepository.findAll();
        assertThat(memoireList).hasSize(databaseSizeBeforeUpdate);
        Memoire testMemoire = memoireList.get(memoireList.size() - 1);
        assertThat(testMemoire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMemoire.getSujet()).isEqualTo(UPDATED_SUJET);
        assertThat(testMemoire.getLangue()).isEqualTo(UPDATED_LANGUE);
        assertThat(testMemoire.isConfidentiel()).isEqualTo(UPDATED_CONFIDENTIEL);
    }

    @Test
    @Transactional
    public void updateNonExistingMemoire() throws Exception {
        int databaseSizeBeforeUpdate = memoireRepository.findAll().size();

        // Create the Memoire
        MemoireDTO memoireDTO = memoireMapper.toDto(memoire);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMemoireMockMvc.perform(put("/api/memoires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memoireDTO)))
            .andExpect(status().isCreated());

        // Validate the Memoire in the database
        List<Memoire> memoireList = memoireRepository.findAll();
        assertThat(memoireList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMemoire() throws Exception {
        // Initialize the database
        memoireRepository.saveAndFlush(memoire);
        int databaseSizeBeforeDelete = memoireRepository.findAll().size();

        // Get the memoire
        restMemoireMockMvc.perform(delete("/api/memoires/{id}", memoire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Memoire> memoireList = memoireRepository.findAll();
        assertThat(memoireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Memoire.class);
        Memoire memoire1 = new Memoire();
        memoire1.setId(1L);
        Memoire memoire2 = new Memoire();
        memoire2.setId(memoire1.getId());
        assertThat(memoire1).isEqualTo(memoire2);
        memoire2.setId(2L);
        assertThat(memoire1).isNotEqualTo(memoire2);
        memoire1.setId(null);
        assertThat(memoire1).isNotEqualTo(memoire2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemoireDTO.class);
        MemoireDTO memoireDTO1 = new MemoireDTO();
        memoireDTO1.setId(1L);
        MemoireDTO memoireDTO2 = new MemoireDTO();
        assertThat(memoireDTO1).isNotEqualTo(memoireDTO2);
        memoireDTO2.setId(memoireDTO1.getId());
        assertThat(memoireDTO1).isEqualTo(memoireDTO2);
        memoireDTO2.setId(2L);
        assertThat(memoireDTO1).isNotEqualTo(memoireDTO2);
        memoireDTO1.setId(null);
        assertThat(memoireDTO1).isNotEqualTo(memoireDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(memoireMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(memoireMapper.fromId(null)).isNull();
    }
}
