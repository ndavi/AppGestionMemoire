package fr.sciencesu.memoire.service.mapper;

import fr.sciencesu.memoire.domain.*;
import fr.sciencesu.memoire.service.dto.MemoireDTO;

import org.mapstruct.*;

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
}
