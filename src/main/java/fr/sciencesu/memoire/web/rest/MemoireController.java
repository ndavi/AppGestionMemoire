package fr.sciencesu.memoire.web.rest;


import com.codahale.metrics.annotation.Timed;
import com.sun.mail.iap.Response;
import fr.sciencesu.memoire.domain.Memoire;
import fr.sciencesu.memoire.repository.MemoireRepository;
import fr.sciencesu.memoire.service.dto.MemoireDTO;
import fr.sciencesu.memoire.service.mapper.MemoireMapper;
import fr.sciencesu.memoire.service.util.AppProperties;
import fr.sciencesu.memoire.web.rest.jhipster.MemoireResource;
import fr.sciencesu.memoire.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/")
public class MemoireController {

    private final Logger log = LoggerFactory.getLogger(MemoireResource.class);

    private static final String ENTITY_NAME = "memoire";


    private final MemoireRepository memoireRepository;
    private final MemoireMapper memoireMapper;

    public MemoireController(MemoireRepository memoireRepository, MemoireMapper memoireMapper) {
        this.memoireRepository = memoireRepository;
        this.memoireMapper = memoireMapper;
    }

    /**
     * GET  /memoires/notConfiendential : get all memoires that are not confiendential.
     *
     * @param id the id of the memoireDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the memoireDTO, or with status 404 (Not Found)
     */
    @PostMapping("/memoires/upload")
    @Timed
    public ResponseEntity<List<MemoireDTO>> uploadMemoire(@RequestParam("file") List<MemoireDTO> memoires) {
        memoires.forEach(m -> {
            String savePath = AppProperties.savePath + "/" + m.getData().getOriginalFilename();
            File convFile = new File(savePath);
            try {
                m.getData().transferTo(convFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            m.setDataPath(savePath);
        });
        List<Memoire> memoiresEntity = memoireMapper.toEntity(memoires);
        memoiresEntity = memoireRepository.save(memoiresEntity);
        return ResponseEntity.ok().body(memoireMapper.toDto(memoiresEntity));
    }
}
