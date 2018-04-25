package fr.sciencesu.memoire.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.sciencesu.memoire.domain.Secteur;

import fr.sciencesu.memoire.repository.SecteurRepository;
import fr.sciencesu.memoire.web.rest.errors.BadRequestAlertException;
import fr.sciencesu.memoire.web.rest.util.HeaderUtil;
import fr.sciencesu.memoire.service.dto.SecteurDTO;
import fr.sciencesu.memoire.service.mapper.SecteurMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Secteur.
 */
@RestController
@RequestMapping("/api")
public class SecteurResource {

    private final Logger log = LoggerFactory.getLogger(SecteurResource.class);

    private static final String ENTITY_NAME = "secteur";

    private final SecteurRepository secteurRepository;

    private final SecteurMapper secteurMapper;

    public SecteurResource(SecteurRepository secteurRepository, SecteurMapper secteurMapper) {
        this.secteurRepository = secteurRepository;
        this.secteurMapper = secteurMapper;
    }

    /**
     * POST  /secteurs : Create a new secteur.
     *
     * @param secteurDTO the secteurDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new secteurDTO, or with status 400 (Bad Request) if the secteur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/secteurs")
    @Timed
    public ResponseEntity<SecteurDTO> createSecteur(@Valid @RequestBody SecteurDTO secteurDTO) throws URISyntaxException {
        log.debug("REST request to save Secteur : {}", secteurDTO);
        if (secteurDTO.getId() != null) {
            throw new BadRequestAlertException("A new secteur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Secteur secteur = secteurMapper.toEntity(secteurDTO);
        secteur = secteurRepository.save(secteur);
        SecteurDTO result = secteurMapper.toDto(secteur);
        return ResponseEntity.created(new URI("/api/secteurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /secteurs : Updates an existing secteur.
     *
     * @param secteurDTO the secteurDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated secteurDTO,
     * or with status 400 (Bad Request) if the secteurDTO is not valid,
     * or with status 500 (Internal Server Error) if the secteurDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/secteurs")
    @Timed
    public ResponseEntity<SecteurDTO> updateSecteur(@Valid @RequestBody SecteurDTO secteurDTO) throws URISyntaxException {
        log.debug("REST request to update Secteur : {}", secteurDTO);
        if (secteurDTO.getId() == null) {
            return createSecteur(secteurDTO);
        }
        Secteur secteur = secteurMapper.toEntity(secteurDTO);
        secteur = secteurRepository.save(secteur);
        SecteurDTO result = secteurMapper.toDto(secteur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, secteurDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /secteurs : get all the secteurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of secteurs in body
     */
    @GetMapping("/secteurs")
    @Timed
    public List<SecteurDTO> getAllSecteurs() {
        log.debug("REST request to get all Secteurs");
        List<Secteur> secteurs = secteurRepository.findAll();
        return secteurMapper.toDto(secteurs);
        }

    /**
     * GET  /secteurs/:id : get the "id" secteur.
     *
     * @param id the id of the secteurDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the secteurDTO, or with status 404 (Not Found)
     */
    @GetMapping("/secteurs/{id}")
    @Timed
    public ResponseEntity<SecteurDTO> getSecteur(@PathVariable Long id) {
        log.debug("REST request to get Secteur : {}", id);
        Secteur secteur = secteurRepository.findOne(id);
        SecteurDTO secteurDTO = secteurMapper.toDto(secteur);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(secteurDTO));
    }

    /**
     * DELETE  /secteurs/:id : delete the "id" secteur.
     *
     * @param id the id of the secteurDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/secteurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteSecteur(@PathVariable Long id) {
        log.debug("REST request to delete Secteur : {}", id);
        secteurRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
