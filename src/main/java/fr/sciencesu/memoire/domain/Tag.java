package fr.sciencesu.memoire.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Tag.
 */
@Entity
@Table(name = "tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @OneToMany(mappedBy = "tag")
    @JsonIgnore
    private Set<Secteur> secteurs = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tag_memoires",
               joinColumns = @JoinColumn(name="tags_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="memoires_id", referencedColumnName="id"))
    private Set<Memoire> memoires = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Tag nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Secteur> getSecteurs() {
        return secteurs;
    }

    public Tag secteurs(Set<Secteur> secteurs) {
        this.secteurs = secteurs;
        return this;
    }

    public Tag addSecteurs(Secteur secteur) {
        this.secteurs.add(secteur);
        secteur.setTag(this);
        return this;
    }

    public Tag removeSecteurs(Secteur secteur) {
        this.secteurs.remove(secteur);
        secteur.setTag(null);
        return this;
    }

    public void setSecteurs(Set<Secteur> secteurs) {
        this.secteurs = secteurs;
    }

    public Set<Memoire> getMemoires() {
        return memoires;
    }

    public Tag memoires(Set<Memoire> memoires) {
        this.memoires = memoires;
        return this;
    }

    public Tag addMemoires(Memoire memoire) {
        this.memoires.add(memoire);
        return this;
    }

    public Tag removeMemoires(Memoire memoire) {
        this.memoires.remove(memoire);
        return this;
    }

    public void setMemoires(Set<Memoire> memoires) {
        this.memoires = memoires;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tag tag = (Tag) o;
        if (tag.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tag.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tag{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
