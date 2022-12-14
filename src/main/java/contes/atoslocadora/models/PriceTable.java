package contes.atoslocadora.models;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
@PrimaryKeyJoinColumn(name = "price_type")
public class PriceTable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PriceType priceType;
    
    @NotNull
    @Column(nullable = false)
    private Double price;
    
    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime lastModified;
    
    public PriceTable() {
    }

    public PriceTable(@NotNull PriceType priceType, @NotNull Double price) {
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

    @Override
    public boolean equals(Object o) {
        PriceTable priceTable = (PriceTable) o;

        if (this == o) return true;
        if (Objects.equals(priceType, priceTable.priceType)) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(price, priceTable.price);
    }
}
