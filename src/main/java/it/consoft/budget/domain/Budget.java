package it.consoft.budget.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Budget.
 */
@Entity
@Table(name = "budget")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Budget implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "gennaio", precision=10, scale=2)
    private BigDecimal gennaio;

    @Column(name = "febbraio", precision=10, scale=2)
    private BigDecimal febbraio;

    @Column(name = "marzo", precision=10, scale=2)
    private BigDecimal marzo;

    @Column(name = "aprile", precision=10, scale=2)
    private BigDecimal aprile;

    @Column(name = "maggio", precision=10, scale=2)
    private BigDecimal maggio;

    @Column(name = "giugno", precision=10, scale=2)
    private BigDecimal giugno;

    @Column(name = "luglio", precision=10, scale=2)
    private BigDecimal luglio;

    @Column(name = "agosto", precision=10, scale=2)
    private BigDecimal agosto;

    @Column(name = "settembre", precision=10, scale=2)
    private BigDecimal settembre;

    @Column(name = "ottobre", precision=10, scale=2)
    private BigDecimal ottobre;

    @Column(name = "novembre", precision=10, scale=2)
    private BigDecimal novembre;

    @Column(name = "dicembre", precision=10, scale=2)
    private BigDecimal dicembre;

    @Column(name = "totale", precision=10, scale=2)
    private BigDecimal totale;

    @Column(name = "mensilizzare")
    private Boolean mensilizzare;

    @Column(name = "divisione")
    private Long divisione;

    @ManyToOne
    private Commessa commessa;

    @ManyToOne
    private Conto conto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getGennaio() {
        return gennaio;
    }

    public Budget gennaio(BigDecimal gennaio) {
        this.gennaio = gennaio;
        return this;
    }

    public void setGennaio(BigDecimal gennaio) {
        this.gennaio = gennaio;
    }

    public BigDecimal getFebbraio() {
        return febbraio;
    }

    public Budget febbraio(BigDecimal febbraio) {
        this.febbraio = febbraio;
        return this;
    }

    public void setFebbraio(BigDecimal febbraio) {
        this.febbraio = febbraio;
    }

    public BigDecimal getMarzo() {
        return marzo;
    }

    public Budget marzo(BigDecimal marzo) {
        this.marzo = marzo;
        return this;
    }

    public void setMarzo(BigDecimal marzo) {
        this.marzo = marzo;
    }

    public BigDecimal getAprile() {
        return aprile;
    }

    public Budget aprile(BigDecimal aprile) {
        this.aprile = aprile;
        return this;
    }

    public void setAprile(BigDecimal aprile) {
        this.aprile = aprile;
    }

    public BigDecimal getMaggio() {
        return maggio;
    }

    public Budget maggio(BigDecimal maggio) {
        this.maggio = maggio;
        return this;
    }

    public void setMaggio(BigDecimal maggio) {
        this.maggio = maggio;
    }

    public BigDecimal getGiugno() {
        return giugno;
    }

    public Budget giugno(BigDecimal giugno) {
        this.giugno = giugno;
        return this;
    }

    public void setGiugno(BigDecimal giugno) {
        this.giugno = giugno;
    }

    public BigDecimal getLuglio() {
        return luglio;
    }

    public Budget luglio(BigDecimal luglio) {
        this.luglio = luglio;
        return this;
    }

    public void setLuglio(BigDecimal luglio) {
        this.luglio = luglio;
    }

    public BigDecimal getAgosto() {
        return agosto;
    }

    public Budget agosto(BigDecimal agosto) {
        this.agosto = agosto;
        return this;
    }

    public void setAgosto(BigDecimal agosto) {
        this.agosto = agosto;
    }

    public BigDecimal getSettembre() {
        return settembre;
    }

    public Budget settembre(BigDecimal settembre) {
        this.settembre = settembre;
        return this;
    }

    public void setSettembre(BigDecimal settembre) {
        this.settembre = settembre;
    }

    public BigDecimal getOttobre() {
        return ottobre;
    }

    public Budget ottobre(BigDecimal ottobre) {
        this.ottobre = ottobre;
        return this;
    }

    public void setOttobre(BigDecimal ottobre) {
        this.ottobre = ottobre;
    }

    public BigDecimal getNovembre() {
        return novembre;
    }

    public Budget novembre(BigDecimal novembre) {
        this.novembre = novembre;
        return this;
    }

    public void setNovembre(BigDecimal novembre) {
        this.novembre = novembre;
    }

    public BigDecimal getDicembre() {
        return dicembre;
    }

    public Budget dicembre(BigDecimal dicembre) {
        this.dicembre = dicembre;
        return this;
    }

    public void setDicembre(BigDecimal dicembre) {
        this.dicembre = dicembre;
    }

    public BigDecimal getTotale() {
        return totale;
    }

    public Budget totale(BigDecimal totale) {
        this.totale = totale;
        return this;
    }

    public void setTotale(BigDecimal totale) {
        this.totale = totale;
    }

    public Boolean isMensilizzare() {
        return mensilizzare;
    }

    public Budget mensilizzare(Boolean mensilizzare) {
        this.mensilizzare = mensilizzare;
        return this;
    }

    public void setMensilizzare(Boolean mensilizzare) {
        this.mensilizzare = mensilizzare;
    }

    public Long getDivisione() {
        return divisione;
    }

    public Budget divisione(Long divisione) {
        this.divisione = divisione;
        return this;
    }

    public void setDivisione(Long divisione) {
        this.divisione = divisione;
    }

    public Commessa getCommessa() {
        return commessa;
    }

    public Budget commessa(Commessa commessa) {
        this.commessa = commessa;
        return this;
    }

    public void setCommessa(Commessa commessa) {
        this.commessa = commessa;
    }

    public Conto getConto() {
        return conto;
    }

    public Budget conto(Conto conto) {
        this.conto = conto;
        return this;
    }

    public void setConto(Conto conto) {
        this.conto = conto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Budget budget = (Budget) o;
        if (budget.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), budget.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Budget{" +
            "id=" + getId() +
            ", gennaio='" + getGennaio() + "'" +
            ", febbraio='" + getFebbraio() + "'" +
            ", marzo='" + getMarzo() + "'" +
            ", aprile='" + getAprile() + "'" +
            ", maggio='" + getMaggio() + "'" +
            ", giugno='" + getGiugno() + "'" +
            ", luglio='" + getLuglio() + "'" +
            ", agosto='" + getAgosto() + "'" +
            ", settembre='" + getSettembre() + "'" +
            ", ottobre='" + getOttobre() + "'" +
            ", novembre='" + getNovembre() + "'" +
            ", dicembre='" + getDicembre() + "'" +
            ", totale='" + getTotale() + "'" +
            ", mensilizzare='" + isMensilizzare() + "'" +
            ", divisione='" + getDivisione() + "'" +
            "}";
    }
}
