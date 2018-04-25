package fr.sciencesu.memoire.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Specialite.
 */
@Entity
@Table(name = "specialite")
public class Specialite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @OneToMany(mappedBy = "specialite")
    @JsonIgnore
    private Set<Promotion> specialites = new HashSet<>();

    @ManyToOne
    private Secteur secteur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Specialite libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Promotion> getSpecialites() {
        return specialites;
    }

    public Specialite specialites(Set<Promotion> promotions) {
        this.specialites = promotions;
        return this;
    }

    public Specialite addSpecialite(Promotion promotion) {
        this.specialites.add(promotion);
        promotion.setSpecialite(this);
        return this;
    }

    public Specialite removeSpecialite(Promotion promotion) {
        this.specialites.remove(promotion);
        promotion.setSpecialite(null);
        return this;
    }

    public void setSpecialites(Set<Promotion> promotions) {
        this.specialites = promotions;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public Specialite secteur(Secteur secteur) {
        this.secteur = secteur;
        return this;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
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
        Specialite specialite = (Specialite) o;
        if (specialite.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), specialite.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Specialite{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
