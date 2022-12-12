package contes.atoslocadora.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

// @Entity
public class Price {
    
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PriceType priceType = PriceType.LUXURY;

    @NotNull
    private Double price;

    @OneToMany(mappedBy = "automovel")
    private Set<Automovel> automoveis = new HashSet<>();

    public Price() {
    }

    public Price(@NotNull PriceType priceType, @NotNull Double price) {
        this.priceType = priceType;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public PriceType getPriceType() {
        return priceType;
    }

    public void setPriceType(PriceType priceType) {
        this.priceType = priceType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<Automovel> getAutomoveis() {
        return automoveis;
    }

    public void addAutomovel(Automovel automovel) {
        automoveis.add(automovel);
    }

    
}
