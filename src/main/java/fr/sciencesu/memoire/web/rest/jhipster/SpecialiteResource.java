package fr.sciencesu.memoire.web.rest.jhipster;

import com.codahale.metrics.annotation.Timed;
import fr.sciencesu.memoire.domain.Specialite;

import fr.sciencesu.memoire.repository.SpecialiteRepository;
import fr.sciencesu.memoire.web.rest.errors.BadRequestAlertException;
import fr.sciencesu.memoire.web.rest.util.HeaderUtil;
import fr.sciencesu.memoire.service.dto.SpecialiteDTO;
import fr.sciencesu.memoire.service.mapper.SpecialiteMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Specialite.
 */
@RestController
@RequestMapping("/api")
public class SpecialiteResource {

    private final Logger log = LoggerFactory.getLogger(SpecialiteResource.class);

    private static final String ENTITY_NAME = "specialite";

    private final SpecialiteRepository specialiteRepository;

    private final SpecialiteMapper specialiteMapper;

    public SpecialiteResource(SpecialiteRepository specialiteRepository, SpecialiteMapper specialiteMapper) {
        this.specialiteRepository = specialiteRepository;
        this.specialiteMapper = specialiteMapper;
    }

    /**
     * POST  /specialites : Create a new specialite.
     *
     * @param specialiteDTO the specialiteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new specialiteDTO, or with status 400 (Bad Request) if the specialite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/specialites")
    @Timed
    public ResponseEntity<SpecialiteDTO> createSpecialite(@RequestBody SpecialiteDTO specialiteDTO) throws URISyntaxException {
        log.debug("REST request to save Specialite : {}", specialiteDTO);
        if (specialiteDTO.getId() != null) {
            throw new BadRequestAlertException("A new specialite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Specialite specialite = specialiteMapper.toEntity(specialiteDTO);
        specialite = specialiteRepository.save(specialite);
        SpecialiteDTO result = specialiteMapper.toDto(specialite);
        return ResponseEntity.created(new URI("/api/specialites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /specialites : Updates an existing specialite.
     *
     * @param specialiteDTO the specialiteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated specialiteDTO,
     * or with status 400 (Bad Request) if the specialiteDTO is not valid,
     * or with status 500 (Internal Server Error) if the specialiteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/specialites")
    @Timed
    public ResponseEntity<SpecialiteDTO> updateSpecialite(@RequestBody SpecialiteDTO specialiteDTO) throws URISyntaxException {
        log.debug("REST request to update Specialite : {}", specialiteDTO);
        if (specialiteDTO.getId() == null) {
            return createSpecialite(specialiteDTO);
        }
        Specialite specialite = specialiteMapper.toEntity(specialiteDTO);
        specialite = specialiteRepository.save(specialite);
        SpecialiteDTO result = specialiteMapper.toDto(specialite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, specialiteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /specialites : get all the specialites.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of specialites in body
     */
    @GetMapping("/specialites")
    @Timed
    public List<SpecialiteDTO> getAllSpecialites() {
        log.debug("REST request to get all Specialites");
        List<Specialite> specialites = specialiteRepository.findAll();
        return specialiteMapper.toDto(specialites);
        }

    /**
     * GET  /specialites/:id : get the "id" specialite.
     *
     * @param id the id of the specialiteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the specialiteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/specialites/{id}")
    @Timed
    public ResponseEntity<SpecialiteDTO> getSpecialite(@PathVariable Long id) {
        log.debug("REST request to get Specialite : {}", id);
        Specialite specialite = specialiteRepository.findOne(id);
        SpecialiteDTO specialiteDTO = specialiteMapper.toDto(specialite);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(specialiteDTO));
    }

    /**
     * DELETE  /specialites/:id : delete the "id" specialite.
     *
     * @param id the id of the specialiteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/specialites/{id}")
    @Timed
    public ResponseEntity<Void> deleteSpecialite(@PathVariable Long id) {
        log.debug("REST request to delete Specialite : {}", id);
        specialiteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
