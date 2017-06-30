package it.consoft.budget.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A BudgetTemporaneo.
 */
@Entity
@Table(name = "budget_temporaneo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BudgetTemporaneo implements Serializable {

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
    private ContoContabile contoContabile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getGennaio() {
        return gennaio;
    }

    public BudgetTemporaneo gennaio(BigDecimal gennaio) {
        this.gennaio = gennaio;
        return this;
    }

    public void setGennaio(BigDecimal gennaio) {
        this.gennaio = gennaio;
    }

    public BigDecimal getFebbraio() {
        return febbraio;
    }

    public BudgetTemporaneo febbraio(BigDecimal febbraio) {
        this.febbraio = febbraio;
        return this;
    }

    public void setFebbraio(BigDecimal febbraio) {
        this.febbraio = febbraio;
    }

    public BigDecimal getMarzo() {
        return marzo;
    }

    public BudgetTemporaneo marzo(BigDecimal marzo) {
        this.marzo = marzo;
        return this;
    }

    public void setMarzo(BigDecimal marzo) {
        this.marzo = marzo;
    }

    public BigDecimal getAprile() {
        return aprile;
    }

    public BudgetTemporaneo aprile(BigDecimal aprile) {
        this.aprile = aprile;
        return this;
    }

    public void setAprile(BigDecimal aprile) {
        this.aprile = aprile;
    }

    public BigDecimal getMaggio() {
        return maggio;
    }

    public BudgetTemporaneo maggio(BigDecimal maggio) {
        this.maggio = maggio;
        return this;
    }

    public void setMaggio(BigDecimal maggio) {
        this.maggio = maggio;
    }

    public BigDecimal getGiugno() {
        return giugno;
    }

    public BudgetTemporaneo giugno(BigDecimal giugno) {
        this.giugno = giugno;
        return this;
    }

    public void setGiugno(BigDecimal giugno) {
        this.giugno = giugno;
    }

    public BigDecimal getLuglio() {
        return luglio;
    }

    public BudgetTemporaneo luglio(BigDecimal luglio) {
        this.luglio = luglio;
        return this;
    }

    public void setLuglio(BigDecimal luglio) {
        this.luglio = luglio;
    }

    public BigDecimal getAgosto() {
        return agosto;
    }

    public BudgetTemporaneo agosto(BigDecimal agosto) {
        this.agosto = agosto;
        return this;
    }

    public void setAgosto(BigDecimal agosto) {
        this.agosto = agosto;
    }

    public BigDecimal getSettembre() {
        return settembre;
    }

    public BudgetTemporaneo settembre(BigDecimal settembre) {
        this.settembre = settembre;
        return this;
    }

    public void setSettembre(BigDecimal settembre) {
        this.settembre = settembre;
    }

    public BigDecimal getOttobre() {
        return ottobre;
    }

    public BudgetTemporaneo ottobre(BigDecimal ottobre) {
        this.ottobre = ottobre;
        return this;
    }

    public void setOttobre(BigDecimal ottobre) {
        this.ottobre = ottobre;
    }

    public BigDecimal getNovembre() {
        return novembre;
    }

    public BudgetTemporaneo novembre(BigDecimal novembre) {
        this.novembre = novembre;
        return this;
    }

    public void setNovembre(BigDecimal novembre) {
        this.novembre = novembre;
    }

    public BigDecimal getDicembre() {
        return dicembre;
    }

    public BudgetTemporaneo dicembre(BigDecimal dicembre) {
        this.dicembre = dicembre;
        return this;
    }

    public void setDicembre(BigDecimal dicembre) {
        this.dicembre = dicembre;
    }

    public BigDecimal getTotale() {
        return totale;
    }

    public BudgetTemporaneo totale(BigDecimal totale) {
        this.totale = totale;
        return this;
    }

    public void setTotale(BigDecimal totale) {
        this.totale = totale;
    }

    public Boolean isMensilizzare() {
        return mensilizzare;
    }

    public BudgetTemporaneo mensilizzare(Boolean mensilizzare) {
        this.mensilizzare = mensilizzare;
        return this;
    }

    public void setMensilizzare(Boolean mensilizzare) {
        this.mensilizzare = mensilizzare;
    }

    public Long getDivisione() {
        return divisione;
    }

    public BudgetTemporaneo divisione(Long divisione) {
        this.divisione = divisione;
        return this;
    }

    public void setDivisione(Long divisione) {
        this.divisione = divisione;
    }

    public Commessa getCommessa() {
        return commessa;
    }

    public BudgetTemporaneo commessa(Commessa commessa) {
        this.commessa = commessa;
        return this;
    }

    public void setCommessa(Commessa commessa) {
        this.commessa = commessa;
    }

    public ContoContabile getContoContabile() {
        return contoContabile;
    }

    public BudgetTemporaneo contoContabile(ContoContabile contoContabile) {
        this.contoContabile = contoContabile;
        return this;
    }

    public void setContoContabile(ContoContabile contoContabile) {
        this.contoContabile = contoContabile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BudgetTemporaneo budgetTemporaneo = (BudgetTemporaneo) o;
        if (budgetTemporaneo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), budgetTemporaneo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BudgetTemporaneo{" +
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
