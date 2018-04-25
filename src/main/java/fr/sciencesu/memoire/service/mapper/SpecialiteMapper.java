package fr.sciencesu.memoire.service.mapper;

import fr.sciencesu.memoire.domain.*;
import fr.sciencesu.memoire.service.dto.SpecialiteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Specialite and its DTO SpecialiteDTO.
 */
@Mapper(componentModel = "spring", uses = {SecteurMapper.class})
public interface SpecialiteMapper extends EntityMapper<SpecialiteDTO, Specialite> {

    @Mapping(source = "secteur.id", target = "secteurId")
    SpecialiteDTO toDto(Specialite specialite);

    @Mapping(target = "specialites", ignore = true)
    @Mapping(source = "secteurId", target = "secteur")
    Specialite toEntity(SpecialiteDTO specialiteDTO);

    default Specialite fromId(Long id) {
        if (id == null) {
            return null;
        }
        Specialite specialite = new Specialite();
        specialite.setId(id);
        return specialite;
    }
}
