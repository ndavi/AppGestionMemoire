package fr.sciencesu.memoire.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Specialite entity.
 */
public class SpecialiteDTO implements Serializable {

    private Long id;

    private String libelle;

    private Long secteurId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Long getSecteurId() {
        return secteurId;
    }

    public void setSecteurId(Long secteurId) {
        this.secteurId = secteurId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SpecialiteDTO specialiteDTO = (SpecialiteDTO) o;
        if(specialiteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), specialiteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SpecialiteDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
