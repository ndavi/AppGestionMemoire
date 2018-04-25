package fr.sciencesu.memoire.repository;

import fr.sciencesu.memoire.domain.Secteur;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Secteur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecteurRepository extends JpaRepository<Secteur, Long> {

}
