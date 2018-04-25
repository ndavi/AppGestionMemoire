package fr.sciencesu.memoire.service.mapper;

import fr.sciencesu.memoire.domain.*;
import fr.sciencesu.memoire.service.dto.PromotionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Promotion and its DTO PromotionDTO.
 */
@Mapper(componentModel = "spring", uses = {SpecialiteMapper.class})
public interface PromotionMapper extends EntityMapper<PromotionDTO, Promotion> {

    @Mapping(source = "specialite.id", target = "specialiteId")
    PromotionDTO toDto(Promotion promotion);

    @Mapping(source = "specialiteId", target = "specialite")
    @Mapping(target = "utilisateurs", ignore = true)
    Promotion toEntity(PromotionDTO promotionDTO);

    default Promotion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Promotion promotion = new Promotion();
        promotion.setId(id);
        return promotion;
    }
}
