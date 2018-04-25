package fr.sciencesu.memoire.web.rest.jhipster;

import com.codahale.metrics.annotation.Timed;
import fr.sciencesu.memoire.domain.Memoire;

import fr.sciencesu.memoire.repository.MemoireRepository;
import fr.sciencesu.memoire.web.rest.errors.BadRequestAlertException;
import fr.sciencesu.memoire.web.rest.util.HeaderUtil;
import fr.sciencesu.memoire.web.rest.util.PaginationUtil;
import fr.sciencesu.memoire.service.dto.MemoireDTO;
import fr.sciencesu.memoire.service.mapper.MemoireMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Memoire.
 */
@RestController
@RequestMapping("/api")
public class MemoireResource {

    private final Logger log = LoggerFactory.getLogger(MemoireResource.class);

    private static final String ENTITY_NAME = "memoire";


    private final MemoireRepository memoireRepository;
    private final MemoireMapper memoireMapper;

    public MemoireResource(MemoireRepository memoireRepository, MemoireMapper memoireMapper) {
        this.memoireRepository = memoireRepository;
        this.memoireMapper = memoireMapper;
    }

    /**
     * POST  /memoires : Create a new memoire.
     *
     * @param memoireDTO the memoireDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new memoireDTO, or with status 400 (Bad Request) if the memoire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/memoires")
    @Timed
    public ResponseEntity<MemoireDTO> createMemoire(@Valid @RequestBody MemoireDTO memoireDTO) throws URISyntaxException {
        log.debug("REST request to save Memoire : {}", memoireDTO);
        if (memoireDTO.getId() != null) {
            throw new BadRequestAlertException("A new memoire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Memoire memoire = memoireMapper.toEntity(memoireDTO);
        memoire = memoireRepository.save(memoire);
        MemoireDTO result = memoireMapper.toDto(memoire);
        return ResponseEntity.created(new URI("/api/memoires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /memoires : Updates an existing memoire.
     *
     * @param memoireDTO the memoireDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated memoireDTO,
     * or with status 400 (Bad Request) if the memoireDTO is not valid,
     * or with status 500 (Internal Server Error) if the memoireDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/memoires")
    @Timed
    public ResponseEntity<MemoireDTO> updateMemoire(@Valid @RequestBody MemoireDTO memoireDTO) throws URISyntaxException {
        log.debug("REST request to update Memoire : {}", memoireDTO);
        if (memoireDTO.getId() == null) {
            return createMemoire(memoireDTO);
        }
        Memoire memoire = memoireMapper.toEntity(memoireDTO);
        memoire = memoireRepository.save(memoire);
        MemoireDTO result = memoireMapper.toDto(memoire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, memoireDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /memoires : get all the memoires.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of memoires in body
     */
    @GetMapping("/memoires")
    @Timed
    public ResponseEntity<List<MemoireDTO>> getAllMemoires(Pageable pageable) {
        log.debug("REST request to get a page of Memoires");
        Page<Memoire> page = memoireRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/memoires");
        return new ResponseEntity<>(memoireMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /memoires/:id : get the "id" memoire.
     *
     * @param id the id of the memoireDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the memoireDTO, or with status 404 (Not Found)
     */
    @GetMapping("/memoires/{id}")
    @Timed
    public ResponseEntity<MemoireDTO> getMemoire(@PathVariable Long id) {
        log.debug("REST request to get Memoire : {}", id);
        Memoire memoire = memoireRepository.findOneWithEagerRelationships(id);
        MemoireDTO memoireDTO = memoireMapper.toDto(memoire);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(memoireDTO));
    }

    /**
     * DELETE  /memoires/:id : delete the "id" memoire.
     *
     * @param id the id of the memoireDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/memoires/{id}")
    @Timed
    public ResponseEntity<Void> deleteMemoire(@PathVariable Long id) {
        log.debug("REST request to delete Memoire : {}", id);
        memoireRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
