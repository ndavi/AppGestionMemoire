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
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/")
public class MemoireController {

    private final Logger log = LoggerFactory.getLogger(MemoireResource.class);

    private static final String SAVE_PATH = "/opt/memoire/files";


    private final MemoireRepository memoireRepository;
    private final MemoireMapper memoireMapper;

    public MemoireController(MemoireRepository memoireRepository, MemoireMapper memoireMapper) {
        this.memoireRepository = memoireRepository;
        this.memoireMapper = memoireMapper;
    }

    /**
     * GET  /memoires/upload : upload an array of memoire.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the memoireDTO, or with status 500 (Error)
     */
    @PostMapping("/memoires/upload")
    @Timed
    public ResponseEntity<List<MemoireDTO>> uploadMemoire(@RequestBody List<MemoireDTO> memoires) {
        //TODO : Fix client sending too much memoire
        memoires.forEach(m -> {
            String savePath = SAVE_PATH + "/" + m.getNom() + m.getSujet() + "." + m.getExtension();
            //String savePath = AppProperties.savePath + "/" + m.getData().getOriginalFilename();
            File convFile = new File(savePath);
            try {
                byte[] data = Base64.decodeBase64(m.getBase64Data());
                saveFile(convFile,data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            m.setDataPath(savePath);
        });
        List<Memoire> memoiresEntity = memoireMapper.toEntity(memoires);
        memoiresEntity = memoireRepository.save(memoiresEntity);
        return ResponseEntity.ok().body(memoireMapper.toDto(memoiresEntity));
    }

    public void saveFile(File file, byte[] bytes) throws IOException
    {
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
        } finally
        {
            if (fos != null)
                try
                {
                    fos.close();
                } catch (IOException e)
                {
                    // Sad, but true
                }
        }
    }
}
