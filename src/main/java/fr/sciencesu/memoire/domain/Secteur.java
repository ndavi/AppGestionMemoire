package fr.sciencesu.memoire.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Secteur.
 */
@Entity
@Table(name = "secteur")
public class Secteur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @OneToMany(mappedBy = "secteur")
    @JsonIgnore
    private Set<Specialite> specialites = new HashSet<>();

    @OneToMany(mappedBy = "secteur")
    @JsonIgnore
    private Set<UserExtra> utilisateurs = new HashSet<>();

    @ManyToOne
    private Tag tag;

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

    public Secteur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Specialite> getSpecialites() {
        return specialites;
    }

    public Secteur specialites(Set<Specialite> specialites) {
        this.specialites = specialites;
        return this;
    }

    public Secteur addSpecialite(Specialite specialite) {
        this.specialites.add(specialite);
        specialite.setSecteur(this);
        return this;
    }

    public Secteur removeSpecialite(Specialite specialite) {
        this.specialites.remove(specialite);
        specialite.setSecteur(null);
        return this;
    }

    public void setSpecialites(Set<Specialite> specialites) {
        this.specialites = specialites;
    }

    public Set<UserExtra> getUtilisateurs() {
        return utilisateurs;
    }

    public Secteur utilisateurs(Set<UserExtra> userExtras) {
        this.utilisateurs = userExtras;
        return this;
    }

    public Secteur addUtilisateurs(UserExtra userExtra) {
        this.utilisateurs.add(userExtra);
        userExtra.setSecteur(this);
        return this;
    }

    public Secteur removeUtilisateurs(UserExtra userExtra) {
        this.utilisateurs.remove(userExtra);
        userExtra.setSecteur(null);
        return this;
    }

    public void setUtilisateurs(Set<UserExtra> userExtras) {
        this.utilisateurs = userExtras;
    }

    public Tag getTag() {
        return tag;
    }

    public Secteur tag(Tag tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
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
        Secteur secteur = (Secteur) o;
        if (secteur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), secteur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Secteur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
