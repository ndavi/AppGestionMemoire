package fr.sciencesu.memoire.repository;

import fr.sciencesu.memoire.domain.Memoire;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Memoire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemoireRepository extends JpaRepository<Memoire, Long> {
    @Query("select distinct memoire from Memoire memoire left join fetch memoire.tags")
    List<Memoire> findAllWithEagerRelationships();

    @Query("select memoire from Memoire memoire left join fetch memoire.tags where memoire.id =:id")
    Memoire findOneWithEagerRelationships(@Param("id") Long id);

    List<Memoire> findByConfidentielFalse();

}
