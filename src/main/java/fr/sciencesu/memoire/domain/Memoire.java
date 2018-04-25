package fr.sciencesu.memoire.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import fr.sciencesu.memoire.domain.enumeration.Langue;

/**
 * A Memoire.
 */
@Entity
@Table(name = "memoire")
public class Memoire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "sujet", nullable = false)
    private String sujet;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "langue", nullable = false)
    private Langue langue;

    @Column(name = "confidentiel")
    private Boolean confidentiel;

    @Column(name = "data_path")
    private String dataPath;

    @ManyToMany
    @JoinTable(name = "memoire_tags",
               joinColumns = @JoinColumn(name="memoires_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="tags_id", referencedColumnName="id"))
    private Set<Tag> tags = new HashSet<>();

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

    public Memoire nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSujet() {
        return sujet;
    }

    public Memoire sujet(String sujet) {
        this.sujet = sujet;
        return this;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public Langue getLangue() {
        return langue;
    }

    public Memoire langue(Langue langue) {
        this.langue = langue;
        return this;
    }

    public void setLangue(Langue langue) {
        this.langue = langue;
    }

    public Boolean isConfidentiel() {
        return confidentiel;
    }

    public Memoire confidentiel(Boolean confidentiel) {
        this.confidentiel = confidentiel;
        return this;
    }

    public void setConfidentiel(Boolean confidentiel) {
        this.confidentiel = confidentiel;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Memoire tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public Memoire addTags(Tag tag) {
        this.tags.add(tag);
        return this;
    }

    public Memoire removeTags(Tag tag) {
        this.tags.remove(tag);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
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
        Memoire memoire = (Memoire) o;
        if (memoire.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), memoire.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Memoire{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", sujet='" + getSujet() + "'" +
            ", langue='" + getLangue() + "'" +
            ", confidentiel='" + isConfidentiel() + "'" +
            "}";
    }
}
