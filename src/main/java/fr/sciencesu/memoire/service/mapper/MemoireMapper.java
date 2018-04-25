package fr.sciencesu.memoire.service.mapper;

import fr.sciencesu.memoire.domain.*;
import fr.sciencesu.memoire.service.dto.MemoireDTO;

import fr.sciencesu.memoire.service.util.AppProperties;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Value;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Mapper for the entity Memoire and its DTO MemoireDTO.
 */
@Mapper(componentModel = "spring", uses = {TagMapper.class})
public interface MemoireMapper extends EntityMapper<MemoireDTO, Memoire> {

    default Memoire fromId(Long id) {
        if (id == null) {
            return null;
        }
        Memoire memoire = new Memoire();
        memoire.setId(id);
        return memoire;
    }

    @Mapping(target = "data", ignore = true)
    Memoire toEntity(MemoireDTO memoire);
}
