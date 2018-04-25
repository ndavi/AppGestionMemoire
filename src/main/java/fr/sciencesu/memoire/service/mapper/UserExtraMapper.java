package fr.sciencesu.memoire.service.mapper;

import fr.sciencesu.memoire.domain.*;
import fr.sciencesu.memoire.service.dto.UserExtraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserExtra and its DTO UserExtraDTO.
 */
@Mapper(componentModel = "spring", uses = {PromotionMapper.class, SecteurMapper.class})
public interface UserExtraMapper extends EntityMapper<UserExtraDTO, UserExtra> {

    @Mapping(source = "promotion.id", target = "promotionId")
    @Mapping(source = "secteur.id", target = "secteurId")
    UserExtraDTO toDto(UserExtra userExtra);

    @Mapping(source = "promotionId", target = "promotion")
    @Mapping(source = "secteurId", target = "secteur")
    UserExtra toEntity(UserExtraDTO userExtraDTO);

    default UserExtra fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserExtra userExtra = new UserExtra();
        userExtra.setId(id);
        return userExtra;
    }
}
