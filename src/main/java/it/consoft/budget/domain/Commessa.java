package it.consoft.budget.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Commessa.
 */
@Entity
@Table(name = "commessa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Commessa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "codice", nullable = false)
    private String codice;

    @ManyToOne
    private Divisione divisione;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodice() {
        return codice;
    }

    public Commessa codice(String codice) {
        this.codice = codice;
        return this;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public Divisione getDivisione() {
        return divisione;
    }

    public Commessa divisione(Divisione divisione) {
        this.divisione = divisione;
        return this;
    }

    public void setDivisione(Divisione divisione) {
        this.divisione = divisione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Commessa commessa = (Commessa) o;
        if (commessa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commessa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Commessa{" +
            "id=" + getId() +
            ", codice='" + getCodice() + "'" +
            "}";
    }
}
