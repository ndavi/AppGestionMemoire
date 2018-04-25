package fr.sciencesu.memoire.web.rest.jhipster;

import com.codahale.metrics.annotation.Timed;
import fr.sciencesu.memoire.domain.UserExtra;

import fr.sciencesu.memoire.repository.UserExtraRepository;
import fr.sciencesu.memoire.web.rest.errors.BadRequestAlertException;
import fr.sciencesu.memoire.web.rest.util.HeaderUtil;
import fr.sciencesu.memoire.service.dto.UserExtraDTO;
import fr.sciencesu.memoire.service.mapper.UserExtraMapper;
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
 * REST controller for managing UserExtra.
 */
@RestController
@RequestMapping("/api")
public class UserExtraResource {

    private final Logger log = LoggerFactory.getLogger(UserExtraResource.class);

    private static final String ENTITY_NAME = "userExtra";

    private final UserExtraRepository userExtraRepository;

    private final UserExtraMapper userExtraMapper;

    public UserExtraResource(UserExtraRepository userExtraRepository, UserExtraMapper userExtraMapper) {
        this.userExtraRepository = userExtraRepository;
        this.userExtraMapper = userExtraMapper;
    }

    /**
     * POST  /user-extras : Create a new userExtra.
     *
     * @param userExtraDTO the userExtraDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userExtraDTO, or with status 400 (Bad Request) if the userExtra has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-extras")
    @Timed
    public ResponseEntity<UserExtraDTO> createUserExtra(@RequestBody UserExtraDTO userExtraDTO) throws URISyntaxException {
        log.debug("REST request to save UserExtra : {}", userExtraDTO);
        if (userExtraDTO.getId() != null) {
            throw new BadRequestAlertException("A new userExtra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserExtra userExtra = userExtraMapper.toEntity(userExtraDTO);
        userExtra = userExtraRepository.save(userExtra);
        UserExtraDTO result = userExtraMapper.toDto(userExtra);
        return ResponseEntity.created(new URI("/api/user-extras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-extras : Updates an existing userExtra.
     *
     * @param userExtraDTO the userExtraDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userExtraDTO,
     * or with status 400 (Bad Request) if the userExtraDTO is not valid,
     * or with status 500 (Internal Server Error) if the userExtraDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-extras")
    @Timed
    public ResponseEntity<UserExtraDTO> updateUserExtra(@RequestBody UserExtraDTO userExtraDTO) throws URISyntaxException {
        log.debug("REST request to update UserExtra : {}", userExtraDTO);
        if (userExtraDTO.getId() == null) {
            return createUserExtra(userExtraDTO);
        }
        UserExtra userExtra = userExtraMapper.toEntity(userExtraDTO);
        userExtra = userExtraRepository.save(userExtra);
        UserExtraDTO result = userExtraMapper.toDto(userExtra);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userExtraDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-extras : get all the userExtras.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userExtras in body
     */
    @GetMapping("/user-extras")
    @Timed
    public List<UserExtraDTO> getAllUserExtras() {
        log.debug("REST request to get all UserExtras");
        List<UserExtra> userExtras = userExtraRepository.findAll();
        return userExtraMapper.toDto(userExtras);
        }

    /**
     * GET  /user-extras/:id : get the "id" userExtra.
     *
     * @param id the id of the userExtraDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userExtraDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-extras/{id}")
    @Timed
    public ResponseEntity<UserExtraDTO> getUserExtra(@PathVariable Long id) {
        log.debug("REST request to get UserExtra : {}", id);
        UserExtra userExtra = userExtraRepository.findOne(id);
        UserExtraDTO userExtraDTO = userExtraMapper.toDto(userExtra);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userExtraDTO));
    }

    /**
     * DELETE  /user-extras/:id : delete the "id" userExtra.
     *
     * @param id the id of the userExtraDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-extras/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserExtra(@PathVariable Long id) {
        log.debug("REST request to delete UserExtra : {}", id);
        userExtraRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
