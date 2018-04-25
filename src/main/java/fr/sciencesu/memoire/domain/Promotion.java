package fr.sciencesu.memoire.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Promotion.
 */
@Entity
@Table(name = "promotion")
public class Promotion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "datedebut")
    private Instant datedebut;

    @Column(name = "datefin")
    private Instant datefin;

    @ManyToOne
    private Specialite specialite;

    @OneToMany(mappedBy = "promotion")
    @JsonIgnore
    private Set<UserExtra> utilisateurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDatedebut() {
        return datedebut;
    }

    public Promotion datedebut(Instant datedebut) {
        this.datedebut = datedebut;
        return this;
    }

    public void setDatedebut(Instant datedebut) {
        this.datedebut = datedebut;
    }

    public Instant getDatefin() {
        return datefin;
    }

    public Promotion datefin(Instant datefin) {
        this.datefin = datefin;
        return this;
    }

    public void setDatefin(Instant datefin) {
        this.datefin = datefin;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public Promotion specialite(Specialite specialite) {
        this.specialite = specialite;
        return this;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public Set<UserExtra> getUtilisateurs() {
        return utilisateurs;
    }

    public Promotion utilisateurs(Set<UserExtra> userExtras) {
        this.utilisateurs = userExtras;
        return this;
    }

    public Promotion addUtilisateurs(UserExtra userExtra) {
        this.utilisateurs.add(userExtra);
        userExtra.setPromotion(this);
        return this;
    }

    public Promotion removeUtilisateurs(UserExtra userExtra) {
        this.utilisateurs.remove(userExtra);
        userExtra.setPromotion(null);
        return this;
    }

    public void setUtilisateurs(Set<UserExtra> userExtras) {
        this.utilisateurs = userExtras;
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
        Promotion promotion = (Promotion) o;
        if (promotion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), promotion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Promotion{" +
            "id=" + getId() +
            ", datedebut='" + getDatedebut() + "'" +
            ", datefin='" + getDatefin() + "'" +
            "}";
    }
}
