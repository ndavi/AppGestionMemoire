package fr.sciencesu.memoire.service.mapper;

import fr.sciencesu.memoire.domain.*;
import fr.sciencesu.memoire.service.dto.TagDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tag and its DTO TagDTO.
 */
@Mapper(componentModel = "spring", uses = {MemoireMapper.class})
public interface TagMapper extends EntityMapper<TagDTO, Tag> {


    @Mapping(target = "secteurs", ignore = true)
    Tag toEntity(TagDTO tagDTO);

    default Tag fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tag tag = new Tag();
        tag.setId(id);
        return tag;
    }
}
