package fr.sciencesu.memoire.web.rest;

import fr.sciencesu.memoire.AppGestionMemoireApp;

import fr.sciencesu.memoire.domain.Secteur;
import fr.sciencesu.memoire.repository.SecteurRepository;
import fr.sciencesu.memoire.service.dto.SecteurDTO;
import fr.sciencesu.memoire.service.mapper.SecteurMapper;
import fr.sciencesu.memoire.web.rest.errors.ExceptionTranslator;

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

/**
 * Test class for the SecteurResource REST controller.
 *
 * @see SecteurResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppGestionMemoireApp.class)
public class SecteurResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private SecteurRepository secteurRepository;

    @Autowired
    private SecteurMapper secteurMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSecteurMockMvc;

    private Secteur secteur;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SecteurResource secteurResource = new SecteurResource(secteurRepository, secteurMapper);
        this.restSecteurMockMvc = MockMvcBuilders.standaloneSetup(secteurResource)
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
    public static Secteur createEntity(EntityManager em) {
        Secteur secteur = new Secteur()
            .nom(DEFAULT_NOM);
        return secteur;
    }

    @Before
    public void initTest() {
        secteur = createEntity(em);
    }

    @Test
    @Transactional
    public void createSecteur() throws Exception {
        int databaseSizeBeforeCreate = secteurRepository.findAll().size();

        // Create the Secteur
        SecteurDTO secteurDTO = secteurMapper.toDto(secteur);
        restSecteurMockMvc.perform(post("/api/secteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secteurDTO)))
            .andExpect(status().isCreated());

        // Validate the Secteur in the database
        List<Secteur> secteurList = secteurRepository.findAll();
        assertThat(secteurList).hasSize(databaseSizeBeforeCreate + 1);
        Secteur testSecteur = secteurList.get(secteurList.size() - 1);
        assertThat(testSecteur.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createSecteurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = secteurRepository.findAll().size();

        // Create the Secteur with an existing ID
        secteur.setId(1L);
        SecteurDTO secteurDTO = secteurMapper.toDto(secteur);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSecteurMockMvc.perform(post("/api/secteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secteurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Secteur in the database
        List<Secteur> secteurList = secteurRepository.findAll();
        assertThat(secteurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = secteurRepository.findAll().size();
        // set the field null
        secteur.setNom(null);

        // Create the Secteur, which fails.
        SecteurDTO secteurDTO = secteurMapper.toDto(secteur);

        restSecteurMockMvc.perform(post("/api/secteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secteurDTO)))
            .andExpect(status().isBadRequest());

        List<Secteur> secteurList = secteurRepository.findAll();
        assertThat(secteurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSecteurs() throws Exception {
        // Initialize the database
        secteurRepository.saveAndFlush(secteur);

        // Get all the secteurList
        restSecteurMockMvc.perform(get("/api/secteurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(secteur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())));
    }

    @Test
    @Transactional
    public void getSecteur() throws Exception {
        // Initialize the database
        secteurRepository.saveAndFlush(secteur);

        // Get the secteur
        restSecteurMockMvc.perform(get("/api/secteurs/{id}", secteur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(secteur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSecteur() throws Exception {
        // Get the secteur
        restSecteurMockMvc.perform(get("/api/secteurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSecteur() throws Exception {
        // Initialize the database
        secteurRepository.saveAndFlush(secteur);
        int databaseSizeBeforeUpdate = secteurRepository.findAll().size();

        // Update the secteur
        Secteur updatedSecteur = secteurRepository.findOne(secteur.getId());
        // Disconnect from session so that the updates on updatedSecteur are not directly saved in db
        em.detach(updatedSecteur);
        updatedSecteur
            .nom(UPDATED_NOM);
        SecteurDTO secteurDTO = secteurMapper.toDto(updatedSecteur);

        restSecteurMockMvc.perform(put("/api/secteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secteurDTO)))
            .andExpect(status().isOk());

        // Validate the Secteur in the database
        List<Secteur> secteurList = secteurRepository.findAll();
        assertThat(secteurList).hasSize(databaseSizeBeforeUpdate);
        Secteur testSecteur = secteurList.get(secteurList.size() - 1);
        assertThat(testSecteur.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingSecteur() throws Exception {
        int databaseSizeBeforeUpdate = secteurRepository.findAll().size();

        // Create the Secteur
        SecteurDTO secteurDTO = secteurMapper.toDto(secteur);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSecteurMockMvc.perform(put("/api/secteurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secteurDTO)))
            .andExpect(status().isCreated());

        // Validate the Secteur in the database
        List<Secteur> secteurList = secteurRepository.findAll();
        assertThat(secteurList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSecteur() throws Exception {
        // Initialize the database
        secteurRepository.saveAndFlush(secteur);
        int databaseSizeBeforeDelete = secteurRepository.findAll().size();

        // Get the secteur
        restSecteurMockMvc.perform(delete("/api/secteurs/{id}", secteur.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Secteur> secteurList = secteurRepository.findAll();
        assertThat(secteurList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Secteur.class);
        Secteur secteur1 = new Secteur();
        secteur1.setId(1L);
        Secteur secteur2 = new Secteur();
        secteur2.setId(secteur1.getId());
        assertThat(secteur1).isEqualTo(secteur2);
        secteur2.setId(2L);
        assertThat(secteur1).isNotEqualTo(secteur2);
        secteur1.setId(null);
        assertThat(secteur1).isNotEqualTo(secteur2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecteurDTO.class);
        SecteurDTO secteurDTO1 = new SecteurDTO();
        secteurDTO1.setId(1L);
        SecteurDTO secteurDTO2 = new SecteurDTO();
        assertThat(secteurDTO1).isNotEqualTo(secteurDTO2);
        secteurDTO2.setId(secteurDTO1.getId());
        assertThat(secteurDTO1).isEqualTo(secteurDTO2);
        secteurDTO2.setId(2L);
        assertThat(secteurDTO1).isNotEqualTo(secteurDTO2);
        secteurDTO1.setId(null);
        assertThat(secteurDTO1).isNotEqualTo(secteurDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(secteurMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(secteurMapper.fromId(null)).isNull();
    }
}
