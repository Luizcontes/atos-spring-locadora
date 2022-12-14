package contes.atoslocadora.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Priceused {

    @Id
    @GeneratedValue
    private Long id;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PriceType priceType = PriceType.LUXURY;
    
    @NotNull
    @Column(nullable = false)
    private Double price;
    
    @OneToMany(mappedBy = "priceused", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonIgnore
    private List<Automovel> automoveis = new ArrayList<>();
    
    public Priceused() {
    }

    public Priceused(@NotNull PriceType priceType, @NotNull Double price) {
        this.priceType = priceType;
        this.price = price;
    }

    public Priceused(PriceTable priceTable){
        this.priceType = priceTable.getPriceType();
        this.price = priceTable.getPrice();
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

    public List<Automovel> getAutomoveis() {
        return automoveis;
    }

    public void addAutomovel(Automovel automovel) {
        automoveis.add(automovel);
    }
}
