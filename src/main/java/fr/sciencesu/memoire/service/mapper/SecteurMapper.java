package fr.sciencesu.memoire.service.mapper;

import fr.sciencesu.memoire.domain.*;
import fr.sciencesu.memoire.service.dto.SecteurDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Secteur and its DTO SecteurDTO.
 */
@Mapper(componentModel = "spring", uses = {TagMapper.class})
public interface SecteurMapper extends EntityMapper<SecteurDTO, Secteur> {

    @Mapping(source = "tag.id", target = "tagId")
    SecteurDTO toDto(Secteur secteur);

    @Mapping(target = "specialites", ignore = true)
    @Mapping(target = "utilisateurs", ignore = true)
    @Mapping(source = "tagId", target = "tag")
    Secteur toEntity(SecteurDTO secteurDTO);

    default Secteur fromId(Long id) {
        if (id == null) {
            return null;
        }
        Secteur secteur = new Secteur();
        secteur.setId(id);
        return secteur;
    }
}
