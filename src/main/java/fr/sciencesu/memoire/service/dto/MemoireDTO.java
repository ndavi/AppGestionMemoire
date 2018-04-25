package fr.sciencesu.memoire.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import fr.sciencesu.memoire.domain.enumeration.Langue;
import org.springframework.web.multipart.MultipartFile;

/**
 * A DTO for the Memoire entity.
 */
public class MemoireDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String sujet;

    @NotNull
    private Langue langue;

    private Boolean confidentiel;

    private MultipartFile data;

    private String dataPath;

    private Set<TagDTO> tags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public Langue getLangue() {
        return langue;
    }

    public void setLangue(Langue langue) {
        this.langue = langue;
    }

    public Boolean isConfidentiel() {
        return confidentiel;
    }

    public void setConfidentiel(Boolean confidentiel) {
        this.confidentiel = confidentiel;
    }

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public MultipartFile getData() {
        return data;
    }

    public void setData(MultipartFile data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MemoireDTO memoireDTO = (MemoireDTO) o;
        if(memoireDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), memoireDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MemoireDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", sujet='" + getSujet() + "'" +
            ", langue='" + getLangue() + "'" +
            ", confidentiel='" + isConfidentiel() + "'" +
            "}";
    }
}
